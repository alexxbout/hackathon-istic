export default defineNuxtRouteMiddleware((to, from) => {
    const isLoggedIn = useCookie("auth_token").value !== undefined;

    if (!isLoggedIn) {
        if (to.path === "/") {
            return navigateTo("/login");
        }
        if (to.path !== "/login") {
            return navigateTo("/login");
        }
    } else {
        if (to.path === "/") {
            return navigateTo("/profils");
        }
    }
});
