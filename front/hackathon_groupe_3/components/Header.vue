<template>
    <div class="flex items-center justify-between w-full p-2 border-b">
        <!-- Conteneur du logo -->
        <div class="flex items-center justify-center w-max gap-x-2">
            <img src="../public/assets/images/logo.svg" alt="Logo" class="w-6 h-6" />
            <span class="font-semibold text-2xl text-primary">SkillStack</span>
        </div>

        <!-- Conteneur des liens -->
        <div class="flex items-center justify-center gap-x-10">
            <ULink v-for="link in links" :key="link.to" :to="link.to" as="button" class="flex justify-center items-center gap-x-2" :class="currentRoute != link.to ? 'opacity-40' : ''">
                <UIcon :name="link.icon" class="w-5 h-5 align-middle" />
                {{ link.label }}
            </ULink>
        </div>

        <!-- Conteneur du bouton avec un petit espace de marge sur la droite -->
        <UButton size="md" color="black" variant="solid" class="flex justify-center items-center gap-x-2">
            <!-- Avatar inséré ici -->
            <span>Marion Durant</span>
            <UAvatar alt="Photo de profile" size="sm" class="w-8 h-8" />
        </UButton>
    </div>
</template>

<script setup lang="ts">
import { useRoute } from "vue-router";

const links: { label: string; icon: string; to: string }[] = [
    { label: "Profils", icon: "lucide:users", to: "/profils" },
    { label: "Pools", icon: "material-symbols:filter-alt-outline", to: "/pools" },
    { label: "Projects", icon: "tabler:clipboard-list", to: "/projects" },
];

const route = useRoute();
const currentRoute = ref(route.name);

watchEffect(() => {
    currentRoute.value = route.path;
});
</script>
