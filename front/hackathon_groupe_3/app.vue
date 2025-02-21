<template>
    <NuxtLayout>
        <Header v-if="isLogged" />
        <NuxtPage />
        <UModals />
        <UNotifications />
    </NuxtLayout>
</template>

<script setup>
import { useRouter } from "vue-router";

definePageMeta({
    middleware: "auth",
});

const router = useRouter();
const authToken = useCookie("auth_token"); // Vérifie si l'utilisateur est connecté
const isLogged = computed(() => !!authToken.value);

// Vérifie la route actuelle et redirige si nécessaire
onMounted(() => {
    if (!isLogged.value && router.currentRoute.value.path === "/") {
        router.push("/login");
    }
});
</script>