<template>
  <div>
    <UButton label="Créer un nouvel utilisateur" variant="soft" color="gray" icon="lucide:user-plus" @click="isOpen = true" />

    <UModal v-model="isOpen">
      <UCard :ui="{ ring: '', divide: 'divide-y divide-gray-100 dark:divide-gray-800' }">
        <template #header>
          <h2 class="text-lg font-semibold">Créer un nouvel utilisateur</h2>
        </template>

        <div class="modal-body flex flex-col gap-y-4">
          <div class="flex flex-row gap-x-4">
            <div class="flex flex-col gap-y-1 w-1/2">
              <UFormGroup label="Nom" required :error="showErrors && !isNameValid && 'Le nom ne doit contenir que des lettres.'">
                <template #default="{ error }">
                  <UInput v-model="newUser.nom" variant="outline" type="text" size="md" placeholder="Saisir le nom" :trailing-icon="error ? 'i-heroicons-exclamation-triangle-20-solid' : undefined" />
                </template>
                <template #error="{ error }">
                <span :class="[error ? 'text-red-500 dark:text-red-400' : 'text-primary-500 dark:text-primary-400']">
                  {{ error || 'Nom valide' }}
                </span>
                </template>
              </UFormGroup>
            </div>
            <div class="flex flex-col gap-y-1 w-1/2">
              <UFormGroup label="Prénom" required :error="showErrors && !isPrenomValid && 'Le prénom ne doit contenir que des lettres.'">
                <template #default="{ error }">
                  <UInput v-model="newUser.prenom" variant="outline" type="text" size="md" placeholder="Saisir le prénom" :trailing-icon="error ? 'i-heroicons-exclamation-triangle-20-solid' : undefined" />
                </template>
                <template #error="{ error }">
                <span :class="[error ? 'text-red-500 dark:text-red-400' : 'text-primary-500 dark:text-primary-400']">
                  {{ error || 'Prénom valide' }}
                </span>
                </template>
              </UFormGroup>
            </div>

          </div>

          <UFormGroup label="Rôle" required>
            <USelectMenu v-model="newUser.role" :options="roleOptions">
              <template #selected>
                <div v-if="newUser.role" class="flex items-center gap-x-2">
                  <UIcon :name="getRoleConfig(newUser.role).icon" class="w-5 h-5" />
                  <span>{{ getRoleConfig(newUser.role).label }}</span>
                </div>
                <span v-else>Choisir un rôle</span>
              </template>
              <template #option="{ option }">
                <div class="flex items-center gap-x-2">
                  <UIcon :name="option.icon" class="w-5 h-5" />
                  <span>{{ option.label }}</span>
                </div>
              </template>
            </USelectMenu>
          </UFormGroup>

          <UFormGroup label="Email" required :error="showErrors && !isEmailValid && 'Format de mail est incorrect'">
            <template #default="{ error }">
              <UInput v-model="newUser.email" type="email" :trailing-icon="error ? 'i-heroicons-exclamation-triangle-20-solid' : undefined" placeholder="you@example.com" icon="i-heroicons-envelope" />
            </template>
            <template #error="{ error }">
              <span :class="[error ? 'text-red-500 dark:text-red-400' : 'text-primary-500 dark:text-primary-400']">
                {{ error || 'Email valide' }}
              </span>
            </template>
          </UFormGroup>
        </div>

        <template #footer>
          <div class="flex w-full gap-1">
            <UButton color="gray" variant="solid" label="Annuler" icon="tabler:circle-arrow-left" class="w-1/2" @click="closeModal" />
            <UButton label="Créer" icon="material-symbols:edit-outline" class="w-1/2" @click="createUser" :disabled="!isFormValid" />
          </div>
        </template>
      </UCard>
    </UModal>
  </div>
</template>

<script setup lang="ts">
import { ref, computed } from "vue";
import { Role, RoleConfig, roleOptions } from "~/types/roles";

const isOpen = ref(false);
let newUser = ref({
  nom: "",
  prenom: "",
  email: "",
  role: null as Role | null,
});

const showErrors = ref(false);

const nameRegex = /^[A-Za-zÀ-ÿ]+$/;
const prenomRegex = /^[A-Za-zÀ-ÿ]+$/;

// Validation des champs
const isEmailValid = computed(() => {
  const emailRegex = /^[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\.[a-zA-Z]{2,6}$/;
  return emailRegex.test(newUser.value.email);
});

const isNameValid = computed(() => {
  return nameRegex.test(newUser.value.nom);
});

const isPrenomValid = computed(() => {
  return prenomRegex.test(newUser.value.prenom);
});

// Vérification de la validité globale du formulaire (on ne vérifie que si les champs sont remplis)
const isFormValid = computed(() => {
  return (
      newUser.value.nom.trim() !== "" &&
      newUser.value.prenom.trim() !== "" &&
      newUser.value.role !== null &&
      newUser.value.email.trim() !== ""
  );
});

// Fonction pour réinitialiser le modal et les champs
function closeModal() {
  isOpen.value = false;
  newUser.value = {
    nom: "",
    prenom: "",
    email: "",
    role: null as Role | null,
  };
  showErrors.value = false; // Réinitialisation de l'état d'erreur
}

// Fonction pour obtenir la configuration du rôle
const getRoleConfig = (role: Role | null) => {
  return RoleConfig[role as Role] ?? { label: "Inconnu", icon: "i-heroicons-question-mark-circle", color: "gray" };
};

// Fonction pour créer un utilisateur
async function createUser() {

  if (isFormValid.value) {
    showErrors.value = true;
    return;
  }
    const userData = {
      nom: newUser.value.nom,
      prenom: newUser.value.prenom,
      email: newUser.value.email,
      role_id: newUser.value.role?.id,
    };

    await APIUtils.addUser(userData)
        .then(response => {
          console.log('Nouvel utilisateur créé :', response.data);
          closeModal();
        })
        .catch(error => {
          console.error('Erreur lors de la création de l\'utilisateur :', error);
        });

}
</script>

<style scoped>
.error-text {
  color: #f87171; /* Couleur rouge pour l'erreur */
  font-size: 0.875rem; /* Taille du texte d'erreur */
  margin-top: 0.25rem; /* Un petit espace en haut du message d'erreur */
}

</style>
