<script setup>
import Header from './Header.vue'
import Footer from './Footer.vue'
import ASide from './ASide.vue'
</script>

<template>
  <el-container>
      <el-header> <Header/> </el-header>
        <el-container>
          <ASide/>
          <el-main> <router-view /> </el-main>
        </el-container>
      <el-footer> <Footer/> </el-footer>
    </el-container>
</template>

<script>
export default {
  name: 'Layout',
}
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.el-header {
    background-color: #393D49;
    color: #D2D2D2;
    text-align: center;
    line-height: 60px;
    height: 60px;
}
.el-aside {
    background-color: #4A5064;
    color: #333;
    /* 设置左侧 aside 高度。vh就是当前屏幕可见高度的1%。因此，100vh就等于当前浏览器的视窗高度，即浏览器内部的可视区域的高度大小。 */
    height: calc(100vh - 60px - 40px);
}
.el-main {
    background-color: white;
    color: #333;
    padding: 0;
    /* 设置主体 main 高度（减去 header 和 footer 的高度） */
    height: calc(100vh - 60px - 40px);
}
.el-footer {
    background-color: white;
    color: #333;
    text-align: center;
    line-height: 40px;
    height: 40px;
}
</style>
