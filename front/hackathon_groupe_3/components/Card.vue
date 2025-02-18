<template>
    <div class="flex justify-center items-center bg-gray-100">
        <UCard class="min-w-[400px] border-2 border-transparent group hover:border-green-500 duration-200 cursor-pointer relative" :class="isChecked ? 'border-green-500' : ''" @click="toggleCheckbox">
            <div class="flex items-center justify-between">
                <!-- Avatar + Identitié -->
                <div class="flex items-center justify-center gap-x-2">
                    <UAvatar :src="props.photo" size="3xl" alt="Photo de profil" />
                    <span class="text-lg font-semibold">{{ props.prenom }} {{ props.nom }}</span>
                </div>

                <!-- Checkbox Nuxt UI conditionnellement affichée -->
                <UIcon :name="isChecked ? 'material-symbols:check-circle' : 'material-symbols:check-circle-outline'" class="size-7 duration-200" :class="isChecked ? 'text-green-500' : 'group-hover:text-gray-400 group-hover:opacity-100 opacity-0'"></UIcon>
            </div>

            <!-- Bouton en bas -->
            <UButton color="black" variant="outline" class="mt-6 w-full text-center flex justify-center items-center"> Afficher les détails </UButton>
        </UCard>
    </div>
</template>

<script lang="ts" setup>
import { ref } from "vue";

const props = defineProps<{
    photo: string;
    nom: string;
    prenom: string;
}>();

const isChecked = ref(false);

const toggleCheckbox = () => {
    isChecked.value = !isChecked.value;
};
</script>
