<template>
    <UModal :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' } }">
        <UCard>
            <template #header>
                <span class="text-xl font-semibold">Informations sur le projet</span>
            </template>

            <!-- Form to create a new project -->
            <form class="flex flex-col gap-y-4">
                <div class="flex flex-col gap-2">
                    <label class="text-sm font-medium">Nom du projet</label>
                    <UInput v-model="name" class="w-full" variant="outline" color="gray" size="md" placeholder="Projet..." />
                </div>

                <div class="flex flex-col gap-2">
                    <label class="text-sm font-medium">Date de début</label>
                    <UInput v-model="startDate" class="w-full" variant="outline" color="gray" size="md" placeholder="Date de début" type="date" />
                </div>

                <div class="flex flex-col gap-2">
                    <label class="text-sm font-medium">Date de fin</label>
                    <UInput v-model="endDate" class="w-full" variant="outline" color="gray" size="md" placeholder="Date de fin" type="date" />
                </div>

                <div class="flex w-full gap-2">
                    <UButton size="md" color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="handleCancel" block />
                    <UButton size="md" label="Créer le projet" icon="material-symbols:add" class="w-1/2" block @click="handleCreateProject" />
                </div>
            </form>
        </UCard>
    </UModal>
</template>

<script lang="ts" setup>
import type { Projet } from '~/types/entities';

const emits = defineEmits<{
    (e: "close"): void;
    (e: "create", project: Projet): void;
}>();

const name = ref<string>("");
const startDate = ref<string>("");
const endDate = ref<string>("");

const handleCreateProject = () => {
	const project: Projet = {
		nom: name.value,
		date_debut: new Date(startDate.value),
		date_fin: new Date(endDate.value),
	};

	emits("create", project);
};

const handleCancel = () => {
	emits("close");
};
</script>