<template>
  <div>
      <el-row>
        <el-col :span="18">
          <el-menu router mode="horizontal">
            <el-menu-item :index="'/'"><img src="/logo.png" style="height: var(--el-menu-item-height);"></el-menu-item>
            <el-menu-item :index="'/'" style="height: var(--el-menu-item-height);">
              <span>当前项目：</span>
              <el-select v-model="currentProjectId" placeholder="选择项目" size="small" @change="setCurrentProjectIdToLocalStorage">
                <el-option v-for="item in projectList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-menu-item>
            <el-menu-item index="/project/home">
              <el-icon><Grid /></el-icon> <template #title>项目管理</template>
            </el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="6">
            <el-menu router mode="horizontal" style="justify-content:flex-end; ">
              <!-- <el-menu-item v-bind:index="''"><el-icon><avatar /></el-icon>Admin</el-menu-item> -->
              <el-menu-item :index="'/'"><el-icon><avatar /></el-icon>Admin</el-menu-item>
              <el-menu-item :index="'https://gitee.com/mengweijin/'">
                <a href="https://gitee.com/mengweijin/" target="_blank" style="text-decoration:none;">
                  <img alt="Gitee" src="/favicon-gitee.ico" style="height: 18px;vertical-align: middle;"> Gitee
                </a>
              </el-menu-item>
              <el-sub-menu index="/">
                <template #title><el-icon><setting /></el-icon><span>设置</span></template>
                <!-- <el-menu-item v-bind:index="''"><el-icon><document /></el-icon>item one</el-menu-item>
                <el-menu-item v-bind:index="''"><el-icon><document /></el-icon>item two</el-menu-item>
                <el-menu-item v-bind:index="''"><el-icon><document /></el-icon>item three</el-menu-item> -->
              </el-sub-menu>
            </el-menu>
        </el-col>
      </el-row>
    
  </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import projectStore from '@/store/projectStore.js'
const $axios = inject('$axios')

/**
 * state、getters、actions 里面属性或者方法  都是通过 projectStore “点” 出来使用的
 * 如果想将状态数据解构出来直接使用  必须引入storeToRefs（否则不是响应式） 来自于 pinia（类似于 reactive响应式，结构使用toRefs）
 * import { storeToRefs } from 'pinia'
 * import projectStore from '@/store/projectStore.js'
 * const { projectId } = storeToRefs(projectStore)
 */

// ref 和 reactive 都可以给变量添加响应性。
// ref 取值和赋值要用 .value。如赋值：currentProjectId.value = '1'
const currentProjectId = ref(null)
const projectList = ref([])

/**
 * import { inject } from "vue"
 * const currentProjectId = inject('currentProjectId')
 */
provide('currentProjectId', readonly(currentProjectId))

const setProjectList = () => {
    $axios.get('/project/list').then((response) => {
        projectList.value = response.data
        if(!currentProjectId.value && projectList.value && projectList.value.length > 0) {
          currentProjectId.value = projectList.value[0].id
          setCurrentProjectIdToLocalStorage(currentProjectId.value)
        }
        debugger
        projectStoreChange(currentProjectId.value)
    })
}

/**
 * window.localStorage.removeItem('CURRENT_USER_ACTIVE_PROJECT_ID')
 * window.sessionStorage.removeItem('CURRENT_USER_ACTIVE_PROJECT_ID')
 */
function getCurrentProjectIdFromLocalStorage() {
    return window.localStorage.getItem('CURRENT_USER_ACTIVE_PROJECT_ID')
}
function setCurrentProjectIdToLocalStorage(projectId) {
    window.localStorage.setItem('CURRENT_USER_ACTIVE_PROJECT_ID', projectId)
}

function projectStoreChange(projectId) {
    projectStore.projectId = projectId
}

onMounted(() => {
    currentProjectId.value = getCurrentProjectIdFromLocalStorage()
    setProjectList()
})
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-menu {
  --el-menu-text-color: #D2D2D2;
  --el-menu-bg-color: #393D49;
  --el-menu-hover-text-color: #5FB878;
  --el-menu-hover-bg-color: #393D49;
  --el-menu-active-color: #1E9FFF;
}

img{
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 100%;
}
</style>