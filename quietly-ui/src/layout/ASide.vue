<template>
  <el-aside :width="isCollapse ? '64px' : '200px'">
    <div class="toggle-button" @click="toggleCollapse">
      <el-icon :size="20" style="vertical-align: middle" v-show="isCollapse"><expand /></el-icon>
      <el-icon :size="20" style="vertical-align: middle" v-show="!isCollapse"><fold /></el-icon>
    </div>
    
    <el-menu router mode="vertical" :collapse="isCollapse" :collapse-transition="false" unique-opened :default-active="activeMenu" @select="onSelectMenu">
      <el-menu-item index="/">           <el-icon><HomeFilled /></el-icon><template #title>Project Home</template></el-menu-item>
      <el-menu-item index="/datasource">    <el-icon><Coin />   </el-icon><template #title>数据库管理</template></el-menu-item>
      <el-menu-item index="/api-definition"><el-icon><Link />   </el-icon><template #title>接口定义</template></el-menu-item>
      <el-menu-item index="/1">              <el-icon><Tickets /></el-icon><template #title>API 自动化测试用例</template></el-menu-item>
      <el-menu-item index="/2">              <el-icon><Tickets /></el-icon><template #title>E2E 自动化测试用例</template></el-menu-item>
      <el-menu-item index="/3" disabled>     <el-icon><Tickets /></el-icon><template #title>UI 自动化测试用例</template></el-menu-item>
      <el-menu-item index="/4" disabled>     <el-icon><Tickets /></el-icon><template #title>手工测试用例</template></el-menu-item>
      <!-- <el-sub-menu index="/">
        <template #title><el-icon><Platform /></el-icon> <span>接口测试</span></template>
        <el-menu-item index="/"><el-icon><Tickets /></el-icon>接口测试用例</el-menu-item>
      </el-sub-menu> -->
      <el-menu-item index="/5">              <el-icon><QuestionFilled /></el-icon><template #title>使用文档</template></el-menu-item>
    </el-menu>
  </el-aside>
  
</template>

<script setup>
import { reactive, ref, watch } from 'vue'
const activeMenu = ref('')
const isCollapse = ref(false)
const toggleCollapse = () => { isCollapse.value = !isCollapse.value }

function onSelectMenu(index, indexPath) {
    activeMenu.value = index
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.toggle-button{
  /* 添加背景颜色 */
  background-color: #4A5064;
  /* 设置文本大小 */
  font-size:10px;
  /* 设置文本行高 */
  line-height:40px;
  /* 设置文本颜色 */
  color:#fff;
  /* 设置文本居中 */
  text-align: center;
  /* 设置文本间距 */
  letter-spacing: 0.2em;
  /* 设置鼠标悬浮变小手效果 */
  cursor:pointer;
}
.el-menu {
  --el-menu-text-color: #D2D2D2;
  --el-menu-bg-color: #4A5064;
  --el-menu-hover-text-color: #5FB878;
  --el-menu-hover-bg-color: #4A5064;
  --el-menu-active-color: #1E9FFF;
}
/* 
.el-menu-item {
  color: #D2D2D2;
  background-color: #4A5064;
}
.el-menu-item-group {
  background-color: #4A5064;
}
.el-sub-menu {
  background-color: #4A5064;
  --el-menu-text-color: #D2D2D2;
  --el-menu-bg-color: #4A5064;
  --el-menu-hover-bg-color: #4A5064;
}  */
</style>