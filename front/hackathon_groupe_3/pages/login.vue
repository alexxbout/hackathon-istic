<template>
  <div class="grid grid-cols-2 w-screen h-screen">
    <div class="h-full w-full flex flex-col items-center justify-between py-5">
      <div/>

      <div class="flex flex-col gap-y-10">
        <div class="flex items-center justify-center w-max gap-x-5">
          <img alt="Logo" class="w-16 h-16" src="../public/assets/images/logo.svg"/>
          <span class="font-semibold text-6xl text-primary">SkillStack</span>
        </div>

        <div class="flex flex-col gap-y-2">
          <span class="text-2xl text-gray-800 font-medium">Connexion à votre compte</span>
          <span>Bon retour parmi nous !</span>
        </div>

        <div class="flex flex-col gap-y-2">
          <UInput color="gray" icon="lucide:user" placeholder="Login" size="xl" type="text" variant="outline"/>
          <UInput color="gray" icon="lucide:key-round" placeholder="Mot de passe" size="xl" type="password"
                  variant="outline"/>

          <UButton block class="mt-4" color="primary" size="xl" variant="solid" @click="handleConnect">Connexion
          </UButton>
        </div>
      </div>

      <div class="flex items-center justify-center w-max">
        <span class="whitespace-nowrap">Vous n'avez pas de compte ?</span>
        <UButton block class="w-min" color="primary" size="xl" variant="link">S'enregistrer</UButton>
      </div>
    </div>

    <div class="bg-[url('/assets/images/pattern.png')] bg-no-repeat bg-cover h-full w-full"/>
  </div>
</template>

<script setup>
import {navigateTo, useCookie} from '#app';

const handleConnect = () => {
  console.log("Connexion");

  // Définir un cookie
  const testCookie = useCookie('auth_token', {maxAge: 3600}); // Expire après 1h
  testCookie.value = "MonCookieDeTest";

  // Vérifier que le cookie est bien défini
  console.log("Cookie défini:", testCookie.value);

  // Rediriger vers une autre page
  navigateTo('/profils');
};
</script>