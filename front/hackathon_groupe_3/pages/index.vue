<template>
    <div class="h-screen w-screen flex flex-col">
        <Header />
    </div>
</template>

<script lang="ts" setup>
import type { TypeRole } from '~/types/roles';

definePageMeta({
    middleware: "auth",
});

const role = ref<TypeRole>(useCookie<TypeRole>("role").value);
</script>
