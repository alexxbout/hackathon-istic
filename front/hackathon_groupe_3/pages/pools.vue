<template>
  <CardPool
      v-for="poolItem in poolsWithCompetences" :key="poolItem.pool.id"
      :competences="poolItem.competences.map(c => ({ nom: c.nom }))" :experience="poolItem.pool.experience"
      :isChecked="selectedPools.has(poolItem.pool.id!)" :nom="poolItem.pool.nom"
      @click="toggleSelection(poolItem.pool.id!)"
      @use-pool="handlePoolSelection"
  />
</template>


<script lang="ts" setup>
import { ref } from 'vue';
import type { Competence, Pool } from "~/types/entities";

definePageMeta({
  middleware: "auth",
});

definePageMeta({
  middleware: "auth",
});

const modal = ref(false);

const openModal = () => {
  modal.value = true;
};

const selectedPools = ref(new Set<number>());

const selectedCompetences = ref<number[]>([]);

const toggleSelection = (id: number) => {
  if (selectedPools.value.has(id)) {
    selectedPools.value.delete(id);
  } else {
    selectedPools.value.add(id);
  }
};

function handlePoolSelection() {
  //get competences asscociated with selected pool
  // selectedCompetences.value = poolsWithCompetences.value.map(poolItem => {
  //   if (poolItem.pool.id) {
  //     if (selectedPools.value.has(poolItem.pool.id)) {
  //       return poolItem.competences.map(competence => competence.id);
  //     }
  //   }
  //   return [];
  // }).flat();
  console.log(selectedCompetences.value);
  localStorage.setItem('selectedCompetences', JSON.stringify(selectedCompetences.value));
  //set to local storage the experience ofpools selected
  localStorage.setItem('selectedPools', JSON.stringify(poolsWithCompetences.value.filter(poolItem => selectedPools.value.has(poolItem.pool.id!)).map(poolItem => poolItem.pool.experience)));
  //navigateTo('/profils');
  //TODO fix navigate
}

const poolsWithCompetences = ref<{ pool: Pool; competences: Competence[] }[]>([
  {
    pool: {id: 1, user_id: 101, nom: "Backend Experts", experience: 5},
    competences: [
      {id: 5, nom: "Node.js"},
      {id: 6, nom: "Python"},
      {id: 7, nom: "Django"},
    ],
  },
  {
    pool: {id: 2, user_id: 102, nom: "Frontend Masters", experience: 3},
    competences: [
      {id: 1, nom: "JavaScript"},
      {id: 2, nom: "TypeScript"},
      {id: 3, nom: "Vue.js"},
      {id: 4, nom: "React"},
    ],
  },
  {
    pool: {id: 3, user_id: 103, nom: "Fullstack Wizards", experience: 7},
    competences: [
      {id: 1, nom: "JavaScript"},
      {id: 2, nom: "TypeScript"},
      {id: 5, nom: "Node.js"},
      {id: 6, nom: "Python"},
    ],
  },
  {
    pool: {id: 4, user_id: 104, nom: "Data Scientists", experience: 6},
    competences: [
      {id: 6, nom: "Python"},
      {id: 8, nom: "Flask"},
      {id: 9, nom: "Machine Learning"},
    ],
  },
  {
    pool: {id: 5, user_id: 105, nom: "DevOps Gurus", experience: 4},
    competences: [
      {id: 10, nom: "Kubernetes"},
      {id: 11, nom: "Docker"},
      {id: 12, nom: "AWS"},
      {id: 13, nom: "Azure"},
    ],
  },
  {
    pool: {id: 6, user_id: 106, nom: "Cloud Engineers", experience: 5},
    competences: [
      {id: 12, nom: "AWS"},
      {id: 13, nom: "Azure"},
      {id: 11, nom: "Docker"},
    ],
  },
  {
    pool: {id: 7, user_id: 107, nom: "Cybersecurity Specialists", experience: 8},
    competences: [
      {id: 14, nom: "Cybersecurity"},
      {id: 15, nom: "Penetration Testing"},
    ],
  },
]);
</script>