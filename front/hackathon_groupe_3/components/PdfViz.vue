<template>
    <div @click="triggerFileInput" class="w-full h-full flex items-center justify-center rounded-xl" :class="mode !== ProfilModalMode.INFO ? 'outline-4 outline-dashed outline-green-500 cursor-pointer' : ''" style="aspect-ratio: 1 / 1.414">
        <!-- Mode INFO : Affichage du PDF ou message -->
        <template v-if="mode === ProfilModalMode.INFO">
            <iframe v-if="pdfUrl" :src="pdfUrl" class="w-full h-full rounded-xl border-none"></iframe>
            <span v-else class="text-gray-500">Aucun CV disponible</span>
        </template>

        <!-- Mode CREATE / UPDATE : Boutons d'upload et de suppression -->
        <template v-else>
            <div v-if="pdfUrl" class="relative w-full h-full">
                <iframe :src="pdfUrl" class="w-full h-full rounded-xl"></iframe>

                <UButton @click.stop="handleDeleteCv" class="absolute bottom-0 w-full py-3 mt-3 rounded-b-xl rounded-t-none" icon="ic:sharp-delete" size="sm" color="red" variant="solid" label="Supprimer" block />
            </div>
            <label v-else class="flex flex-col items-center justify-center cursor-pointer h-full w-full bg-green-50">
                <input ref="fileInput" type="file" accept="application/pdf" class="hidden" @change="handleFileUpload" />
                <UIcon name="mdi:file-plus" class="text-green-500 w-10 h-10" />
                <span class="text-green-500">Ajouter un CV</span>
            </label>
        </template>
    </div>
</template>

<script setup lang="ts">
import { defineProps, ref } from "vue";
import type { Profil } from "~/types/entities";
import { ProfilModalMode } from "~/types/modals";

const props = defineProps<{
    mode: ProfilModalMode;
    profil?: Profil;
}>();

const fileInput = ref<HTMLInputElement | null>(null);
const pdfUrl = ref<string | null>(props.profil?.cvUrl ?? null);

const triggerFileInput = () => {
    if (props.mode !== ProfilModalMode.INFO && !pdfUrl.value) {
        fileInput.value?.click();
    }
};

const handleFileUpload = (event: Event) => {
    const file = (event.target as HTMLInputElement).files?.[0];
    if (file) {
        pdfUrl.value = URL.createObjectURL(file);
    }
};

const handleDeleteCv = () => {
    pdfUrl.value = null;
};
</script>
