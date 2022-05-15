import { createApp } from 'vue'
import App from './App.vue'
import VueRouter from './router/router.js'
import Http from './utils/http.js'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(VueRouter)
app.mount('#app')
app.config.globalProperties.$http = Http
