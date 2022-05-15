import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'
import path from 'path'
// 自动按需导入 Element-Plus 图标：https://element-plus.org/zh-CN/component/icon.html#auto-import
import Icons from 'unplugin-icons/vite'
import IconsResolver from 'unplugin-icons/resolver'
// 自动按需导入 Element-Plus 组件：https://element-plus.org/zh-CN/guide/quickstart.html#%E6%8C%89%E9%9C%80%E5%AF%BC%E5%85%A5
// 就不用在 main.js 或者 .vue 组件中手动导入了。
import AutoImport from 'unplugin-auto-import/vite'
import Components from 'unplugin-vue-components/vite'
import { ElementPlusResolver, NaiveUiResolver } from 'unplugin-vue-components/resolvers'

// https://vitejs.dev/config/
export default defineConfig({
      plugins: [
          vue(), // 配置需要使用的插件列表，这里将vue添加进去
          AutoImport({
            // Auto import functions from Vue, e.g. ref, reactive, toRef...
            // 自动导入 Vue 相关函数，如：ref, reactive, toRef 等
            imports: ['vue'],
            // Auto import functions from Element Plus, e.g. ElMessage, ElMessageBox... (with style)
            // 自动导入 Element Plus 相关函数，如：ElMessage, ElMessageBox... (带样式)
            resolvers: [
                ElementPlusResolver(),
                // Auto import icon components
                // 自动导入图标组件
                IconsResolver({
                  prefix: 'Icon',
                }),
            ], 
          }),
          Components({
            resolvers: [
                // Auto register icon components
                // 自动注册图标组件
                IconsResolver({
                    enabledCollections: ['ep'],
                }),
                // Auto register Element Plus components
                // 自动导入 Element Plus 组件
                ElementPlusResolver(),
                // 自动导入 NaiveUi 组件
                NaiveUiResolver()
            ], 
          }),
          Icons({
            autoInstall: true,
          }),
      ], 
      // 配置文件别名
      // https://cn.vitejs.dev/config/#resolve-alias
      // 这里是将src目录配置别名为 @ 方便在项目中导入src目录下的文件
      resolve: {
          alias: {
              // 设置路径
              '~': path.resolve(__dirname, './'),
              // 设置别名
              '@': path.resolve(__dirname, './src')
          },
          // https://cn.vitejs.dev/config/#resolve-extensions
          extensions: ['.mjs', '.js', '.ts', '.jsx', '.tsx', '.json', '.vue']
      },
      // 打包配置
      build: {
          outDir: '../src/main/resources/static', //指定输出路径
      },
      /*
      本地运行配置，及反向代理配置。配置完成后：
      前端接口调用：axios.get('/api/index')  此时会通过这里的代理规则，将源地址代理到目标地址，从而访问目标地址的接口
      最后，需要注意的是 /api开头的 /不能丢，正则匹配也是
      */
      server: {
          cors: true, // 默认启用并允许任何源
          open: true, // 在服务器启动时自动在浏览器中打开应用程序
          proxy: {
              '/api': {
                  target: 'http://127.0.0.1:8080',   // 所要代理的目标地址
                  changeOrigin: true, // true/false, Default: false - changes the origin of the host header to the target URL
                      // 重写传过来的path路径，比如 `/api/index/1?id=10&name=zs`
                      //（注意:path路径最前面有斜杠（/），因此，正则匹配的时候不要忘了是斜杠（/）开头的；选项的 key 也是斜杠（/）开头的）
                  rewrite: (path) => path.replace(/^\/api/, '')
              }
          }
      }
})
