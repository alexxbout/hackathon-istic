<template>
    <div class="border border-black rounded-2xl flex items-center justify-center w-max h-max p-3 gap-x-2.5 shrink-0">
        <USelectMenu variant="outline" color="gray" size="xl" v-model="selectedSkills" :options="skillOptions" :placeholder="selectSkillPlaceholder" searchable :searchable-placeholder="skillSearchText" multiple>
            <template #label>
                <span v-if="selectedSkills.length" class="truncate">{{ selectedSkillLabels }}</span>
                <span v-else>{{ selectSkillPlaceholder }}</span>
            </template>

            <template #option-empty="{ query }">
                <span>{{ skillSearchNoResults }}{{ query }}"</span>
            </template>
        </USelectMenu>

        <USelect variant="outline" color="gray" size="xl" v-model="selectedExperience" :options="experiencesOptions" :placeholder="experiencePlaceholder" />

        <UPopover :popper="{ placement: 'bottom-start' }">
            <UButton size="xl" icon="i-heroicons-calendar-days-20-solid"> {{ format(selectedPeriod.start, "d MMM, yyy") }} - {{ format(selectedPeriod.end, "d MMM, yyy") }}</UButton>

            <template #panel="{ close }">
                <div class="flex items-center sm:divide-x divide-gray-200 dark:divide-gray-800">
                    <div class="hidden sm:flex flex-col py-4">
                        <UButton v-for="(range, index) in ranges" :key="index" :label="range.label" color="gray" variant="ghost" class="rounded-none px-6" :class="[isRangeSelected(range.duration) ? 'bg-gray-100 dark:bg-gray-800' : 'hover:bg-gray-50 dark:hover:bg-gray-800/50']" truncate @click="selectRange(range.duration)" />
                    </div>

                    <DatePicker v-model="selectedPeriod" @close="close" />
                </div>
            </template>
        </UPopover>
    </div>
</template>

<script setup lang="ts">
import { format, isSameDay, sub, type Duration } from "date-fns";
import { computed, ref } from "vue";
import type { Role } from "../types/roles";

const props = defineProps<{
    role: Role;
}>();

// TODO: Fetch skills from API
const skillOptions: { value: number; label: string }[] = [
    { value: 1, label: "JavaScript" },
    { value: 2, label: "TypeScript" },
    { value: 3, label: "Vue.js" },
    { value: 4, label: "React" },
    { value: 5, label: "Angular" },
    { value: 6, label: "Node.js" },
    { value: 7, label: "Express" },
    { value: 8, label: "NestJS" },
    { value: 9, label: "GraphQL" },
    { value: 10, label: "REST" },
    { value: 11, label: "MongoDB" },
    { value: 12, label: "PostgreSQL" },
    { value: 13, label: "MySQL" },
    { value: 14, label: "Docker" },
    { value: 15, label: "Kubernetes" },
    { value: 16, label: "AWS" },
    { value: 17, label: "Azure" },
    { value: 18, label: "GCP" },
    { value: 19, label: "CI/CD" },
    { value: 20, label: "TDD" },
    { value: 21, label: "BDD" },
    { value: 22, label: "Scrum" },
    { value: 23, label: "Kanban" },
    { value: 24, label: "XP" },
    { value: 25, label: "DevOps" },
    { value: 26, label: "SRE" },
    { value: 27, label: "Agile" },
    { value: 28, label: "Lean" },
    { value: 29, label: "Design Patterns" },
    { value: 30, label: "SOLID" },
    { value: 31, label: "Microservices" },
    { value: 32, label: "Serverless" },
    { value: 33, label: "Security" },
    { value: 34, label: "Performance" },
    { value: 35, label: "Accessibility" },
    { value: 36, label: "SEO" },
];

const selectedSkills = ref<{ value: number; label: string }[]>([]);
const selectSkillPlaceholder = ref("Sélectionner des compétences");
const skillSearchText = ref("Rechercher...");
const skillSearchNoResults = ref("Aucun résultat pour '");

const selectedSkillLabels = computed(() => {
    return selectedSkills.value.map((skill) => skill.label).join(", ");
});

const selectedExperience = ref();

// TODO: Fetch experiences from API
const experiencesOptions: { value: number; label: string }[] = [
    { value: 1, label: "+1 an" },
    { value: 2, label: "+2 ans" },
    { value: 3, label: "+3 ans" },
    { value: 4, label: "+4 ans" },
    { value: 5, label: "+5 ans" },
];
const experiencePlaceholder = ref("Sélectionner une expérience");

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
    selectedPeriod.value = { start: sub(new Date(), duration), end: new Date() };
};

const clearFilters = () => {
    selectedSkills.value = [];
    selectedExperience.value = null;
    selectedPeriod.value = { start: new Date(), end: new Date(new Date().setFullYear(new Date().getFullYear() + 1)) };
};

defineEmits<{ clearFilters: () => void }>();
</script>
