import axios from 'axios'
import { ElMessage, ElLoading } from 'element-plus'

// axios.defaults.headers['Content-Type'] = 'application/json;charset=utf-8'
// axios中请求配置有baseURL选项，表示请求URL公共部分。参考文档 https://cn.vitejs.dev/guide/env-and-mode.html
// axios.defaults.baseURL = import.meta.env.VITE_APP_SERVER_URL
// 超时，单位：秒
// axios.defaults.timeout = import.meta.env.VITE_AXIOS_TIMEOUT
// 创建axios实例（里面的配置不建议配置为上面注释的全局默认值，假如你还需要访问其他后台服务应用，就可以再按照下面的方式创建一个新对象来操作。）
let axiosInstance = axios.create({
    // axios中请求配置有baseURL选项，表示请求URL公共部分
    baseURL: import.meta.env.VITE_APP_BASE_URL,
    // 超时
    timeout: import.meta.env.VITE_AXIOS_TIMEOUT,
    // 允许携带cookie请求
    withCredentials: true,
    headers: {'Content-Type': 'application/json;charset=utf-8'}
})

// 添加请求拦截器。在发送请求之前做些什么，比如设置 token，权限认证等
axiosInstance.interceptors.request.use(
    config => {
        //config.headers['Content-Type'] = "application/json;charset=utf-8";
        //config.baseURL = import.meta.env.VITE_APP_SERVER_URL;
        return config;
    },
    error => {
        // 对请求错误做些什么
        console.log(error)
        return Promise.reject(error)
    }
)

// 添加响应拦截器
axiosInstance.interceptors.response.use(
    response => {
        // 对响应数据做点什么
        return response;
    },
    error => {
        // 对响应错误做点什么
        console.log(error)
        if (error.response.status) {
            switch (error.response.status) {
                case 400:
                    ElMessage.error({ message: error.response.status + ": " + JSON.stringify(error.response.data), duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 401:
                    ElMessage.error({ message: error.response.status + " Unauthorized", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 403:
                    ElMessage.error({ message: error.response.status + " Forbidden", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 404:
                    ElMessage.error({ message: error.response.status + " Not Found", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 405:
                    ElMessage.error({ message: error.response.status + " Method Not Allowed", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 406:
                    ElMessage.error({ message: error.response.status + " Not Acceptable", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 408:
                    ElMessage.error({ message: error.response.status + " Request Timeout", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                case 500:
                    ElMessage.error({ message: error.response.status + " Internal Server Error", duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
                default:
                    ElMessage.error({ message: error.response.status + ": " + JSON.stringify(error.response.data), duration: import.meta.env.VITE_AXIOS_ERROR_MESSAGE_DURATION, showClose: true })
                    break;
            }
        }
        return Promise.reject(error);
    }
)


/**
 * get 请求方法，添加了全屏遮罩层
 * @param {String} url
 * @param {Object} config 比如传入参数：let config = {params: {userId: 123}}
 */
axiosInstance.loading = async function(url, config) {
    // 添加全屏遮罩层
    let loadingInstance = ElLoading.service()
    const response = await axiosInstance.get(url, config);
    // 拿到结果后，关闭遮罩层
    loadingInstance.close();
    // 原样返回结果
    resolve(response);
}

/**
 * Get Download file
 * @param {String} url
 * @param {String} fileName
 * @returns
 */
axiosInstance.download = async function(url, fileName) {
    const response = await axiosInstance.get(url, { responseType: 'blob' });
    let fileURL = window.URL.createObjectURL(new Blob([response.data]));
    let fileLink = document.createElement('a');
    fileLink.href = fileURL;
    fileLink.setAttribute('download', fileName);
    document.body.appendChild(fileLink);
    fileLink.click();
    fileLink.remove();
}

export default axiosInstance


/**
 * 说明：
 * 1. 在 vue 中给 data() 中的对象赋值时，下面的 then() 需要用 => 方式，而不能用 function(response) {} 的方式，否则 then 代码块里面的 this 指向的是 axios，而不是 vue。
 *
 * 2. 当使用 get 请求时，参数传递的 key 要用 params
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.get('/doc-namespace/' + namespaceId, { params: requestParams }).then((response) => {
 *    this.namespace = response.data
 * })
 *
 * 3. 当使用除了 get 方式以外的请求类型时，参数传递直接传入即可
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.post('/doc-namespace/', requestParams).then((response) => {
 *    // do something
 * })
 *
 * 4. 这里扩展了一个 loading 全屏遮罩层的 get 方式的方法
 * 假如请求参数对象为 let requestParams = {id: 1, name: 'jack'}
 * this.$http.loading('/doc-namespace/' + namespaceId, { params: requestParams }).then((response) => {
 *    this.namespace = response.data
 * })
 *
 * 5. 这里扩展了一个 download 下载文件的 get 方式的方法
 * 假如: fileName = a.zip
 * this.$http.download('/common/download/' + fileId, fileName)
 */