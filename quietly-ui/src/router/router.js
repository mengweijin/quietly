import { createRouter, createWebHistory } from 'vue-router'
import Layout from '../layout/Layout.vue'
import LayoutMain from '../layout/LayoutMain.vue'
import LayoutAsideMain from '../layout/LayoutAsideMain.vue'

const routes = [
    { 
        path: '/', 
        component: Layout,
        redirect: '/quietly/home',
        children: [
            {
                path: '/project',
                component: LayoutMain,
                children: [
                    {
                        path: '/project/home',
                        components: {
                            default: () => import ('@/views/project/Index.vue')
                        }
                    }
                ]
            },
            {
                path: '/quietly',
                component: LayoutAsideMain,
                children: [
                    {
                        path: 'home',
                        components: {
                            default: () => import ('../views/Home.vue')
                        }
                    },
                    {
                        path: 'datasource',
                        components: {
                            default: () => import ('@/views/datasource/Index.vue')
                        }
                    },
                    {
                        path: 'api-definition',
                        components: {
                            default: () => import ('@/views/apiDefinition/Index.vue')
                        }
                    },
                    {
                        path: 'case-definition-api',
                        components: {
                            default: () => import ('@/views/caseDefinition/Index.vue')
                        },
                        props: { caseType: 'API' }
                    },
                    {
                        path: 'case-definition-e2e-flow',
                        components: {
                            default: () => import ('@/views/caseDefinition/Index.vue')
                        },
                        props: { caseType: 'E2E_FLOW' }
                    },
                    {
                        path: 'case-definition-web-ui',
                        components: {
                            default: () => import ('@/views/caseDefinition/Index.vue')
                        },
                        props: { caseType: 'WEB_UI' }
                    },
                    {
                        path: 'case-definition-mobile-ui',
                        components: {
                            default: () => import ('@/views/caseDefinition/Index.vue')
                        },
                        props: { caseType: 'MOBILE_UI' }
                    },
                    {
                        path: 'case-manual-testing',
                        components: {
                            default: () => import ('@/views/caseDefinition/Index.vue')
                        },
                    },
                    {
                        path: 'step-definition',
                        components: {
                            default: () => import ('@/views/stepDefinition/Index.vue')
                        }
                    }
                ]
            },
            
        ]
    }, 
    
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