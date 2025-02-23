<template>
    <div class="border-2 border-black rounded-xl flex items-center justify-center w-full h-max p-3 gap-x-2.5">
        <USelectMenu @change="handleSkillsUpdate" class="flex-1 min-w-0" variant="outline" color="gray" size="md" v-model="selectedSkills" :options="skillOptions" :placeholder="selectSkillPlaceholder" searchable :searchable-placeholder="skillSearchText" multiple>
            <template #label>
                <span v-if="selectedSkills.length" class="truncate">{{ selectedSkillLabels }}</span>
                <span v-else>{{ selectSkillPlaceholder }}</span>
            </template>

            <template #option-empty="{ query }">
                <span>{{ skillSearchNoResults }}{{ query }}"</span>
            </template>
        </USelectMenu>

        <USelect @change="handleExperienceUpdate" class="flex-1 min-w-0" variant="outline" color="gray" size="md" v-model="selectedExperience" :options="experiencesOptions" :placeholder="experiencePlaceholder" />

        <div v-if="props.role != 'rh'" ref="datePickerElement" class="flex-1 min-w-0">
            <UPopover class="w-full" :popper="{ placement: 'bottom-start' }">
                <UButton size="md" icon="i-heroicons-calendar-days-20-solid"> {{ format(selectedPeriod.start, "d MMM, yyy") }} - {{ format(selectedPeriod.end, "d MMM, yyy") }}</UButton>

                <template #panel="{ close }">
                    <DatePicker @update:model-value="handlePeriodUpdate" v-model="selectedPeriod" @close="close" />
                </template>
            </UPopover>
        </div>
    </div>
</template>

<script setup lang="ts">
import { format, isSameDay, sub, type Duration } from "date-fns";
import { computed, ref } from "vue";
import type { TypeRole } from "../types/roles";

const props = defineProps<{
    role: TypeRole;
    skillOptions: { value: number; label: string }[];
}>();

const datePickerElement = ref<HTMLElement>();

const skillOptions = ref<{ value: number; label: string }[]>(props.skillOptions);

const selectedSkills = ref<{ value: number; label: string }[]>([]);
const selectSkillPlaceholder = ref("Filtrer les compétences");
const skillSearchText = ref("Rechercher...");
const skillSearchNoResults = ref("Aucun résultat pour '");

const selectedSkillLabels = computed(() => {
    return selectedSkills.value.map((skill) => skill.label).join(", ");
});

const selectedExperience = ref();

const experiencesOptions = defineModel<{ value: number; label: string }[]>("experienceOptions");

const experiencePlaceholder = ref("Filtrer sur l'expérience");

const selectedPeriod = ref<{ start: Date; end: Date | number }>({ start: new Date(), end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)) });

const isRangeSelected = (duration: Duration) => {
    return isSameDay(selectedPeriod.value.start, sub(new Date(), duration)) && isSameDay(selectedPeriod.value.end, new Date());
};

const selectRange = (duration: Duration) => {
    console.log("Selecting range", duration);

    selectedPeriod.value = { start: sub(new Date(), duration), end: new Date() };
};

const clearFilters = () => {
    console.log("Clearing filters");

    selectedSkills.value = [];
    selectedExperience.value = null;
    selectedPeriod.value = { start: new Date(), end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)) };
};

const emits = defineEmits<{
    (e: "updateSkills", skills: number[]): void;
    (e: "updateExperience", experience: number): void;
    (e: "updatePeriod", period: { start: Date; end: Date | number }): void;
}>();

const handleSkillsUpdate = (skills: { value: number; label: string }[]) => {
    console.log("Skills filter updated", skills);

    selectedSkills.value = skills;
    emits(
        "updateSkills",
        skills.map((skill) => skill.value)
    );
};

const handleExperienceUpdate = (experience: number) => {
    console.log("Experience filter updated", experience);

    selectedExperience.value = experience;
    emits("updateExperience", experience);
};

const handlePeriodUpdate = (period: { start: Date; end: Date | number }) => {
    console.log("Period filter updated", period);

    selectedPeriod.value = period;
    emits("updatePeriod", period);
};

onMounted(() => {
    // Fix the width of the date picker button
    const button = datePickerElement.value?.querySelector("button");
    if (button) {
        button.classList.add("w-full");
    }
});

defineExpose({ clearFilters });
</script>
