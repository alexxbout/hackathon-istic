<template>
    <div class="flex justify-center items-center min-h-screen bg-gray-100">
        <UCard class="min-w-[500px] border-2  border-gray-200 group duration-200 relative p-4  shadow-lg rounded-lg">
            <template #header>
                <Placeholder class="h-8">  
                    <span class="text-xl font-semibold text-left">{{ props.nom }}</span>
                </Placeholder>
            </template>

            <div class="mt-0 p-2 border border-gray-300  rounded-lg w-full" style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1)">
                <p class="text-md font-semibold">Compétences : </p>
                <div class="flex flex-wrap gap-1 mt-2">
                    <UBadge 
                        v-for="(competence, index) in props.competences" 
                        :key="index" 
                        color="white" 
                        :ui="{
                            color: {
                                white: {
                                    solid: badgeColors[index] 
                                }
                            }
                        }" 
                        size="xs" 
                        variant="solid"
                    >
                        {{ competence.nom }}
                    </UBadge>
                </div>

                <p class="text-md font-semibold mt-2">Expériences : </p>
                <p class="text-xs font-semibold mt-2">+{{ props.experience }} ans</p>
            </div>

            <div class="flex gap-2 mt-8 w-full">

                <!-- Bouton pour Accéder aux détails -->
                <UButton 
                    @click="handleAccessDetails(poolId)" 
                    size="md" color="green" variant="solid"
                    :ui="{ rounded: 'rounded-full' }"
                    class="w-1/2 text-center flex justify-center items-center border-2 border-black bg-white text-black hover:border-green-500 hover:text-green-500 hover:bg-white">
                    <UIcon name="ix:eye-filled" class="w-5 h-5" />
                    Accéder aux détails
                </UButton>

                <!-- Bouton pour Utiliser le pool -->
                <UButton 
                    @click="handleUsePool(poolId)" 
                    size="md" color="green" variant="solid"
                    :ui="{ rounded: 'rounded-full' }"
                    class="w-1/2 text-center flex justify-center items-center border-2 border-black bg-white text-black hover:text-green-500 hover:border-green-400 hover:bg-white">
                    <UIcon name="ix:certificate-success-filled" class="w-5 h-5" />
                    Utiliser ce pool
                </UButton>
            </div>

        </UCard>
    </div>
</template>

<script lang="ts" setup>
import { ref, onMounted } from 'vue';

const props = defineProps<{
    competences: { nom: string }[]; 
    experience: number;
    nom: string;
    isChecked?: boolean;
}>();

const poolId = 1; 
const emits = defineEmits<{
    (e: "accessDetails", poolId: number): void;
    (e: "usePool", poolId: number): void;
}>();

function handleAccessDetails(poolId: number) {
    console.log("Accessing details for pool with ID:", poolId);
    emits("accessDetails", poolId); 
}

function handleUsePool(poolId: number) {
    console.log("Using pool with ID:", poolId);
    emits("usePool", poolId); 
}

const badgeColors = ref<string[]>([]);

const generateRandomColor = () => {
    const colors = ['red', 'green', 'blue', 'yellow', 'orange', 'purple', 'pink', 'indigo', 'cyan'];
    return `bg-${colors[Math.floor(Math.random() * colors.length)]}-400`;
};

onMounted(() => {
    badgeColors.value = props.competences.map(() => generateRandomColor());
});
</script>
