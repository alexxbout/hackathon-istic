<template>
    <UModal :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' }, width: '' }">
        <UCard class="max-h-[90vh] overflow-y-auto w-[70vw]">
            <div class="grid grid-cols-2 gap-x-5">
                <!-- Left Column -->
                <div class="flex flex-col justify-between">
                    <div class="flex flex-col gap-y-10">
                        <span class="text-xl font-semibold">{{ header }}</span>

                        <div class="w-full flex items-center justify-center flex-col">
                            <!-- Mode INFO : Affichage simple -->
                            <UAvatar v-if="mode == ProfilModalMode.INFO" alt="Photo de profil" :src="profilePictureUrl" size="xl" :ui="{ size: { xl: 'h-24 w-24 text-3xl' } }" />

                            <!-- Mode UPDATE : Affichage avec suppression -->
                            <div v-else-if="mode == ProfilModalMode.UPDATE && profilePictureUrl" class="relative">
                                <div class="flex items-center justify-center p-2 bg-red-500 absolute -top-2 -right-2 z-10 rounded-full cursor-pointer" @click="profilePictureUrl = undefined">
                                    <UIcon name="ic:sharp-delete" class="text-white w-4 h-4" />
                                </div>
                                <UAvatar alt="Photo de profil" :src="profilePictureUrl" size="xl" :ui="{ size: { xl: 'h-24 w-24 text-3xl' }, background: 'outline-4 outline-dashed outline-green-500 cursor-pointer' }" />
                            </div>

                            <!-- Afficher le contour vert si aucune image -->
                            <label v-else-if="mode == ProfilModalMode.UPDATE || mode == ProfilModalMode.CREATE" for="profile_picture" class="flex items-center justify-center outline-4 outline-dashed outline-green-500 cursor-pointer h-24 w-24 rounded-full bg-green-50">
                                <input id="profile_picture" type="file" class="hidden" accept="image/*" @change="handleProfilePictureUpload" />
                                <UIcon name="mdi:image-plus" class="text-green-500 w-10 h-10" />
                            </label>
                        </div>

                        <div class="flex flex-col gap-y-5">
                            <div class="flex flex-col gap-y-2">
                                <span>Nom</span>
                                <UInput size="md" :variant="mode == ProfilModalMode.INFO ? 'none' : 'outline'" color="gray" v-model="name" placeholder="Nom" :disabled="mode == ProfilModalMode.INFO" />
                            </div>

                            <div class="flex flex-col gap-y-2">
                                <span>Prénom</span>
                                <UInput size="md" :variant="mode == ProfilModalMode.INFO ? 'none' : 'outline'" color="gray" v-model="firstname" placeholder="Prénom" :disabled="mode == ProfilModalMode.INFO" />
                            </div>

                            <div class="flex flex-col gap-y-2">
                                <span>Ville</span>
                                <UInput size="md" :variant="mode == ProfilModalMode.INFO ? 'none' : 'outline'" color="gray" v-model="ville" placeholder="Ville" :disabled="mode == ProfilModalMode.INFO" />
                            </div>

                            <div class="flex flex-col gap-y-2">
                                <span>Expérience</span>
                                <USelect class="w-full" :variant="mode == ProfilModalMode.INFO ? 'none' : 'outline'" color="gray" size="md" v-model="selectedExperience" :options="experiencesOptions" :placeholder="experiencePlaceholder" :disabled="mode == ProfilModalMode.INFO" />
                            </div>

                            <div class="flex flex-col gap-y-2">
                                <span>Compétences</span>
                                <div class="flex flex-wrap gap-2">
                                    <div v-for="skill in competences">
                                        <!-- TODO: Replace with BadgeSkill component -->
                                        <UButton color="black" variant="solid">{{ skill }}</UButton>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div class="flex items-center justify-center gap-x-2">
                        <UButton v-if="mode == ProfilModalMode.INFO || mode == ProfilModalMode.CREATE" size="md" class="w-1/2" color="gray" icon="lucide:arrow-left" @click="handleCloseModal" :ui="{ base: 'shrink' }" block>Retour</UButton>
                        <UButton v-if="mode == ProfilModalMode.UPDATE" variant="outline" size="md" class="w-1/2" color="red" icon="lucide:circle-x" @click="handleCancelUpdate" :ui="{ base: 'shrink' }" block>Annuler</UButton>
                        <UButton v-if="mode == ProfilModalMode.INFO" variant="outline" size="md" class="w-1/2" icon="lucide:pencil" @click="handleUpdate" block>Modifier...</UButton>
                        <UButton v-if="mode == ProfilModalMode.UPDATE" variant="outline" size="md" class="w-1/2" icon="lucide:circle-check" @click="handleSaveUpdate" block>Sauvegarder</UButton>
                        <UButton v-if="mode == ProfilModalMode.CREATE" variant="outline" size="md" class="w-1/2" icon="lucide:circle-check" @click="handleSaveCreate" block>Valider la création</UButton>
                    </div>
                </div>

                <!-- Right Column -->
                <div class="relative">
                    <PdfViz :mode="mode" :profil="props.profil" />
                </div>
            </div>
        </UCard>
    </UModal>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import type { ProfilCompetences } from "~/types/entities";
import { ProfilModalMode } from "~/types/modals";

const props = defineProps<{
    mode: ProfilModalMode;
    profil?: ProfilCompetences;
}>();

const emits = defineEmits<{
    (e: "close"): void;
    (e: "update", newMode: ProfilModalMode): void;
}>();

const mode = defineModel<ProfilModalMode>("mode");

const toast = useToast();

const header = ref("");

watch(
    () => mode,
    () => {
        header.value = getHeader();
    },
    { deep: true }
);

const getHeader = (): string => {
    switch (mode.value) {
        case ProfilModalMode.INFO:
            return "Information du profil";
        case ProfilModalMode.UPDATE:
            return "Modifier le profil";
        case ProfilModalMode.CREATE:
            return "Créer un profil";
        default:
            return "Unknown";
    }
};

const name = ref<string>(props.profil?.profil.nom ?? "");
const firstname = ref<string>(props.profil?.profil.prenom ?? "");
const ville = ref<string>(props.profil?.profil.ville ?? "");
const experience = ref<string>(props.profil?.profil.experience.toString() ?? "");
const competences = ref<string[]>(props.profil?.competences.map((c) => c.nom) ?? []);

const profilePictureUrl = ref<string | undefined>(props.profil?.profil.profile_picture);

// TODO: Fetch data from API ???
const experienceOptions = [
    { value: 1, label: "+1 an" },
    { value: 2, label: "+2 ans" },
    { value: 3, label: "+3 ans" },
    { value: 4, label: "+4 ans" },
    { value: 5, label: "+5 ans" },
    { value: 6, label: "+6 ans" },
    { value: 7, label: "+7 ans" },
    { value: 8, label: "+8 ans" },
];

const experiencesOptions = ref<{ value: number; label: string }[]>(experienceOptions);
const experiencePlaceholder = ref("Niveau d'éxpérience");
const selectedExperience = ref(); // TODO

const handleUpdate = () => {
    console.log("Update profil");

    mode.value = ProfilModalMode.UPDATE;

    console.log("Nouveau mode :", mode.value);
};

const handleCancelUpdate = () => {
    console.log("Cancel update profil");

    mode.value = ProfilModalMode.INFO;

    console.log("Nouveau mode :", mode.value);
};

const handleDeleteProfilPicture = () => {
    console.log("Delete profil picture");
    // TODO: Call API to delete profil picture
};

const handleSaveUpdate = () => {
    console.log("Save profil");

    mode.value = ProfilModalMode.INFO;

    console.log("Nouveau mode :", mode.value);

    toast.add({ title: "Profil mis à jour", color: "green" });
};

const handleCloseModal = () => {
    emits("close");
};

const handleProfilePictureUpload = (event: Event) => {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
        profilePictureUrl.value = URL.createObjectURL(file);
    }
};

const handleSaveCreate = () => {
    console.log("Create profil");

    mode.value = ProfilModalMode.INFO;

    console.log("Nouveau mode :", mode.value);

    toast.add({ title: "Profil créé", color: "green" });
};

onMounted(() => {
    header.value = getHeader();
});
</script>
