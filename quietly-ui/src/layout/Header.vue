<template>
  <div>
      <el-row>
        <el-col :span="18">
          <el-menu router mode="horizontal">
            <el-menu-item :index="'/'"><img src="/logo.png" style="height: var(--el-menu-item-height);"></el-menu-item>
            <el-menu-item :index="'/'" style="height: var(--el-menu-item-height);">
              <span>当前项目：</span>
              <el-select v-model="projectData.currentProjectId" placeholder="选择项目" size="small">
                <el-option v-for="item in projectData.projectList" :key="item.id" :label="item.name" :value="item.id" />
              </el-select>
            </el-menu-item>
            <el-menu-item index="/project">
              <el-icon><Grid /></el-icon> <template #title>项目管理</template>
            </el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="6">
            <el-menu router mode="horizontal" style="justify-content:flex-end; ">
              <!-- <el-menu-item v-bind:index="''"><el-icon><avatar /></el-icon>Admin</el-menu-item> -->
              <el-menu-item :index="'/admin'"><el-icon><avatar /></el-icon>Admin</el-menu-item>
              <el-menu-item :index="'https://gitee.com/mengweijin/'">
                <a href="https://gitee.com/mengweijin/" target="_blank" style="text-decoration:none;">
                  <img alt="Gitee" src="/favicon-gitee.ico" style="height: 18px;vertical-align: middle;"> Gitee
                </a>
              </el-menu-item>
              <el-sub-menu index="/setting">
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
import { ref, reactive, inject } from "vue"
const $axios = inject('$axios')

const projectData = reactive({
  currentProjectId: null,
  projectList: []
});

const getProjectList = () => {
  $axios.get('/project/list').then((response) => {
    projectData.projectList = response.data
    // set sessionStorage
    // window.localStorage.removeItem('CURRENT_USER_ACTIVE_PROJECT_ID')
    // window.sessionStorage.removeItem('CURRENT_USER_ACTIVE_PROJECT_ID')
  let projectId = window.sessionStorage.getItem('CURRENT_USER_ACTIVE_PROJECT_ID')
    if(projectId) {
      projectData.currentProjectId = projectId
    } else {
      if(projectData.projectList && projectData.projectList.length > 0) {
        projectData.currentProjectId = projectData.projectList[0].id
        window.sessionStorage.setItem('CURRENT_USER_ACTIVE_PROJECT_ID', projectData.currentProjectId)
      }
    }
  })
}

onMounted(() => {
  getProjectList()
});
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