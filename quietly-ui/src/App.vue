<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<script setup>

</script>

<!-- /** el-config-provider: Element-Plus global default language setting */ -->
<script>
  import { defineComponent } from 'vue'
  import { ElConfigProvider } from 'element-plus'
  import zhCn from 'element-plus/lib/locale/lang/zh-cn'

  export default defineComponent({
    components: {
      ElConfigProvider,
    },
    setup() {
      return {
        locale: zhCn,
      }
    },
  })
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: left;
  color: #2c3e50;
  margin-top: 0px;
}

html, body {
    margin: 0;
    padding: 0;
    height: 100%;
}
</style>
