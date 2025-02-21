<template>
    <UButton @click="handleDeleteUsers" variant="soft" color="red" icon="ic:baseline-delete"> Supprimer un utilisateur </UButton>
</template>

<script setup lang="ts">
// Props pour recevoir les lignes sélectionnées
const props = defineProps({
    selectedRows: {
        type: Array,
        required: true,
        default: () => [], // Valeur par défaut pour éviter les erreurs
    },
});

// Émettre un événement pour informer le parent de la suppression
const emit = defineEmits(["delete-users"]);

const handleDeleteUsers = async () => {
    if (props.selectedRows.length > 0) {
        try {
            for (const user of props.selectedRows) {
                // await APIUtils.deleteUser(user.id); // Appel API pour supprimer un utilisateur par ID
            }
            emit("delete-users", props.selectedRows);
            alert(`${props.selectedRows.length} utilisateur(s) supprimé(s).`);
        } catch (error) {
            console.error("Erreur lors de la suppression de l'utilisateur :", error);
            alert("Une erreur est survenue lors de la suppression.");
        }
    } else {
        alert("Veuillez sélectionner au moins un utilisateur à supprimer.");
    }
};
</script>