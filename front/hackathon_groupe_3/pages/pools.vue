<template>
    <div class="flex flex-col min-h-screen">
        <!-- Contenu principal -->
        <div class="flex flex-col items-center flex-grow py-10 gap-y-5 mx-80">
            <!-- Header -->
            <div class="flex items-center justify-between w-full">
                <div class="flex items-center gap-x-2">
                    <span class="text-3xl font-medium">Pools</span>
                    <span class="text-2xl self-end">({{ poolsWithCompetences.length }})</span>
                </div>
                <div class="flex items-center gap-x-2">
                    <UButton variant="soft" color="green" icon="lucide:user-plus" @click="openCreateModal"> Créer un nouveau pool </UButton>

                    <PoolModal v-model="modal" :id="newPool.id" :user_id="-1" :nom="newPool.nom" :competences="newPool.competences" :experience="newPool.experience" @updatePool="updatePool" @createPool="createPool" @close="closeModal" />
                </div>
            </div>

            <!-- Grid des pools -->
            <div v-if="hotreload" class="grid grid-cols-2 gap-4 w-full">
                <CardPool v-for="poolItem in paginatedPools" :id="poolItem.id" :key="poolItem.id" :user_id="-1" :competences="poolItem.competences" :experience="poolItem.experience" :nom="poolItem.nom" :poolId="poolItem.id" @click="toggleSelection(poolItem.id!)" @poolUpdated="updatePool" @deletePool="deletePool" @usePool="handlePoolSelection" />
            </div>

            <!-- Espace vide pour pousser la pagination en bas -->
            <div class="flex-grow"></div>
        </div>

        <!-- Pagination en bas -->
        <div class="flex justify-center pb-6">
            <UPagination v-show="poolsWithCompetences.length > itemsPerPage" v-model="page" size="lg" :total="poolsWithCompetences.length" :page-count="itemsPerPage" />
        </div>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import PoolModal from "~/components/PoolModal.vue";
import type { Pool } from "~/types/entities";

// definePageMeta({
//   middleware: "auth",
// });

const modal = ref(false);
const hotreload = ref(true);
const page = ref(1);
const itemsPerPage = 6;
const newPool = ref<Pool>({
    id: -1,
    user_id: -1,
    nom: "",
    experience: 0,
    competences: [],
});

const poolsWithCompetences = ref<Pool[]>([
    {
        id: 1,
        user_id: 101,
        nom: "Backend Experts",
        experience: 5,
        competences: [
            { id: 5, nom: "Node.js" },
            { id: 6, nom: "Python" },
            { id: 7, nom: "Django" },
        ],
    },
    {
        id: 2,
        user_id: 102,
        nom: "Frontend Masters",
        experience: 3,
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
            { id: 3, nom: "Vue.js" },
            { id: 4, nom: "React" },
        ],
    },
    {
        id: 3,
        user_id: 103,
        nom: "Fullstack Wizards",
        experience: 7,
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
            { id: 5, nom: "Node.js" },
            { id: 6, nom: "Python" },
        ],
    },
    {
        id: 4,
        user_id: 104,
        nom: "Data Scientists",
        experience: 6,
        competences: [
            { id: 6, nom: "Python" },
            { id: 8, nom: "Flask" },
            { id: 9, nom: "Machine Learning" },
        ],
    },
    {
        id: 5,
        user_id: 105,
        nom: "DevOps Gurus",
        experience: 4,
        competences: [
            { id: 10, nom: "Kubernetes" },
            { id: 11, nom: "Docker" },
            { id: 12, nom: "AWS" },
            { id: 13, nom: "Azure" },
        ],
    },
    {
        id: 6,
        user_id: 106,
        nom: "Cloud Engineers",
        experience: 5,
        competences: [
            { id: 12, nom: "AWS" },
            { id: 13, nom: "Azure" },
            { id: 11, nom: "Docker" },
        ],
    },
    {
        id: 7,
        user_id: 107,
        nom: "Cybersecurity Specialists",
        experience: 8,
        competences: [
            { id: 14, nom: "Cybersecurity" },
            { id: 15, nom: "Penetration Testing" },
        ],
    },
    {
        id: 8,
        user_id: 108,
        nom: "AI Researchers",
        experience: 7,
        competences: [
            { id: 9, nom: "Machine Learning" },
            { id: 16, nom: "Deep Learning" },
            { id: 17, nom: "TensorFlow" },
        ],
    },
]);
const paginatedPools = computed(() => {
    const start = (page.value - 1) * itemsPerPage;
    return poolsWithCompetences.value.slice(start, start + itemsPerPage);
});

const selectedPools = ref(new Set<number>());

const selectedCompetences = ref<number[]>([]);

const toggleSelection = (id: number) => {
    if (selectedPools.value.has(id)) {
        selectedPools.value.delete(id);
    } else {
        selectedPools.value.add(id);
    }
};

const openCreateModal = () => {
    modal.value = true;
};

const closeModal = () => {
    modal.value = false;
};

function updatePool(updatedPool: Pool) {
    const poolIndex = poolsWithCompetences.value.findIndex((p) => p.id === updatedPool.id);
    if (poolIndex !== -1) {
        poolsWithCompetences.value[poolIndex].nom = updatedPool.nom;
        poolsWithCompetences.value[poolIndex].experience = updatedPool.experience;
        poolsWithCompetences.value[poolIndex].competences = updatedPool.competences;

        hotreload.value = false;
        setTimeout(() => (hotreload.value = true), 10);
    }
}

function deletePool(poolId: number) {
    console.log("test" + poolId);
    poolsWithCompetences.value = poolsWithCompetences.value.filter((p) => p.id !== poolId);
    if (page.value > Math.ceil(poolsWithCompetences.value.length / itemsPerPage)) {
        page.value = 1;
    }
}

function createPool(newPool: Pool) {
    const poolId = poolsWithCompetences.value.length + 1;

    const pool = {
        id: poolId,
        user_id: Math.floor(Math.random() * 1000),
        nom: newPool.nom,
        experience: newPool.experience,
        competences: newPool.competences,
    };

    poolsWithCompetences.value.push(pool);

    // Ferme la modal après la création
    modal.value = false;
}

function handlePoolSelection() {
    //get competences asscociated with selected pool
    // selectedCompetences.value = poolsWithCompetences.value.map(poolItem => {
    //   if (poolItem.pool.id) {
    //     if (selectedPools.value.has(poolItem.pool.id)) {
    //       return poolItem.competences.map(competence => competence.id);
    //     }
    //   }
    //   return [];
    // }).flat();
    console.log(selectedCompetences.value);
    localStorage.setItem("selectedCompetences", JSON.stringify(selectedCompetences.value));
    //set to local storage the experience ofpools selected
    localStorage.setItem("selectedPools", JSON.stringify(poolsWithCompetences.value.filter((poolItem) => selectedPools.value.has(poolItem.id!)).map((poolItem) => poolItem.experience)));
    //navigateTo('/profils');
    //TODO fix navigate
}
</script>
