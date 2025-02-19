<template>
  <div>
    <UButton label="Open" @click="openModal" />

    <PoolModal v-model="modal" />
  </div>
</template>

<script setup lang="ts">
import { ref } from "vue";
import PoolModal from "~/components/PoolModal.vue";

const modal = ref(false);

const openModal = () => {
  modal.value = true;
};

</script>
