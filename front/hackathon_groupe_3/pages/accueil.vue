<template>
    <div class="p-4 flex justify-center">
        <div class="w-11/12 bg-white shadow-lg rounded-lg p-4">
            <!-- UTabs pour la navigation -->
            <UTabs :items="items" @change="handleTabChange" class="w-full">
                <template #icon="{ item, selected }">
                    <UIcon :name="item.icon" class="w-4 h-4 flex-shrink-0 me-2" :class="[selected && 'text-primary-500 dark:text-primary-400']" />
                </template>
            </UTabs>

            <!-- Contenu pour l'onglet "Liste des utilisateurs" -->
            <div v-if="activeTab === 0" class="mt-4">
                <div class="flex justify-end mb-4 gap-x-2">
                    <CreateUser />
                    <DeleteUser :selectedRows="selectedRows" @delete-users="handleDeleteUsers" />
                </div>
                <TableUser @update:selectedRows="selectedRows = $event" />
            </div>

            <div v-if="activeTab === 1" class="mt-4">
                <div class="flex justify-end mb-4 gap-x-2">
                    <UButton variant="soft" color="gray" icon="lucide:user-plus">Créer un nouveau profil</UButton>
                    <UButton variant="soft" color="red" icon="ic:baseline-delete"> Supprimer un profil </UButton>
                </div>
                <TableProfils />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import TableUser from "~/components/TableUsers.vue";
import DeleteUser from "~/components/DeleteUser.vue";
import CreateUser from "~/components/CreateUser.vue";

// État pour suivre l'onglet actif
const activeTab = ref(0);

// Données des onglets
const items = [
    {
        label: "Consulter la liste des utilisateurs",
        icon: "fa6-solid:users",
    },
    {
        label: "Consulter la liste des profils",
        icon: "tabler:users",
    },
];
const handleTabChange = (newTabIndex) => {
    activeTab.value = newTabIndex;
};

// Logique pour la gestion des utilisateurs
const selectedRows = ref([]);

const handleDeleteUsers = (usersToDelete) => {
    if (usersToDelete.length > 0) {
        console.log("Utilisateurs à supprimer :", usersToDelete);
        alert(`${usersToDelete.length} utilisateur(s) supprimé(s).`);
    } else {
        alert("Veuillez sélectionner au moins un utilisateur à supprimer.");
    }
};

const deleteUsers = (usersToDelete) => {
    data.value = data.value.filter((user) => !usersToDelete.includes(user));
    selectedRows.value = [];
};

defineExpose({ deleteUsers });
</script>
