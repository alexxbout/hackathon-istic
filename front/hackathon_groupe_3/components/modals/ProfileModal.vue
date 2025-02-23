<template>
    <UModal :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' }, width: '' }">
        <UCard class="max-h-[90vh] w-[70vw] overflow-y-auto">
            <div class="grid grid-cols-2 gap-x-5" :class="!props.profil?.cvUrl ? 'divide-x' : ''">
                <!-- Left Column -->
                <div class="flex flex-col justify-between gap-y-5">
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
                                    <div v-for="(skill, index) in competences">
                                        <!-- TODO: Replace with BadgeSkill component -->
                                        <UBadge v-if="mode == ProfilModalMode.INFO" color="black" variant="solid">{{ skill }}</UBadge>

                                        <BadgeCompetence v-else :key="index" :label="skill" @remove="" />
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
import type { Competence, Profil } from "~/types/entities";
import { ProfilModalMode } from "~/types/modals";
import BadgeCompetence from "~/components/BadgeSkill.vue";
import { APIUtils } from "~/types/utilsApi";

const props = defineProps<{
    profil?: Profil;
}>();

const emits = defineEmits<{
    (e: "close"): void;
    (e: "update", newMode: ProfilModalMode): void;
    (e: "create", profil: Profil): void;
}>();

const mode = defineModel<ProfilModalMode>("mode", {
    default: ProfilModalMode.INFO,
});

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

const name = ref<string>(props.profil?.nom ?? "");
const firstname = ref<string>(props.profil?.prenom ?? "");
const ville = ref<string>(props.profil?.ville ?? "");
const experience = ref<number>(props.profil?.experience ?? 0);
const competences = ref<string[]>(props.profil?.competences.map((c: any) => c.nom) ?? []);

const profilePictureUrl = ref<string | undefined>(props.profil?.profile_picture);

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
const selectedExperience = ref(props.profil?.experience ?? 0);

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

const handleSaveUpdate = async () => {
    console.log("Saving profile...");

    const competencesIds: number[] = [];

    await APIUtils.getCompetences().then((response) => {
        response.data.forEach((c: any) => {
            if (competencesIds.includes(c.id!)) {
                competencesIds.push(c.id!);
            }
        });
    });

    await APIUtils.updateProfil(props.profil?.id!, {
        nom: name.value,
        prenom: firstname.value,
        ville: ville.value,
        experience: selectedExperience.value,
        competenceIds: competencesIds,
    })
        .then((response) => {
            console.log("Profil updated", response.data);

            mode.value = ProfilModalMode.INFO;

            console.log("Nouveau mode :", mode.value);

            toast.add({ title: "Profil mis à jour", color: "green" });
        })
        .catch((error) => {
            console.error("Error updating profil", error);

            toast.add({ title: "Erreur lors de la mise à jour du profil", color: "red" });
        });
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

const handleSaveCreate = async () => {
    console.log("Create profil");

    const competencesIds: number[] = [];

    await APIUtils.getCompetences().then((response) => {
        response.data.forEach((c: any) => {
            if (competencesIds.includes(c.id!)) {
                competencesIds.push(c.id!);
            }
        });
    });

    await APIUtils.addProfil({
        nom: name.value,
        prenom: firstname.value,
        ville: ville.value,
        experience: selectedExperience.value,
        competenceIds: competencesIds,
    })
        .then((response) => {
            console.log("Profil created", response.data);

            mode.value = ProfilModalMode.INFO;

            console.log("Nouveau mode :", mode.value);

            toast.add({ title: "Profil créé", color: "green" });

            emits("create", response.data);
        })
        .catch((error) => {
            console.error("Error creating profil", error);
        });
};

onMounted(() => {
    console.log("ProfilModal mounted");

    header.value = getHeader();

    // Update profil.cvUrl with random oneline pdf cv

    const pdfURLs = ["https://www.resumeviking.com/wp-content/uploads/2022/02/Stockholm-Resume-Template-Simple.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/New-York-Resume-Template-Creative.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/Vienna-Modern-Resume-Template.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/Sydney-Resume-Template-Modern.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/London-Resume-Template-Professional.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/Dublin-Resume-Template-Modern.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/Moscow-Creative-Resume-Template.pdf", "https://www.resumeviking.com/wp-content/uploads/2022/02/Amsterdam-Modern-Resume-Template.pdf"];

    const randomIndex = Math.floor(Math.random() * pdfURLs.length);

    if (mode.value !== ProfilModalMode.CREATE) {
        props.profil!.cvUrl = pdfURLs[randomIndex];
    }
});
</script>
