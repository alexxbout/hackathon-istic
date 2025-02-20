<template>
    <UCard>
        <div class="flex flex-col gap-y-5">
            <span class="text-lg font-semibold whitespace-nowrap">{{ props.nom }}</span>
            <UBadge class="w-max" :color="isBefore(props.date_fin, new Date()) ? 'red': 'primary'" size="md" variant="outline" icon="ic:baseline-calendar-month">{{ format(props.date_debut, "dd MMMM yyyy", { locale: fr }) + " - " + format(props.date_fin, "dd MMMM yyyy", { locale: fr }) }}</UBadge>
        </div>
    </UCard>
</template>

<script lang="ts" setup>
import { format, isBefore } from "date-fns";
import { fr } from "date-fns/locale";

const props = defineProps<{
    nom: string;
    date_debut: Date;
    date_fin: Date;
    role: string;
}>();
</script>
