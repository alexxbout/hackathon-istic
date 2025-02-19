<script lang="ts" setup>
import {ref} from 'vue';

const avatar = ref<string>('');
const name = ref<string>('Williams');
const firstname = ref<string>('Olivia');
const ville = ref<string>('Rennes');
const experience = ref<string>('+ 3 ans');
//const experienceOptions = ["+1 an", "+2 ans", "+3 ans", "+4 ans", "+5 ans"];
const competences = ref<string[]>(["Java", "Python", "C", "JS"]);
//const competencesOptions = ["Java", "Python", "C", "JS"];
const isOpen = defineModel('isOpen', {
  type: Boolean,
  default: true
});
</script>

<template>
  <UButton @click="isOpen = true">Afficher les détails</UButton>
  <UModal v-model="isOpen" fullscreen>
    <div class="grid grid-cols-1 md:grid-cols-2 gap-4 p-4 w">
      <!-- Left Column -->
      <div class="p-4 bg-gray-200 ">
        <div class="w-full flex items-center justify-center flex-col">
          <UAvatar v-model="avatar" alt="Your Avatar" class="flex" placeholder="Avatar" size="3xl" src="https://www.pngfind.com/pngs/m/114-1146521_girl-avatar-png-picture-female-avatar-no-face.png"/>
        </div>
        <h2 class="font-bold">Informations personnelles</h2>

        <div>
          <label>Nom</label>
          <br>
          <UInput v-model="name" placeholder="Nom"/>
        </div>

        <div>
          <label>Prénom</label>
          <br>
          <UInput v-model="firstname" placeholder="Prénom"/>
        </div>

        <div>
          <label>Ville</label>
          <br>
          <UInput v-model="ville" placeholder="Ville"/>
        </div>

        <div>
          <label>Expérience</label>
          <br>
          {{experience}}
        </div>

        <div>
          <label>Compétences</label>
          <br>
          <!-- creer un bouton pour chaque competence -->
          <div  class="flex space-x-2" v-for="c in competences">
            <UButton color="black" variant="solid">{{c}}</UButton>
          </div>
        </div>
        <div>
          <UButton icon="streamline:return-2" @click="isOpen = false">Retour</UButton>
        </div>
      </div>

      <!-- Right Column -->
      <div class="p-4 bg-gray-200">
        <img
            alt="CV"
            src="../public/assets/images/img-cv.png"/>
      </div>
    </div>
  </UModal>
</template>
