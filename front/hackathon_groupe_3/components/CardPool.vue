<template>
    <UCard class="min-w-[400px] border-2  border-gray-200 group duration-200 relative p-4  shadow-lg rounded-lg">
            <template #header>
                <div class="h-8 flex items-center justify-between">  
                    <span class="text-xl font-semibold text-left">{{ props.nom }}</span>
                    <UButton variant="soft"   @click="handleDelete"  size="xs" color="red" icon="lucide:circle-x" >Supprimer</UButton>

                </div>
            </template>

      <div class="mt-0 p-2 border border-gray-300  rounded-lg w-full"
           style="box-shadow: 4px 4px 10px rgba(0, 0, 0, 0.1)">
        <p class="text-md font-semibold">Compétences : </p>
        <div class="flex flex-wrap gap-1 mt-2">
          <UBadge
              v-for="(competence, index) in props.competences"
              :key="index"
              :ui="{
                            color: {
                                white: {
                                    solid: badgeColors[index]
                                }
                            }
                        }"
              color="white"
              size="xs"
              variant="solid"
          >
            {{ competence.nom }}
          </UBadge>
        </div>

        <p class="text-md font-semibold mt-2">Expériences : </p>
        <p class="text-xs font-semibold mt-2">+{{ props.experience }} ans</p>
      </div>

            <div class="flex gap-2 mt-6 w-full">

                <!-- Bouton pour Accéder aux détails -->
                <UButton 
                    @click="openEditModal" 
                    size="md" color="green" variant="solid"
                    class="w-1/2 text-center flex justify-center items-center border-2 border-black bg-white text-black hover:border-green-500 hover:text-green-500 hover:bg-white">
                    <UIcon name="material-symbols:edit-outline" class="w-5 h-5" />
                    Modifier
                </UButton>
                

                <!-- Bouton pour Utiliser le pool -->
                <UButton 
                    @click="handleUsePool(props.id!)" 
                    size="md" color="green" variant="solid"
                    :ui="{ rounded: 'rounded-full' }"
                    class="w-1/2 text-center flex justify-center items-center border-2 border-black bg-white text-black hover:text-green-500 hover:border-green-400 hover:bg-white">
                    <UIcon name="ix:certificate-success-filled" class="w-5 h-5" />
                    Utiliser ce pool
                </UButton>
            </div>
            <PoolModal 
                v-model="isModalOpen"
                :user_id="userId"
                :nom="selectedPool?.nom || ''" 
                :competences="selectedPool?.competences || []" 
                :experience="selectedPool?.experience || 0"
                @updatePool="handleUpdatePool"
                @createPool="handleCreatePool"
                @close="isModalOpen = false"
            />

        </UCard>
</template>
<script lang="ts" setup>
import { onMounted, ref } from 'vue';
import type { Competence, Pool } from '~/types/entities';
import PoolModal from "./PoolModal.vue";

const props = defineProps<Pool>();

const userId = ref(-1); // TODO
const isModalOpen = ref(false);
const selectedPool = ref<Pool>(props);

const badgeColors = ref<string[]>([]);

const emits = defineEmits<{
    (e: "accessDetails", poolId: number): void;
    (e: "usePool", poolId: number): void;
    (e: "poolUpdated", updatedPool: Pool): void;
    (e: "deletePool", poolId: number): void;

}>();

function handleUpdatePool(updatedPool: { nom: string; experience: number; competences: Competence[] }) {
    if (selectedPool.value) {
        selectedPool.value.nom = updatedPool.nom;
        selectedPool.value.experience = updatedPool.experience;
        selectedPool.value.competences = updatedPool.competences; 

        emits("poolUpdated", <Pool>{
            id: props.id,
            user_id: userId.value,
            nom: updatedPool.nom,
            experience: updatedPool.experience,
            competences: updatedPool.competences
        });
    }
}

function handleDelete() {
    console.log("testId " + props.id)
    emits("deletePool", props.id!);
}

function openEditModal() {
    isModalOpen.value = true;
}

function handleUsePool(poolId: number) {
    console.log("Using pool with ID:", poolId);
    emits("usePool", poolId);
}
function handleCreatePool() {
    isModalOpen.value = true;
}


const generateRandomColor = () => {
  const colors = ['red', 'green', 'blue', 'yellow', 'orange', 'purple', 'pink', 'indigo', 'cyan'];
  return `bg-${colors[Math.floor(Math.random() * colors.length)]}-400`;
};

onMounted(() => {
  badgeColors.value = props.competences.map(() => generateRandomColor());
});
</script>
