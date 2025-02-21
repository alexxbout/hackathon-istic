<template>
  <div class="flex flex-col space-y-4">
    <!-- Barre de recherche et filtre -->
    <div class="flex justify-end space-x-4">
      <!-- Barre de recherche alignée à droite -->
      <UInput
          v-model="searchQuery"
          icon="i-heroicons-magnifying-glass-20-solid"
          size="sm"
          color="white"
          placeholder="Rechercher..."
          class="w-1/6"
      />
    </div>

    <!-- Tableau avec sélection de lignes -->
    <UTable
        v-model="selectedRows"
        :rows="paginatedRows"
        :columns="columns"
        :sort="{ column: sortColumn, direction: sortDirection }"
        @sort="handleSort"
        selectable="multiple"
        class="w-full"
    >
      <!-- Slot personnalisé pour la colonne "Rôle" -->
      <template #role-data="{ row }">
        <UBadge
            :icon="getRoleConfig(row.role).icon"
            :color="getRoleConfig(row.role).color"
            :label="row.role"
        />
      </template>
    </UTable>

    <!-- Pagination -->
    <div v-if="pageCount > 1" class="flex justify-center px-3 py-3.5 border-t border-gray-200 dark:border-gray-700">
      <UPagination v-model="page" :page-count="pageCount" :total="filteredRows.length" size="xl" />
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { RoleConfig } from "~/types/roles";
import { APIUtils } from "~/types/utilsApi";

const users = ref([]);

const fetchUsers = async () => {
  try {
    const response = await APIUtils.getUsers();
    users.value = response.data;
  } catch (error) {
    console.error("Erreur lors de la récupération des utilisateurs", error);
  }
};

onMounted(fetchUsers);

const columns = ref([
  { key: "id", label: "ID", sortable: true },
  { key: "role", label: "Rôle", sortable: true },
  { key: "email", label: "Email", sortable: true },
  { key: "nom", label: "Nom", sortable: true },
  { key: "prenom", label: "Prénom", sortable: true }
]);

const sortColumn = ref("id");
const sortDirection = ref<"asc" | "desc">("asc");
const handleSort = (column: string, direction: "asc" | "desc") => {
  sortColumn.value = column;
  sortDirection.value = direction;
};

const searchQuery = ref("");
const selectedRole = ref("");
const getRoleConfig = (role) => RoleConfig[role] || { icon: "i-heroicons-question-mark-circle", color: "gray" };

const filteredRows = computed(() => {
  return users.value
      .filter(user =>
          Object.values(user).some(value =>
              value.toString().toLowerCase().includes(searchQuery.value.toLowerCase())
          )
      )
      .filter(user => (selectedRole.value ? user.role === selectedRole.value : true))
      .sort((a, b) => {
        const factor = sortDirection.value === "asc" ? 1 : -1;
        return a[sortColumn.value] > b[sortColumn.value] ? factor : -factor;
      });
});

const itemsPerPage = 10;
const page = ref(1);
const paginatedRows = computed(() => {
  const start = (page.value - 1) * itemsPerPage;
  return filteredRows.value.slice(start, start + itemsPerPage);
});
const pageCount = computed(() => Math.ceil(filteredRows.value.length / itemsPerPage));
const selectedRows = ref([]);
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
