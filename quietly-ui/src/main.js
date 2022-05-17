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
// app.config.globalProperties.axios = Http
app.provide('$axios', Http)

/**
 * 全局监听所有 JS 错误
 * 无法识别 Vue 组件信息
 * 可以捕获一些 Vue 监听不到的错误，如：异步错误
 * @param {*} message 错误消息（字符串）
 * @param {*} source 引发错误的脚本的URL（字符串）
 * @param {*} lineno 发生错误的行号（数值）
 * @param {*} colno 发生错误的行的列号（数值）
 * @param {*} error 错误对象（对象）
 */
window.onerror = function(message, source, lineno, colno, error) {
    console.error(message, source, lineno, colno, error)
}
/**
 * Vue全局错误监听，所有组件错误都会汇总到这里
 * errorCaptured 返回 false ，错误会被提前拦截阻止，这里无法捕获
 * @param {*} err 具体错误信息
 * @param {*} vm 当前错误所在的Vue实例
 * @param {*} info Vue特定的错误信息，错误所在的生命周期钩子
 */
app.config.errorHandler = (err, vm, info) => {
    console.error(err, vm, info)
}