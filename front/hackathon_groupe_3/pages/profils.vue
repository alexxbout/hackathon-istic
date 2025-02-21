<template>
    <div class="flex items-center justify-center w-full h-full">
        <div class="flex flex-col items-center justify-center w-full h-full py-10 gap-y-5 mx-80">
            <!-- Header -->
            <div class="flex items-center justify-between w-full">
                <div class="flex items-center justify-center gap-x-2">
                    <span class="text-3xl font-medium">Profils</span>
                    <span class="text-2xl self-end">({{ filteredCustomProfils.length }})</span>
                </div>
                <div class="flex items-center justify-center w-max gap-x-2">
                    <UButton v-if="role != 'cdp'" @click="handleCreateProfile" variant="soft" color="gray" icon="lucide:user-plus">{{ createProfileButton }}</UButton>
                    <UButton v-if="role != 'rh'" @click="handleAddProfiles" variant="soft" color="green" icon="lucide:mouse-pointer-click">{{ addSelectionToProject }}</UButton>
                    <UButton @click="handleClearFilters" variant="soft" color="red" icon="lucide:circle-x">{{ clearFilters }}</UButton>
                    <UButton v-if="role != 'rh'" @click="handleUnselectAll" variant="soft" color="yellow" icon="lucide:circle-slash-2">{{ deselectAll }}</UButton>
                </div>
            </div>

            <!-- Searchbar -->
            <SearchBar ref="searchBarElement" :role="role" :experienceOptions="experienceOptions" :skillOptions="skillOptions" @updateSkills="updateSkills" @updateExperience="updateExperience" @updatePeriod="updatePeriod" />

            <div v-if="hotreload" class="grid grid-cols-2 grid-rows-3 gap-2 content-between w-full">
                <Card v-for="customProfil in paginatedCustomProfils" @@click="handleShowDetails(customProfil.profil.id!)" @click="toggleSelection(customProfil.profil.id!)" :key="customProfil.profil.id" :id="customProfil.profil.id!" :photo="customProfil.profil.profile_picture" :nom="customProfil.profil.nom" :prenom="customProfil.profil.prenom" :role="role" :experience="customProfil.profil.experience" :isChecked="selectedProfiles.has(customProfil.profil.id!)" />
            </div>

            <UPagination v-show="filteredCustomProfils.length > 6" v-model="page" size="xl" :total="filteredCustomProfils.length" :page-count="6" />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { computed, nextTick, ref } from "vue";
import ProfileModal from "~/components/modals/ProfileModal.vue";
import type SearchBar from "~/components/SearchBar.vue";
import type { Competence, Profil, ProfilCompetences } from "~/types/entities";
import { ProfilModalMode } from "~/types/modals";
import type { Role } from "~/types/roles";

definePageMeta({
    middleware: "auth",
});

interface CustomProfil {
    profil: Profil;
    competences: Competence[];
}

const modal = useModal();

const role = ref<Role>("rh");
const searchBarElement = ref<InstanceType<typeof SearchBar>>();
const page = ref(1);
const itemsPerPage = 6;
const selectedProfiles = ref(new Set<number>());
const hotreload = ref(true);
const modalState = ref<ProfilModalMode>();

// États des filtres
const selectedSkills = ref<number[]>([]);
const selectedExperience = ref<number | null>(null);
const selectedPeriod = ref<{ start: Date; end: Date | number }>({
    start: new Date(),
    end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)),
});

// Bouton de création de profil
const createProfileButton = ref<string>("Créer un profil");
const addSelectionToProject = ref<string>("Ajouter à un projet...");
const clearFilters = ref<string>("Effacer les filtres");
const deselectAll = ref<string>("Tout désélectionner");

// Données des profils (exemple)
const customProfils: CustomProfil[] = [
    {
        profil: { id: 1, nom: "Doe", prenom: "John", experience: 5, site_id: 1, cv_url: "https://www.resumeviking.com/wp-content/uploads/2022/02/Dublin-Resume-Template-Modern.pdf", profile_picture: "https://i.pravatar.cc/300", ville: "Paris" },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
        ],
    },
    {
        profil: { id: 2, nom: "Doe", prenom: "Jane", experience: 3, site_id: 1, cv_url: "https://www.resumeviking.com/wp-content/uploads/2022/02/Moscow-Creative-Resume-Template.pdf", profile_picture: "https://i.pravatar.cc/300", ville: "Lyon" },
        competences: [
            { id: 3, nom: "Vue.js" },
            { id: 4, nom: "React" },
        ],
    },
    {
        profil: { id: 3, nom: "Doe", prenom: "Alice", experience: 2, site_id: 1, cv_url: "https://www.resumeviking.com/wp-content/uploads/2022/02/Cape-Town-Resume-Template-Retro-Creative.pdf", profile_picture: "https://i.pravatar.cc/300", ville: "Marseille" },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 3, nom: "Vue.js" },
        ],
    },
    {
        profil: { id: 4, nom: "Doe", prenom: "Bob", experience: 1, site_id: 1, profile_picture: "https://i.pravatar.cc/300", ville: "Toulouse" },
        competences: [
            { id: 2, nom: "TypeScript" },
            { id: 4, nom: "React" },
        ],
    },
    {
        profil: { id: 5, nom: "Doe", prenom: "Eve", experience: 4, site_id: 1, profile_picture: "https://i.pravatar.cc/300", ville: "Nice" },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 4, nom: "React" },
        ],
    },
    {
        profil: { id: 6, nom: "Doe", prenom: "Charlie", experience: 6, site_id: 1, profile_picture: "https://i.pravatar.cc/300", ville: "Nantes" },
        competences: [
            { id: 2, nom: "TypeScript" },
            { id: 3, nom: "Vue.js" },
        ],
    },
    {
        profil: { id: 7, nom: "Doe", prenom: "David", experience: 7, site_id: 1, profile_picture: "https://i.pravatar.cc/300", ville: "Bordeaux" },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
            { id: 3, nom: "Vue.js" },
            { id: 4, nom: "React" },
        ],
    },
];

// Options des filtres
const experienceOptions = ref<{ value: number; label: string }[]>(customProfils.map((customProfil) => ({ value: customProfil.profil.experience, label: `+${customProfil.profil.experience} ans` })).sort((a, b) => a.value - b.value));
const skillOptions = ref<{ value: number; label: string }[]>([
    { value: 1, label: "JavaScript" },
    { value: 2, label: "TypeScript" },
    { value: 3, label: "Vue.js" },
    { value: 4, label: "React" },
]);

// Filtrage des profils
const filteredCustomProfils = computed(() => {
    return customProfils.filter((customProfil) => {
        // Filtrer par expérience
        if (selectedExperience.value !== null && customProfil.profil.experience < selectedExperience.value) {
            return false;
        }

        // Filtrer par compétences (toutes les compétences sélectionnées doivent être présentes)
        if (selectedSkills.value.length > 0) {
            const profilSkillIds = customProfil.competences.map((comp) => comp.id);
            if (!selectedSkills.value.every((skillId) => profilSkillIds.includes(skillId))) {
                return false;
            }
        }

        return true;
    });
});

// Pagination
const paginatedCustomProfils = computed(() => {
    const start = (page.value - 1) * itemsPerPage;
    return filteredCustomProfils.value.slice(start, start + itemsPerPage);
});

// Gérer la sélection/désélection
const toggleSelection = (id: number) => {
    if (selectedProfiles.value.has(id)) {
        selectedProfiles.value.delete(id);
    } else {
        selectedProfiles.value.add(id);
    }
};

// Mise à jour des filtres
const updateSkills = (skills: number[]) => {
    console.log("Mise à jour des compétences sélectionnées", skills);
    selectedSkills.value = skills;
};

const updateExperience = (experience: number) => {
    console.log("Mise à jour de l'expérience sélectionnée", experience);
    selectedExperience.value = experience;
};

const updatePeriod = (period: { start: Date; end: Date | number }) => {
    console.log("Mise à jour de la période sélectionnée", period);
    selectedPeriod.value = period;
};

// Effacer les filtres
const handleClearFilters = () => {
    console.log("Effacement des filtres...");
    searchBarElement.value?.clearFilters();
    selectedSkills.value = [];
    selectedExperience.value = null;
    selectedPeriod.value = { start: new Date(), end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)) };
};

// Tout désélectionner
const handleUnselectAll = async () => {
    console.log("Désélection de tous les profils...");
    selectedProfiles.value.clear();
    hotreload.value = false;
    await nextTick();
    hotreload.value = true;
};

const handleAddProfiles = () => {
    console.log("Ajout des profils sélectionnés à un projet...");
};

const handleCreateProfile = () => {
    console.log("Création d'un profil...");

    modalState.value = ProfilModalMode.CREATE;

    modal.open(ProfileModal, {
        mode: modalState.value,
        onUpdate: (newMode: ProfilModalMode) => {
            console.log("Mode mis à jour :", newMode);
            modalState.value = newMode;
        },
        onClose() {
            console.log("Fermeture du modal");
            modal.close();
        },
    });
};

const createProfilCompetences = (profilId: number): ProfilCompetences => {
    const profil = customProfils.find((customProfil) => customProfil.profil.id === profilId)!.profil;
    return {
        profil: profil,
        competences: customProfils.find((customProfil) => customProfil.profil.id === profilId)!.competences,
    };
};

const handleShowDetails = (id: number) => {
    console.log("Affichage des détails du profil", id);

    modalState.value = ProfilModalMode.INFO;

    modal.open(ProfileModal, {
        mode: modalState.value,
        profil: createProfilCompetences(id),
        onUpdate: (newMode: ProfilModalMode) => {
            console.log("Mode mis à jour :", newMode);
            modalState.value = newMode;
        },
        onClose() {
            console.log("Fermeture du modal");
            modal.close();
        },
    });
};
</script>
