// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
    app: {
        baseURL: "/",
    },
    modules: ["@nuxt/ui"],
    compatibilityDate: "2024-11-01",
    devtools: { enabled: true },
    colorMode: {
        preference: "light",
    },
    routeRules: {
        "/api/v1/**": {
            proxy: { to: "http://backend-service:8080/api/v1/**" },
        },
    },
});