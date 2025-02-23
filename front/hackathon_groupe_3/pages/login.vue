<template>
    <div class="grid grid-cols-2 w-screen h-screen">
        <div class="h-full w-full flex flex-col items-center justify-between py-5">
            <div />

            <div class="flex flex-col gap-y-10">
                <div class="flex items-center justify-center w-max gap-x-5">
                    <img alt="Logo" class="w-16 h-16" src="../public/assets/images/logo.svg" />
                    <span class="font-semibold text-6xl text-primary">SkillStack</span>
                </div>

                <div class="flex flex-col gap-y-2">
                    <span class="text-2xl text-gray-800 font-medium">Connexion à votre compte</span>
                    <span>Bon retour parmi nous !</span>
                </div>

                <div class="flex flex-col gap-y-2">
                    <!-- Select user -->
                    <USelect v-model="selectedUser" size="xl" :options="userOptions" placeholder="Choisir un utilisateur" />

                    <!-- Login input avec erreur conditionnelle -->
                    <UInput v-model="login" color="gray" icon="lucide:user" placeholder="Login" size="xl" type="text" variant="outline" :status="loginError ? 'danger' : ''" />
                    <!-- Mot de passe input avec erreur conditionnelle -->
                    <UInput v-model="password" color="gray" icon="lucide:key-round" placeholder="Mot de passe" size="xl" type="password" variant="outline" :status="loginError ? 'danger' : ''" />

                    <!-- Message d'erreur si la connexion échoue -->
                    <p v-if="loginError" class="text-red-500 text-sm mt-2">Erreur de connexion. Vérifiez vos identifiants.</p>

                    <UButton block class="mt-4" color="primary" size="xl" variant="solid" @click="handleConnect"> Connexion </UButton>
                </div>
            </div>

            <div class="flex items-center justify-center w-max">
                <span class="whitespace-nowrap">Vous n'avez pas de compte ?</span>
                <UButton block class="w-min" color="primary" size="xl" variant="link">S'enregistrer</UButton>
            </div>
        </div>

        <div class="bg-[url('/assets/images/pattern.png')] bg-no-repeat bg-cover h-full w-full" />
    </div>
</template>

<script setup>
import { navigateTo } from "#app";
import { computed, ref } from "vue";
import { APIUtils } from "~/types/utilsApi";

const users = [
    { email: "sophie.durand@example.com", password: "UGFzczEyMzQh", role: "RH" },
    { email: "luc.martin@example.com", password: "U2VjdXJlUHdkMSE=", role: "RH" },
    { email: "claire.bernard@example.com", password: "SFJwYXNzd29yZDIwMjQ=", role: "RH" },
    { email: "thomas.lefevre@example.com", password: "UHJvamVjdExlYWQjOTk=", role: "CDP" },
    { email: "julie.morel@example.com", password: "Q2RQMjAyNCE=", role: "CDP" },
    { email: "nicolas.dubois@example.com", password: "TWFuYWdlMTIzIQ==", role: "CDP" },
    { email: "camille.robert@example.com", password: "QWRtaW5TdXBlcg==", role: "ADMIN" },
    { email: "maxime.garcia@example.com", password: "U2VjdXJlQWRtaW45OQ==", role: "ADMIN" },
    { email: "emma.petit@example.com", password: "QWRtIW5QYXNzMjI=", role: "ADMIN" }
];

const userOptions = ref(users.map((user) => ({ value: user.email, label: user.email + " (" + user.role + ")"})));
const selectedUser = ref(userOptions.value[0].value);

const login = computed(() => selectedUser.value);
const password = computed(() => users.find((user) => user.email === selectedUser.value)?.password);
const loginError = ref(false);

const handleConnect = async () => {
    console.log("Connexion");

    loginError.value = false;

    try {
        const response = await APIUtils.login(login.value, password.value);
        console.log(response);

        if (response.status === 200) {
            console.log("Connection successful");
            console.log("Redirecting to /profils");

            await useAuth().fetchSession();

            navigateTo("/profils");
        } else {
            console.error("Error while logging in");
            loginError.value = true;
        }
    } catch (error) {
        console.error("Error while logging in");
        console.error(error);
        loginError.value = true;
    }
};
</script>
