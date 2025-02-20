<template>
    <div class="flex items-center justify-center w-full h-full">
        <div class="flex flex-col items-center justify-center w-full h-full py-10 gap-y-5 mx-80">
            <!-- Header -->
            <div class="flex items-center justify-between w-full">
                <div class="flex items-center justify-center gap-x-2">
                    <span class="text-3xl font-medium">Projets</span>
                    <span class="text-2xl self-end">({{ projets.length }})</span>
                </div>
                <div class="flex items-center justify-center w-max gap-x-2">
                    <UButton v-if="role != 'rh'" color="primary" icon="ic:baseline-add-circle-outline" variant="soft" @click="handleOpenModal">Créer un nouveau projet</UButton>
                </div>
            </div>

            <!-- Single Project View -->
            <div v-if="selectedProject.id" class="w-full">
                <UButton class="mb-4" color="gray" icon="lucide:arrow-left" variant="soft" @click="clearSelectedProject"> Retour à la liste </UButton>
                <CardProjet :date_debut="selectedProject.date_debut" :date_fin="selectedProject.date_fin" :nom="selectedProject.nom" :role="role" class="w-full" />
            </div>

            <!-- Liste des projets -->
            <div v-else class="grid grid-cols-2 grid-rows-3 gap-2 content-between w-full">
                <CardProjet v-for="projet in paginatedProjets" :key="projet.id" :date_debut="projet.date_debut" :date_fin="projet.date_fin" :nom="projet.nom" :role="role" />
            </div>

            <!--Pagination-->
            <UPagination v-show="projets.length > itemsPerPage" v-model="page" :page-count="itemsPerPage" :total="projets.length" size="xl" />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import ProjectModal from "~/components/modals/ProjectModal.vue";
import type { Projet } from "~/types/entities";
import type { Role } from "~/types/roles";

const toast = useToast();
const modal = useModal();

// TODO: Fetch projets from API
const projets = ref([
    { id: 1, nom: "Développement Web", date_debut: new Date(2022, 1, 1), date_fin: new Date(2022, 12, 31) },
    { id: 2, nom: "Application Mobile", date_debut: new Date(2023, 1, 1), date_fin: new Date(2023, 12, 31) },
    { id: 3, nom: "Système de Gestion", date_debut: new Date(2024, 1, 1), date_fin: new Date(2024, 12, 31) },
    { id: 4, nom: "Analyse de Données", date_debut: new Date(2025, 1, 1), date_fin: new Date(2025, 12, 31) },
    { id: 5, nom: "Intelligence Artificielle", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 6, nom: "Sécurité Informatique", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 7, nom: "Réseaux et Télécommunications", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 8, nom: "Cloud Computing", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 9, nom: "Blockchain", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 10, nom: "IoT", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
    { id: 11, nom: "Big Data", date_debut: new Date(2026, 1, 1), date_fin: new Date(2026, 12, 31) },
]);

const role = ref<Role>("cdp");

const page = ref(1);

const itemsPerPage = ref(8);

const isClicked = ref(false);

const selectedProject = ref<Projet>({
    id: 0,
    nom: "Sélectionner un projet",
    date_debut: new Date(),
    date_fin: new Date(),
});

const paginatedProjets = computed(() => {
    const start = (page.value - 1) * itemsPerPage.value;
    return projets.value.slice(start, start + itemsPerPage.value);
});

const clearSelectedProject = () => {
    selectedProject.value = { id: 0, nom: "", date_debut: new Date(), date_fin: new Date() };
};

const handleCreateProject = () => {
    toast.add({ title: "Projet créé", description: "Le projet a été créé avec succès", color: "green" });

    isClicked.value = false;
};

const handleOpenModal = () => {
    modal.open(ProjectModal, {
        onCreate(project: Projet) {
            // TODO: Call API to create project
            // TODO: Check that project data is valid (empty name, invalid dates, etc.)

            console.log("Création du projet", project);
            handleCreateProject();

            modal.close();
        },
        onClose() {
            console.log("Fermeture du modal");
            modal.close();
        },
    });
};
</script>
