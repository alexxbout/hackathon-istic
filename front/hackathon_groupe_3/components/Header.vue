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
        <div class="flex items-center gap-x-4">
            <UButton size="xl" color="black" variant="outline" :ui="{ rounded: 'rounded-full' }" disabled class="cursor-not-allowed opacity-60">
                {{ role === "rh" ? "RH" : "Chef de projet" }}
            </UButton>
            <UPopover>
                <UButton size="md" color="black" variant="solid" class="flex justify-center items-center gap-x-2">
                    <span>Marion Durant</span>
                    <UAvatar alt="Photo de profil" size="sm" class="w-8 h-8" />
                </UButton>
                <template #panel>
                    <div class="bg-white p-2 flex flex-col gap-y-2">
                        <UButton color="gray" variant="soft" class="flex items-center gap-x-2 rounded-md hover:bg-gray-100">
                            <ULink to="/login" class="flex items-center gap-x-2 rounded-md text-red-600 hover:text-red-800">
                                <UIcon name="heroicons:arrow-left-end-on-rectangle-solid" class="w-5 h-5" />
                                <span class="font-semibold">Déconnexion</span>
                            </ULink>
                        </UButton>

                        <!--Change color mode-->
                        <UButton :label="isDark ? 'Dark' : 'Light'" :icon="isDark ? 'i-heroicons-moon-20-solid' : 'i-heroicons-sun-20-solid'" color="gray" variant="soft" class="flex items-center gap-x-2 rounded-md hover:bg-gray-100" @click="isDark = !isDark" />
                    </div>
                </template>
            </UPopover>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRoute } from "vue-router";
import type { Role } from "../types/roles";

const links: { label: string; icon: string; to: string }[] = [
    { label: "Profils", icon: "lucide:users", to: "/profils" },
    { label: "Pools", icon: "material-symbols:filter-alt-outline", to: "/pools" },
    { label: "Projects", icon: "tabler:clipboard-list", to: "/projects" },
];

const role = ref<Role>("rh");

const route = useRoute();
const currentRoute = ref(route.name);

watchEffect(() => {
    currentRoute.value = route.path;
});
const colorMode = useColorMode();
const isDark = computed({
    get() {
        return colorMode.value === "dark";
    },
    set() {
        colorMode.preference = colorMode.value === "dark" ? "light" : "dark";
    },
});
</script>
