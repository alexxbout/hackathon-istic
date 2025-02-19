<template>
    <NuxtLayout>
        <Header v-if="isLogged" />
        <NuxtPage />
    </NuxtLayout>
</template>

<script lang="ts" setup>
// TODO: Update this with real authentication logic
const isLogged = ref(false);
</script>
