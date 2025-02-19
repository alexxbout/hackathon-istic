<template>
    <div class="border border-black rounded-2xl flex items-center justify-center w-full h-max p-3 gap-x-2.5 shrink-0">
        <USelectMenu @change="handleSkillsUpdate" class="w-full" variant="outline" color="gray" size="xl" v-model="selectedSkills" :options="skillOptions" :placeholder="selectSkillPlaceholder" searchable :searchable-placeholder="skillSearchText" multiple>
            <template #label>
                <span v-if="selectedSkills.length" class="truncate">{{ selectedSkillLabels }}</span>
                <span v-else>{{ selectSkillPlaceholder }}</span>
            </template>

            <template #option-empty="{ query }">
                <span>{{ skillSearchNoResults }}{{ query }}"</span>
            </template>
        </USelectMenu>

        <USelect @change="handleExperienceUpdate" class="w-full" variant="outline" color="gray" size="xl" v-model="selectedExperience" :options="experiencesOptions" :placeholder="experiencePlaceholder" />

        <div v-if="props.role != 'rh'" class="w-full">
            <UPopover :popper="{ placement: 'bottom-start' }">
                <UButton size="xl" icon="i-heroicons-calendar-days-20-solid"> {{ format(selectedPeriod.start, "d MMM, yyy") }} - {{ format(selectedPeriod.end, "d MMM, yyy") }}</UButton>

                <template #panel="{ close }">
                    <div class="flex items-center sm:divide-x divide-gray-200 dark:divide-gray-800">
                        <div class="hidden sm:flex flex-col py-4">
                            <UButton v-for="(range, index) in ranges" :key="index" :label="range.label" color="gray" variant="ghost" class="rounded-none px-6" :class="[isRangeSelected(range.duration) ? 'bg-gray-100 dark:bg-gray-800' : 'hover:bg-gray-50 dark:hover:bg-gray-800/50']" truncate @click="selectRange(range.duration)" />
                        </div>

                        <DatePicker @update:model-value="handlePeriodUpdate" v-model="selectedPeriod" @close="close" />
                    </div>
                </template>
            </UPopover>
        </div>
    </div>
</template>

<script setup lang="ts">
import { format, isSameDay, sub, type Duration } from "date-fns";
import { computed, ref } from "vue";
import type { Role } from "../types/roles";

const props = defineProps<{
    role: Role;
    experienceOptions: { value: number; label: string }[];
    skillOptions: { value: number; label: string }[];
}>();

const skillOptions = ref<{ value: number; label: string }[]>(props.skillOptions);

const selectedSkills = ref<{ value: number; label: string }[]>([]);
const selectSkillPlaceholder = ref("Filtrer les compétences");
const skillSearchText = ref("Rechercher...");
const skillSearchNoResults = ref("Aucun résultat pour '");

const selectedSkillLabels = computed(() => {
    return selectedSkills.value.map((skill) => skill.label).join(", ");
});

const selectedExperience = ref();

const experiencesOptions = ref<{ value: number; label: string }[]>(props.experienceOptions);

const experiencePlaceholder = ref("Filtrer sur l'expérience");

const ranges = [
    { label: "7 derniers jours", duration: { days: 7 } },
    { label: "14 derniers jours", duration: { days: 14 } },
    { label: "30 derniers jours", duration: { days: 30 } },
    { label: "3 derniers mois", duration: { months: 3 } },
    { label: "6 derniers mois", duration: { months: 6 } },
    { label: "Année", duration: { years: 1 } },
];
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

defineExpose({ clearFilters });
</script>
