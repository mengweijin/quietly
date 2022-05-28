<template>
  <el-container>
    <el-main> <router-view /> </el-main>
  </el-container>
</template>

<script setup>

</script>

<style scoped>
.el-main {
    background-color: white;
    color: #333;
    /* 设置主体 main 高度（减去 header 和 footer 的高度） */
    height: calc(100vh - 60px - 40px);
}
</style>
