<template>
    <div class="h-screen w-screen flex flex-col">
        <Header />
        <div class="flex items-center justify-center w-full h-full">
            <div class="flex flex-col items-center w-full h-full py-10 gap-y-5 mx-80">
                <!-- Header -->
                <div class="flex items-center justify-between w-full">
                    <div class="flex items-center justify-center gap-x-2">
                        <span class="text-3xl font-medium">Profils</span>
                        <span class="text-2xl self-end">({{ filteredCustomProfils.length }})</span>
                    </div>
                    <div class="flex items-center justify-center w-max gap-x-2">
                        <UButton v-if="role != 'cdp'" color="gray" icon="lucide:user-plus" variant="soft" @click="handleCreateProfile">{{ createProfileButton }} </UButton>
                        <UButton v-if="role != 'rh'" :disabled="!selectedProfiles.size" color="green" icon="lucide:mouse-pointer-click" trailing-icon="" variant="soft" @click="handleAddProfiles">{{ addSelectionToProject }} </UButton>
                        <UButton color="red" icon="lucide:circle-x" variant="soft" @click="handleClearFilters">{{ clearFilters }} </UButton>
                        <UButton v-if="role != 'rh'" color="yellow" icon="lucide:circle-slash-2" variant="soft" @click="handleUnselectAll">{{ deselectAll }} </UButton>
                    </div>
                </div>
    <div class="h-screen w-screen flex flex-col">
        <Header />
        <div class="flex items-center justify-center w-full h-full">
            <div class="flex flex-col items-center w-full h-full py-10 gap-y-5 mx-80">
                <!-- Header -->
                <div class="flex items-center justify-between w-full">
                    <div class="flex items-center justify-center gap-x-2">
                        <span class="text-3xl font-medium">Profils</span>
                        <span class="text-2xl self-end">({{ filteredCustomProfils.length }})</span>
                    </div>
                    <div class="flex items-center justify-center w-max gap-x-2">
                        <UButton v-if="role != 'cdp'" color="gray" icon="lucide:user-plus" variant="soft" @click="handleCreateProfile">{{ createProfileButton }} </UButton>
                        <UButton v-if="role != 'rh'" :disabled="!selectedProfiles.size" color="green" icon="lucide:mouse-pointer-click" trailing-icon="" variant="soft" @click="handleAddProfiles">{{ addSelectionToProject }} </UButton>
                        <UButton color="red" icon="lucide:circle-x" variant="soft" @click="handleClearFilters">{{ clearFilters }} </UButton>
                        <UButton v-if="role != 'rh'" color="yellow" icon="lucide:circle-slash-2" variant="soft" @click="handleUnselectAll">{{ deselectAll }} </UButton>
                    </div>
                </div>

                <!-- Searchbar -->
                <SearchBar v-model:experienceOptions="experienceOptions" ref="searchBarElement" :role="role" :skillOptions="skillOptions" @updateExperience="updateExperience" @updatePeriod="updatePeriod" @updateSkills="updateSkills" />

                <div v-if="hotreload" class="grid grid-cols-2 grid-rows-3 gap-2 content-between w-full">
                    <CardProfil v-for="profil in paginatedCustomProfils" :id="profil.id!" :key="profil.id" :experience="profil.experience" :isChecked="selectedProfiles.has(profil.id!)" :nom="profil.nom" :photo="profil.profile_picture" :prenom="profil.prenom" :role="role" @click="toggleSelection(profil.id!)" @@click="handleShowDetails(profil.id!)" />
                </div>

                <UPagination v-show="filteredCustomProfils.length > 6" v-model="page" :page-count="6" :total="filteredCustomProfils.length" size="xl" />
            </div>
        </div>
    </div>
                <UPagination v-show="filteredCustomProfils.length > 6" v-model="page" :page-count="6" :total="filteredCustomProfils.length" size="xl" />
            </div>
        </div>
    </div>
</template>

<script lang="ts" setup>
import { computed, nextTick, ref } from "vue";
import { computed, nextTick, ref } from "vue";
import AddProfillProjectModal from "~/components/modals/AddProfillProjectModal.vue";
import ProfileModal from "~/components/modals/ProfileModal.vue";
import type SearchBar from "~/components/SearchBar.vue";
import type { Profil, ProfilCompetences } from "~/types/entities";
import { ProfilModalMode } from "~/types/modals";
import type { TypeRole } from "~/types/roles";
import { APIUtils } from "~/types/utilsApi";

definePageMeta({
    middleware: "auth",
    middleware: "auth",
});

const role = ref<TypeRole>(useCookie<TypeRole>("role").value);

const role = ref<TypeRole>(useCookie<TypeRole>("role").value);

const modal = useModal();

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
    start: new Date(),
    end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)),
});

// Bouton de création de profil
const createProfileButton = ref<string>("Créer un profil");
const addSelectionToProject = ref<string>("Ajouter à un projet...");
const clearFilters = ref<string>("Effacer les filtres");
const deselectAll = ref<string>("Tout désélectionner");

// Données des profils (exemple)
const profils = ref<Profil[]>([]);

// Options des filtres
const experienceOptions = computed<{ value: number; label: string }[]>(() => 
	Array.from(
		new Set(
			profils.value.map((profil) => profil.experience)
		)
	)
	.map((experience) => ({
		value: experience,
		label: `+${experience} ans`,
	}))
	.sort((a, b) => a.value - b.value)
);

const skillOptions = ref<{ value: number; label: string }[]>([]);

// Filtrage des profils
const filteredCustomProfils = computed(() => {
    return profils.value.filter((profil) => {
        // Filtrer par expérience
        if (selectedExperience.value !== null && profil.experience < selectedExperience.value) {
            return false;
        }

        // Filtrer par compétences (toutes les compétences sélectionnées doivent être présentes)
        if (selectedSkills.value.length > 0) {
            const profilSkillIds = profil.competences.map((comp) => comp.id);
            if (!selectedSkills.value.every((skillId) => profilSkillIds.includes(skillId))) {
                return false;
            }
        }
        // Filtrer par compétences (toutes les compétences sélectionnées doivent être présentes)
        if (selectedSkills.value.length > 0) {
            const profilSkillIds = profil.competences.map((comp) => comp.id);
            if (!selectedSkills.value.every((skillId) => profilSkillIds.includes(skillId))) {
                return false;
            }
        }

        return true;
    });
        return true;
    });
});

// Pagination
const paginatedCustomProfils = computed(() => {
    const start = (page.value - 1) * itemsPerPage;
    return filteredCustomProfils.value.slice(start, start + itemsPerPage);
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
    console.log(selectedSkills.value);
    console.log("Mise à jour des compétences sélectionnées", skills);
    selectedSkills.value = skills;
    console.log(selectedSkills.value);
};

const updateExperience = (experience: number) => {
    console.log("Mise à jour de l'expérience sélectionnée", experience);
    selectedExperience.value = experience;
    console.log(selectedExperience.value);
    console.log("Mise à jour de l'expérience sélectionnée", experience);
    selectedExperience.value = experience;
    console.log(selectedExperience.value);
};

const updatePeriod = (period: { start: Date; end: Date | number }) => {
    console.log("Mise à jour de la période sélectionnée", period);
    selectedPeriod.value = period;
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
    console.log("Effacement des filtres...");
    searchBarElement.value?.clearFilters();
    selectedSkills.value = [];
    selectedExperience.value = null;
    selectedPeriod.value = { start: new Date(), end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)) };
};

// Tout désélectionner
const handleUnselectAll = async () => {
    console.log("Désélection de tous les profils.value...");
    selectedProfiles.value.clear();
    hotreload.value = false;
    await nextTick();
    hotreload.value = true;
};

const handleAddProfiles = () => {
    console.log("Ajout des profils sélectionnés à un projet...");
    console.log("Ajout des profils sélectionnés à un projet...");

    modal.open(AddProfillProjectModal, {
        profiles: profils.value,
        onAssign: () => {
            console.log("Assignation des profils à un projet...");
            modal.close();
        },
        onClose() {
            console.log("Fermeture du modal");
            modal.close();
        },
    });
};

const handleCreateProfile = () => {
    console.log("Création d'un profil...");
    console.log("Création d'un profil...");

    modalState.value = ProfilModalMode.CREATE;
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
    const profil = profils.value.find((profil) => profil.id === profilId)!;
    return {
        profil: profil,
        competences: profils.value.find((profil) => profil.id === profilId)!.competences,
    };
};

const handleShowDetails = (id: number) => {
    console.log("Affichage des détails du profil", id);
    console.log("Affichage des détails du profil", id);

    modalState.value = ProfilModalMode.INFO;
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

onMounted(() => {
    console.log("Fetching profils.value...");

    APIUtils.getProfils().then((response) => {
        console.log(response.data);

        profils.value.push(...response.data);
    });

	APIUtils.getCompetences().then((response) => {
        console.log(response.data);

		const data = response.data as { id: number, nom: string }[];

		skillOptions.value.push(...data.map((competence) => ({ value: competence.id, label: competence.nom })));
    });
});
</script>
