<template>
    <div class="h-screen w-screen flex flex-col">
        <Header />
    </div>
</template>

<script lang="ts" setup>

definePageMeta({
    middleware: "auth",
});
</script>
