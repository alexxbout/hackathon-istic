<template>
    <NuxtLayout>
        <NuxtPage />
        <UModals />
        <UNotifications />
    </NuxtLayout>
</template>

<script setup lang="ts">
const { fetchSession } = useAuth();
import { onMounted } from "vue";

onMounted(() => {
    fetchSession();
});
</script>
