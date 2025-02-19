<template>
    <div class="flex items-center justify-center w-screen h-screen">
        <UCard class="w-[400px]">
            <template #header>
                <div class="flex items-center justify-center w-max gap-x-2">
                    <img src="../public/assets/images/logo.svg" alt="Logo" class="w-10 h-10" />
                    <span class="font-semibold text-4xl text-primary">SkillStack</span>
                </div>
            </template>

            <div class="flex flex-col gap-y-10">
                <div class="flex flex-col gap-y-2">
                    <span class="text-xl text-gray-800 font-medium">Connexion à votre compte</span>
                    <span>Bon retour parmi nous !</span>
                </div>

                <div class="flex flex-col gap-y-2">
                    <UInput variant="outline" color="gray" type="text" size="xl" icon="lucide:user" placeholder="Login" />
                    <UInput variant="outline" color="gray" type="password" size="xl" icon="lucide:key-round" placeholder="Mot de passe" />

                    <UButton class="mt-4" color="primary" variant="solid" size="xl">Connexion</UButton>
                </div>
            </div>
        </UCard>
    </div>
</template>

<script lang="ts" setup></script>
