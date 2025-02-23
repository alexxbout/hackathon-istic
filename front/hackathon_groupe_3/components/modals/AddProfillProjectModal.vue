<template>
    <UModal :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' }, width: '' }">
        <UCard class="w-[40vw] overflow-hidden">
            <template #header>
                <div class="flex flex-col gap-y-5">
                    <span class="text-xl font-semibold">Assigner votre équipe à un projet</span>
                    <UProgress :value="progress" indicator />
                </div>
            </template>

            <div v-if="state == State.VALIDATE_PROJECT" class="flex flex-col gap-y-2 w-full">
                <span>Choix du projet</span>
                <USelect v-model="selectedProject" :options="projectsOptions" />
            </div>

            <div v-else-if="state == State.VALIDATE_PROFILES" class="flex flex-col gap-y-2 w-full">
                <span>Membres de l’équipe</span>

                <div>
                    <UTable :rows="rows" :columns="columns">
                        <template #photo-data="{ row }">
                            <UAvatar size="lg" :src="row.photo" :alt="row.nom + row.prenom" />
                        </template>

                        <template #periode-data="{ row }">
                            <UPopover class="w-full" :popper="{ placement: 'bottom-start' }">
                                <UButton size="md" icon="i-heroicons-calendar-days-20-solid">
                                    {{ customFormat(row.periode.start) }} - {{ customFormat(row.periode.end) }}
                                </UButton>

                                <template #panel="{ close }">
                                    <DatePicker 
                                        @update:model-value="updatePeriod(row.id, $event)" 
                                        v-model="row.periode" 
                                        @close="close" 
                                    />
                                </template>
                            </UPopover>
                        </template>
                    </UTable>

                    <div class="flex justify-end px-3 py-3.5 border-t border-gray-200 dark:border-gray-700">
                        <UPagination v-model="page" :page-count="pageCount" :total="props.profiles.length" />
                    </div>
                </div>
            </div>

            <div v-else-if="state == State.FINISH" class="flex flex-col gap-y-2 w-full">
                <span class="font-semibold">Félicitations !</span>
                <span class="font-semibold">Votre équipe a été assignée au projet avec succès.</span>
                <span>Vous pouvez maintenant consulter les détails du projet dans la liste des projets.</span>
            </div>

            <template v-if="state == State.VALIDATE_PROJECT" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="handleCancel" block />
                    <UButton size="md" label="Valider le projet" icon="ic:round-check" class="w-1/2" block @click="handleNext" />
                </div>
            </template>

            <template v-else-if="state == State.VALIDATE_PROFILES" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="handleCancel" block />
                    <UButton size="md" label="Valider l'équipe" icon="ic:round-check" class="w-1/2" block @click="handleNext" />
                </div>
            </template>

            <template v-else-if="state == State.FINISH" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Retour aux profils" icon="tabler:circle-arrow-left" class="w-full" @click="handleCancel" block />
                </div>
            </template>
        </UCard>
    </UModal>
</template>

<script lang="ts" setup>
import { format } from "date-fns";
import { fr } from "date-fns/locale";
import type { Profil, Projet } from "~/types/entities";
import { APIUtils } from "~/types/utilsApi";

const toast = useToast();
const emits = defineEmits<{
    (e: "close"): void;
    (e: "assign"): void;
}>();

enum State {
    VALIDATE_PROJECT,
    VALIDATE_PROFILES,
    FINISH,
}

const props = defineProps<{
    profiles: Profil[];
}>();

const projects = ref<Projet[]>([]);
const projectsOptions = computed(() => 
    projects.value.map((project) => ({ 
        label: project.nom, 
        value: project.id 
    }))
);
const selectedProject = ref<number>();
const selectedProjectComputed = computed<Projet | undefined>(() => 
    projects.value.find((project) => project.id === selectedProject.value)
);

const state = ref(State.VALIDATE_PROJECT);
const page = ref(1);
const pageCount = computed(() => Math.min(3, props.profiles.length));
const periods = ref<{ [key: number]: { start: Date; end: Date } }>({});

watch(selectedProjectComputed, (project) => {
    if (project) {
        periods.value = props.profiles.reduce((acc, profile) => {
            acc[profile.id!] = { 
                start: new Date(project.dateDebut), 
                end: new Date(project.dateFin) 
            };
            return acc;
        }, {} as { [key: number]: { start: Date; end: Date } });
    }
});

const rows = computed(() => {
    return props.profiles
        .slice((page.value - 1) * pageCount.value, page.value * pageCount.value)
        .map((profile) => ({
            id: profile.id,
            photo: profile.profile_picture,
            nom: profile.nom,
            prenom: profile.prenom,
            periode: periods.value[profile.id!] || { start: new Date(), end: new Date() },
        }));
});

const columns = [
    { key: "photo" },
    { key: "nom", label: "Nom" },
    { key: "prenom", label: "Prénom" },
    { key: "periode", label: "Période" },
];

const customFormat = (date: string | Date) => {
    try {
        const parsedDate = typeof date === "string" ? new Date(date) : date;
        return format(parsedDate, "dd MMMM yyyy", { locale: fr });
    } catch (error) {
        return "";
    }
};

const progress = computed(() => {
    switch (state.value) {
        case State.VALIDATE_PROJECT: return 0;
        case State.VALIDATE_PROFILES: return 50;
        case State.FINISH: return 100;
    }
});

const handleNext = () => {
    switch (state.value) {
        case State.VALIDATE_PROJECT:
            if (!selectedProject.value) {
                toast.add({ 
                    title: "Projet requis", 
                    description: "Veuillez sélectionner un projet avant de continuer", 
                    color: "red" 
                });
                return;
            }
            state.value = State.VALIDATE_PROFILES;
            break;
        
        case State.VALIDATE_PROFILES:
            state.value = State.FINISH;
            toast.add({ 
                title: "Équipe assignée", 
                description: "L'équipe a été assignée avec succès", 
                color: "green" 
            });
            break;
    }
};

const handleCancel = () => {
    switch (state.value) {
        case State.VALIDATE_PROFILES:
            state.value = State.VALIDATE_PROJECT;
            break;
        case State.VALIDATE_PROJECT:
        case State.FINISH:
            emits("close");
            break;
    }
};

const updatePeriod = (profileId: number, period: { start: Date; end: Date }) => {
    periods.value[profileId] = period;
};

onMounted(async () => {
    await APIUtils.getProjets().then((response) => {
        projects.value = response.data;
    });
});
</script>