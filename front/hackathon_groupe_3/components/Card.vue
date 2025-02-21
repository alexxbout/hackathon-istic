<template>
    <UCard
        class="min-w-[400px] border-2 border-transparent group duration-200 relative"
        :class="{
            'cursor-pointer hover:border-green-500': ['cdp', 'admin'].includes(props.role),
            'border-green-500': isChecked && ['cdp', 'admin'].includes(props.role),
        }"
        @click="toggleCheckbox">
        <div class="flex items-center justify-between">
            <!-- Avatar + Identitié -->
            <div class="flex items-center justify-center gap-x-2">
                <UAvatar :src="props.photo" size="lg" alt="Photo de profil" />
                <div class="flex flex-col">
                    <span class="text-lg font-semibold">{{ props.prenom }} {{ props.nom }}</span>
                    <UBadge variant="soft" :ui="{ rounded: 'rounded-full' }" :color="getColor(props.experience)" class="w-fit">
                        {{ getExperienceLabel(props.experience) }}
                    </UBadge>
                </div>
            </div>

            <!-- Checkbox Nuxt UI conditionnellement affichée -->
            <UIcon v-show="props.role != 'rh'" :name="isChecked ? 'material-symbols:check-circle' : 'material-symbols:check-circle-outline'" class="size-7 duration-200" :class="isChecked ? 'text-green-500' : 'group-hover:text-gray-400 group-hover:opacity-100 opacity-0'"></UIcon>
        </div>

        <!-- Bouton en bas -->
        <UButton @click.stop="handleShowDetails" color="black" variant="outline" class="mt-6 w-full text-center flex justify-center items-center">Afficher les détails</UButton>
    </UCard>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import type { TypeRole } from "../types/roles";

const props = defineProps<{
    id: number;
    photo: string;
    nom: string;
    prenom: string;
    role: TypeRole;
    isChecked?: boolean;
    experience: number;
}>();

const isChecked = ref(props.isChecked ?? false);

const toggleCheckbox = () => {
    if (props.role != "rh") {
        isChecked.value = !isChecked.value;
    }
};

const getColor = (experience: number) => {
    if (experience > 5) return "yellow";
    else if (experience >= 3) return "red";
    return "green";
};

const getExperienceLabel = (experience: number) => {
    if (experience > 5) return "Sénior";
    else if (experience >= 3) return "Confirmé";
    return "Junior";
};

const emits = defineEmits<{
    (event: "@click", id: number): void;
}>();

const handleShowDetails = () => {
    emits("@click", props.id);
};
</script>
