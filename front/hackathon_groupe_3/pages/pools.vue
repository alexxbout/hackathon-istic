<template>
    <CardPool  
    v-for="poolItem in poolsWithCompetences" :key="poolItem.pool.id" :competences="poolItem.competences.map(c => ({ nom: c.nom }))"  :experience="poolItem.pool.experience" :nom="poolItem.pool.nom"  :isChecked="selectedPools.has(poolItem.pool.id)" 
    @click="toggleSelection(poolItem.pool.id)" 
/>
  </template>
  

<script lang="ts" setup>
import PoolModal from "~/components/PoolModal.vue";
import { ref } from 'vue';

const modal = ref(false);

const openModal = () => {
  modal.value = true;
};

const selectedPools = ref(new Set<number>());

const toggleSelection = (id: number) => {
    if (selectedPools.value.has(id)) {
        selectedPools.value.delete(id);
    } else {
        selectedPools.value.add(id);
    }
};
const poolsWithCompetences = ref([
    {
        pool: { id: 1, user_id: 101, nom: "Backend Experts", experience: 5 },
        competences: [
            { id: 5, nom: "Node.js" },
            { id: 6, nom: "Python" },
            { id: 7, nom: "Django" },
        ],
    },
    {
        pool: { id: 2, user_id: 102, nom: "Frontend Masters", experience: 3 },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
            { id: 3, nom: "Vue.js" },
            { id: 4, nom: "React" },
        ],
    },
    {
        pool: { id: 3, user_id: 103, nom: "Fullstack Wizards", experience: 7 },
        competences: [
            { id: 1, nom: "JavaScript" },
            { id: 2, nom: "TypeScript" },
            { id: 5, nom: "Node.js" },
            { id: 6, nom: "Python" },
        ],
    },
    {
        pool: { id: 4, user_id: 104, nom: "Data Scientists", experience: 6 },
        competences: [
            { id: 6, nom: "Python" },
            { id: 8, nom: "Flask" },
            { id: 9, nom: "Machine Learning" },
        ],
    },
    {
        pool: { id: 5, user_id: 105, nom: "DevOps Gurus", experience: 4 },
        competences: [
            { id: 10, nom: "Kubernetes" },
            { id: 11, nom: "Docker" },
            { id: 12, nom: "AWS" },
            { id: 13, nom: "Azure" },
        ],
    },
    {
        pool: { id: 6, user_id: 106, nom: "Cloud Engineers", experience: 5 },
        competences: [
            { id: 12, nom: "AWS" },
            { id: 13, nom: "Azure" },
            { id: 11, nom: "Docker" },
        ],
    },
    {
        pool: { id: 7, user_id: 107, nom: "Cybersecurity Specialists", experience: 8 },
        competences: [
            { id: 14, nom: "Cybersecurity" },
            { id: 15, nom: "Penetration Testing" },
        ],
    },
]);
</script>