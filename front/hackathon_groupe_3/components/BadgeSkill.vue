<template>
    <UBadge color="black" variant="solid">
        {{ label }}
        <template #trailing>
            <UIcon name="gravity-ui:circle-xmark" class="w-4 h-4" @click="emit('remove')" />
        </template>
    </UBadge>
</template>

<script setup lang="ts">
defineProps<{
    label: string;
}>();

const emit = defineEmits(["remove"]);
</script>
