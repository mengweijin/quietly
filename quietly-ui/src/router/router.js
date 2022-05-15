import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'

const routes = [
    { 
        path: '/', 
        name: 'home',
        component: Layout,
        redirect: '/home',
        children: [
            {
                path: '/home',
                components: {
                    default: () => import ('../views/Home.vue')
                }
            }
        ]
    }
]

// 路由根路径：'/ui/'; 为什么设置路由根路径？避免路由路径和后台接口路径一样，当刷新页面时变成调用后台接口而返回 JSON 的问题。
// 部署到 nginx 服务器下，我们一般 router 的 hitory 使用 createWebHistory 来去掉 url 中的 #
// 但如果要打包并部署到 springboot 下的 static 目录，默认只能用 createWebHashHistory 才不会有问题。
// 但我们又想使用 createWebHistory 模式，还不想刷新时出现页面 404 的问题，此时需要在后端处理了。
// 参考 java 类：NotFoundErrorPageRegistrar.java
const router = createRouter({
    history: createWebHistory('/ui/'),
    routes, 
})

// 根据路由元信息设置页面标题
router.beforeEach((to, from) => {
    window.document.title = to.meta.title || import.meta.env.VITE_APP_TITLE
})

export default router