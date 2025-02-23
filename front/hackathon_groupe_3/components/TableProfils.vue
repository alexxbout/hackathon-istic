<template>
    <div class="flex flex-col space-y-4">
        <!-- Barre de recherche -->
        <div class="flex justify-end space-x-4">
            <UInput v-model="searchQuery" icon="i-heroicons-magnifying-glass-20-solid" size="sm" color="white" placeholder="Rechercher..." class="w-1/6" />
        </div>

        <!-- Tableau avec sélection de lignes -->
        <UTable v-model="selectedRows" :rows="paginatedRows" :columns="columns" :sort="{ column: sortColumn, direction: sortDirection }" @sort="handleSort" selectable="multiple" class="w-full">
            <!-- Slot personnalisé pour l'image de profil -->
            <template #photo-data="{ row }">
                <UAvatar size="lg" :src="row.profile_picture" :alt="row.nom + row.prenom" />
            </template>

            <!-- Slot personnalisé pour le CV -->
            <template #cv_url-data="{ row }">
                <a :href="row.cv_url" target="_blank" class="text-blue-600 underline">Voir CV</a>
            </template>
        </UTable>

        <!-- Pagination -->
        <div v-if="pageCount > 1" class="flex justify-center px-3 py-3.5 border-t border-gray-200 dark:border-gray-700">
            <UPagination v-model="page" :page-count="pageCount" :total="filteredRows.length" size="xl" />
        </div>
    </div>
</template>

<script setup lang="ts">
import { computed, onMounted, ref } from "vue";
import type { Profil } from "~/types/entities";
import { APIUtils } from "~/types/utilsApi";

const profils = ref<Profil[]>([]);

onMounted(async () => {
    APIUtils.getProfils().then((response) => {
        console.log(response.data);

        profils.value.push(...response.data);

        // Fake profile picture
        profils.value.forEach((profil) => {
            profil.profile_picture = `https://i.pravatar.cc/150?img=${profil.id}`;
        });
    });
});

const columns = ref([
    { key: "id", label: "ID", sortable: true },
    { key: "photo", label: "Photo de profil" },
    { key: "nom", label: "Nom", sortable: true },
    { key: "prenom", label: "Prénom", sortable: true },
    { key: "experience", label: "Expérience (années)", sortable: true },
    { key: "ville", label: "Ville", sortable: true },
    { key: "cv_url", label: "CV" },
]);

const sortColumn = ref<keyof Profil>("id");
const sortDirection = ref<"asc" | "desc">("asc");
const handleSort = (column: string, direction: "asc" | "desc") => {
    sortColumn.value = column as keyof Profil;
    sortDirection.value = direction;
};

const searchQuery = ref("");
const filteredRows = computed(() => {
    return profils.value
        .filter((profil) => [profil.nom, profil.prenom].some((value) => value.toLowerCase().includes(searchQuery.value.toLowerCase())))
        .sort((a, b) => {
            const factor = sortDirection.value === "asc" ? 1 : -1;
            return (a[sortColumn.value] as any) > (b[sortColumn.value] as any) ? factor : -factor;
        });
});

const itemsPerPage = 10;
const page = ref(1);
const paginatedRows = computed(() => {
    const start = (page.value - 1) * itemsPerPage;
    return filteredRows.value.slice(start, start + itemsPerPage);
});
const pageCount = computed(() => Math.ceil(filteredRows.value.length / itemsPerPage));
const selectedRows = ref<Profil[]>([]);
</script>

<style scoped>
.UTable th,
.UTable td {
    border: 1px solid #e2e8f0;
    padding: 8px;
}
.UTable {
    border-collapse: collapse;
}
</style>
