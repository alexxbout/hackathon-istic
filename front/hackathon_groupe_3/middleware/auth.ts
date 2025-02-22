export default defineNuxtRouteMiddleware(async () => {
    console.log("Auth middleware");
    
    const { userRole, fetchSession } = useAuth();
    const nuxtApp = useNuxtApp();

    if (!userRole.value) {
        await fetchSession();
    }

    if (!userRole.value) {
        console.error("Non connecté, redirection vers /login");
        return nuxtApp.runWithContext(() => navigateTo("/login"));
    }
});
