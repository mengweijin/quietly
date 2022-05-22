<template>
  <el-container>
      <el-header> <Header/> </el-header>
      <el-main> <router-view /> </el-main>
      <el-footer> <Footer/> </el-footer>
    </el-container>
</template>

<script setup>
import Header from './Header.vue'
import Footer from './Footer.vue'
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
