// https://nuxt.com/docs/api/configuration/nuxt-config
export default defineNuxtConfig({
  compatibilityDate: '2024-11-01',
  devtools: { enabled: true },
  modules: ['@nuxt/ui'],
  css: ['./public/assets/css/main.css'],
  ui: {
    devtools: {
      enabled: false
    },
    colors: {
      primary: 'gray',
      secondary: 'green',
      info: '#41B883',
      success: '#41B883',
      warning: '#E6A23C',
      error: '#F56C6C',
      neutral: '#35495E'
    },

  }
})
