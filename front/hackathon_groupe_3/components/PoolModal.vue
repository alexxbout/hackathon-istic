<template>
    <UModal v-model="isOpen" @close="closeModal" :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' }, width: 'max-w-[90vw]  w-full' }">
        <UCard class="max-w-4xl" :ui="{ ring: '', divide: 'divide-y divide-gray-100 dark:divide-gray-800' }">
            <template #header>
                <div class="h-25">
                    <label class="font-bold text-xl">{{ isEditing ? "Modifier le pool" : "Créer un pool" }}</label>
                </div>
            </template>

            <div class="flex flex-col gap-y-4">
                <div class="flex flex-col gap-y-1">
                    <label>Nom</label>
                    <UInput v-model="editedNom" variant="outline" color="gray" type="text" size="md" placeholder="Saisir le nom" />
                </div>

                <div class="flex flex-col gap-y-1">
                    <label>Expérience</label>
                    <UInput v-model="editedExperience" variant="outline" color="gray" type="number" size="md" placeholder="Saisir expérience" min="0" max="100" />
                </div>

                <div class="flex flex-col gap-y-1">
                    <label>Compétences</label>
                    <div class="flex items-center gap-x-2">
                        <UInput v-model="competence" variant="outline" color="gray" type="text" size="md" placeholder="Saisir une compétence" class="flex-1" />
                        <UButton color="gray" variant="solid" icon="material-symbols:add" @click="ajouterCompetence"> Ajouter </UButton>
                    </div>

                    <div class="flex flex-wrap gap-2 mt-2">
                        <BadgeCompetence v-for="(comp, index) in competences" :key="index" :label="comp.nom" @remove="supprimerCompetence(index)" />
                    </div>
                </div>
            </div>

            <template #footer>
                <div class="flex w-full gap-1">
                    <UButton color="gray" variant="solid" label="Retour" icon="tabler:circle-arrow-left" class="w-1/2" @click="closeModal" />
                    <UButton :label="isEditing ? 'Modifier' : 'Créer'" icon="material-symbols:edit-outline" class="w-1/2" @click="soumettrePool" />
                </div>
            </template>
        </UCard>
    </UModal>
</template>

<script setup lang="ts">
import { ref, watch, type PropType } from "vue";
import BadgeCompetence from "~/components/BadgeSkill.vue";
import type { Competence, Pool } from "~/types/entities";

const props = defineProps<Pool>();

const isOpen = defineModel<boolean>();
const isEditing = ref(false);
const competence = ref<string>("");
const competences = ref<Competence[]>([]);
const editedNom = ref("");
const editedExperience = ref<string | number>("");
const editedPoolId = ref<number >(-1);

const emits = defineEmits<{
    (e: "updatePool", updatedPool:Pool): void;
    (e: "createPool", newPool: Pool): void;
}>();

watch(isOpen, (newVal) => {
    if (newVal) {
        isEditing.value = props.nom !== "";
        editedNom.value = props.nom ;
        editedExperience.value = props.experience ;
        competences.value = props.competences;
    }
});

const existeCompetence = (competenceToCheck: string): boolean => {
    return !!competences.value.find(comp => comp.nom == competenceToCheck); // Comparer juste les strings
};

const ajouterCompetence = () => {
    console.log("Essai d'ajouter compétence...");
    if (!existeCompetence(competence.value)) {
        competences.value.push({id: -1, nom: competence.value});
        console.log("Compétence ajoutée : ", competence.value);
        competence.value = "";
    } else {
        console.log("Compétence déjà existante");
    }
};


const supprimerCompetence = (index: number) => {
    competences.value.splice(index, 1);
};

function soumettrePool() {
  const experienceValue = editedExperience.value === "" ? 0 : Number(editedExperience.value);

  const poolData :Pool = {
    id: isEditing.value ? (editedPoolId.value ) :undefined,  
    nom: editedNom.value,
    experience: experienceValue,
    user_id :-1, //TODO
    competences: competences.value
  };
 


  console.log("Pool soumis :", poolData);

  if (isEditing.value) {
    emits("updatePool", poolData);  
  } else {
    emits("createPool", poolData);  
  }

  closeModal();
}


function closeModal() {
    isOpen.value = false;
}
</script>
