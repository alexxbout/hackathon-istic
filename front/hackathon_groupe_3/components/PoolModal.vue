<template>
  <UModal v-model="isOpen" @close="closeModal">
    <UCard
        class="max-w-[90vw] sm:max-w-md md:max-w-lg lg:max-w-xl"
        :ui="{ ring: '', divide: 'divide-y divide-gray-100 dark:divide-gray-800' }"
    >
      <!-- Header -->
      <template #header>
        <div class="h-25">
          <label class="font-bold text-xl">Pool Backend</label>
        </div>
      </template>

      <!-- Body avec contenu scrollable -->
      <div class="modal-body flex flex-col gap-y-4">

        <div class="flex flex-col gap-y-1">
          <label>Nom</label>
          <UInput variant="outline" color="gray" type="text" size="md" placeholder="Saisir le nom" />
        </div>

        <div class="flex flex-col gap-y-1">
          <label>Expérience</label>
          <UInput variant="outline" color="gray" type="number" size="md" placeholder="Saisir expérience" min="0" max="100"/>
        </div>

        <div class="flex flex-col gap-y-1">
          <label>Date Début - Fin</label>
          <DateRangePicker />
        </div>

        <div class="flex flex-col gap-y-1">
          <label class="font-medium">Compétences</label>
          <div class="flex items-center gap-x-2">
            <UInput
                v-model="competence"
                variant="outline"
                color="gray"
                type="text"
                size="md"
                placeholder="Saisir une compétence"
                class="flex-1"
            />
            <UButton
                color="gray"
                variant="solid"
                icon="material-symbols:add"
                @click="ajouterCompetence"
            >
              Ajouter
            </UButton>
          </div>

          <!-- Affichage des badges -->
          <div class="flex flex-wrap gap-2 mt-2">
            <BadgeCompetence v-for="(comp, index) in competences" :key="index" :label="comp"
                             @remove="supprimerCompetence(index)"/>
          </div>
        </div>
      </div>

      <!-- Footer -->
      <template #footer>
        <div class="flex w-full gap-1">
          <UButton color="gray" variant="solid" label="Retour" icon="tabler:circle-arrow-left" class="w-1/2"
                   @click="closeModal"/>
          <UButton label="Modifier" icon="material-symbols:edit-outline" class="w-1/2" />
        </div>
      </template>
    </UCard>
  </UModal>
</template>

<script setup lang="ts">
import { ref, defineModel } from "vue";
import BadgeCompetence from "~/components/BadgeSkill.vue";

const isOpen = defineModel<boolean>();

const competence = ref("");
const competences = ref<string[]>([]);

const ajouterCompetence = () => {
  if (competence.value.trim() !== "" && !competences.value.includes(competence.value)) {
    competences.value.push(competence.value);
    competence.value = "";
  }
};

const supprimerCompetence = (index: number) => {
  competences.value.splice(index, 1);
};

function closeModal() {
  isOpen.value = false;
  competences.value = [];
  competence.value = "";
}
//TODO : contole saisie competence : à verifier ques les competences inséré existent bien dans la BDD
//TODO: realiser l'action modifier

</script>

<style scoped>
.modal-body {
  max-height: 60vh;
  overflow-y: auto;
  padding-right: 10px;
  -webkit-overflow-scrolling: touch;
}

.modal-body::-webkit-scrollbar {
  width: 6px;
}

.modal-body::-webkit-scrollbar-track {
  background: #f5f7fa;
  border-radius: 10px;
}

.modal-body::-webkit-scrollbar-thumb {
  background: #cbd5e1;
  border-radius: 10px;
}

.modal-body::-webkit-scrollbar-thumb:hover {
  background: #b0bec5;
}
</style>
