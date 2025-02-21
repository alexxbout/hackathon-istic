<template>
    <UModal :ui="{ overlay: { background: 'bg-gray-400/20 backdrop-blur-md' } }">
        <UCard class="overflow-hidden">
            <template #header>
                <div class="flex flex-col gap-y-5">
                    <span class="text-xl font-semibold">Assigner votre équipe à un projet</span>
                    <UProgress :value="progress" indicator />
                </div>
            </template>

            <div v-if="state == State.VALIDATE_PROFILES" class="flex flex-col gap-y-2 w-full">
                <span>Membres de l’équipe</span>

                <UAvatarGroup :max="maxProfiles" class="flex flex-wrap -gap-x-5 w-full">
                    <UAvatar v-for="profile in props?.profiles" size="xl" :src="profile.profile_picture" :alt="profile.nom + profile.prenom" />
                </UAvatarGroup>
            </div>

            <div v-else-if="state == State.VALIDATE_PROJECT" class="flex flex-col gap-y-2 w-full">
                <span>Choix du projet</span>

                <USelect v-model="experience" :options="experiences" />
            </div>

            <div v-else-if="state == State.FINISH" class="flex flex-col gap-y-2 w-full">
                <span class="font-semibold">Félicitations !</span>
                <span class="font-semibold">Votre équipe a été assignée au projet avec succès.</span>
                <span>Vous pouvez maintenant consulter les détails du projet dans la liste des projets.</span>
            </div>

            <template v-if="state == State.VALIDATE_PROFILES" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="handleCancel" block />
                    <UButton size="md" label="Valider l'équipe" icon="ic:round-check" class="w-1/2" block @click="handleNext" />
                </div>
            </template>

            <template v-else-if="state == State.VALIDATE_PROJECT" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="handleCancel" block />
                    <UButton size="md" label="Valider l'équipe" icon="ic:round-check" class="w-1/2" block @click="handleNext" />
                </div>
            </template>

            <template v-else-if="state == State.FINISH" #footer>
                <div class="flex w-full gap-x-2">
                    <UButton size="md" color="gray" variant="solid" label="Retour aux profils" icon="tabler:circle-arrow-left" class="w-full" @click="handleCancel" block />
                </div>
            </template>
        </UCard>
    </UModal>
</template>

<script lang="ts" setup>
import type { Profil } from "~/types/entities";

const emits = defineEmits<{
    (e: "close"): void;
    (e: "assign"): void;
}>();

enum State {
    VALIDATE_PROFILES,
    VALIDATE_PROJECT,
    FINISH,
}

const props = defineProps<{
    profiles: Profil[];
}>();

const state = ref(State.VALIDATE_PROFILES);
const maxProfiles = ref(41);

const experience = ref<string>();
const experiences = ref<[]>();

const progress = computed(() => {
    switch (state.value) {
        case State.VALIDATE_PROFILES:
            return 0;
        case State.VALIDATE_PROJECT:
            return 50;
        case State.FINISH:
            return 100;
    }
});

const handleNext = () => {
    switch (state.value) {
        case State.VALIDATE_PROFILES:
            state.value = State.VALIDATE_PROJECT;
            break;
        case State.VALIDATE_PROJECT:
            state.value = State.FINISH;
            break;
    }
};

const handleAssign = () => {
    // TODO
};

const handleCancel = () => {
    switch (state.value) {
        case State.VALIDATE_PROJECT:
            state.value = State.VALIDATE_PROFILES;
            break;
        case State.VALIDATE_PROFILES:
            state.value = State.FINISH;
            break;
        case State.FINISH:
            emits("close");
            break;
    }
};

const handleClose = () => {
    emits("close");
};
</script>
