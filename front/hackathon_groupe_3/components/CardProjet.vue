<template>
    <UCard>
        <div class="flex flex-col gap-y-5">
            <span class="text-lg font-semibold whitespace-nowrap">{{ props.nom }}</span>
            <UBadge class="w-max" :color="isBefore(props.dateFin, new Date()) ? 'red' : 'primary'" size="md" variant="outline" icon="ic:baseline-calendar-month">{{ customFormat(props.dateDebut) + " - " + customFormat(props.dateFin) }}</UBadge>
            <div class="flex -gap-x-10">
                <UAvatar v-for="profil in profils.slice(0, 5)" :key="profil.id" :src="profil.profile_picture" :alt="profil.nom + profil.prenom" />
            </div>
        </div>
    </UCard>
</template>

<script lang="ts" setup>
import { format, isBefore } from "date-fns";
import { fr } from "date-fns/locale";
import type { Projet } from "~/types/entities";

const props = defineProps<Projet>();

const profils = computed(() => props.reservations.map((reservation) => reservation.profil));

const customFormat = (date: string | Date) => {
    try {
        const parsedDate = typeof date === "string" ? new Date(date) : date;
        return format(parsedDate, "dd MMMM yyyy", { locale: fr });
    } catch (error) {
        return "";
    }
};

onMounted(async () => {
    // Fake profile picture
    profils.value.forEach((profil) => {
        profil.profile_picture = `https://i.pravatar.cc/150?img=${profil.id}`;
    });
});
</script>
