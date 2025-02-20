<template>
    <div class="grid grid-cols-2 w-screen h-screen">
        <div class="h-full w-full flex flex-col items-center justify-between py-5">
            <div />
            
            <div class="flex flex-col gap-y-10">
                <div class="flex items-center justify-center w-max gap-x-5">
                    <img src="../public/assets/images/logo.svg" alt="Logo" class="w-16 h-16" />
                    <span class="font-semibold text-6xl text-primary">SkillStack</span>
                </div>

                <div class="flex flex-col gap-y-2">
                    <span class="text-2xl text-gray-800 font-medium">Connexion à votre compte</span>
                    <span>Bon retour parmi nous !</span>
                </div>

                <div class="flex flex-col gap-y-2">
                    <UInput variant="outline" color="gray" type="text" size="xl" icon="lucide:user" placeholder="Login" />
                    <UInput variant="outline" color="gray" type="password" size="xl" icon="lucide:key-round" placeholder="Mot de passe" />

                    <UButton class="mt-4" color="primary" variant="solid" size="xl" block>Connexion</UButton>
                </div>
            </div>

            <div class="flex items-center justify-center w-max">
                <span class="whitespace-nowrap">Vous n'avez pas de compte ?</span>
                <UButton class="w-min" color="primary" variant="link" size="xl" block>S'enregistrer</UButton>
            </div>
        </div>

        <div class="bg-[url('/assets/images/pattern.png')] bg-no-repeat bg-cover h-full w-full" />
    </div>
</template>

<script lang="ts" setup></script>
