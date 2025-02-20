<template>
    <NuxtLayout>
        <Header v-if="isLogged" />
        <NuxtPage />
        <UModals />
        <UNotifications />
    </NuxtLayout>
</template>

<script lang="ts" setup>
// TODO: Update this with real authentication logic
const isLogged = ref(true);
</script>
