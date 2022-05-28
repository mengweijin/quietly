<template>
    <div>
        <TableSearch @searchEmit="loadTableData"></TableSearch>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="tableDataList" stripe highlight-current-row height="calc(100vh - 60px - 40px - 150px)">
            <el-table-column type="expand">
                <template #default="props">
                    <div>
                        <p>PROJECT_ID: {{ props.row.projectId }}</p>
                        <p>CREATE_TIME: {{ props.row.createTime }}</p>
                        <p>UPDATE_TIME: {{ props.row.updateTime }}</p>
                        <p>DESCRIPTION: {{ props.row.description }}</p>
                        <p style="border-style:solid;border-width:1px;">HEADERS_SAMPLE: <json-viewer copyable preview-mode show-double-quotes :value="JSON.parse(props.row.headersSample)"></json-viewer></p>
                        <p style="border-style:solid;border-width:1px;">BODY_ARGS_SAMPLE: <json-viewer copyable preview-mode show-double-quotes :value="JSON.parse(props.row.bodyArgsSample)"></json-viewer></p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="name" label="NAME" />
            <el-table-column prop="httpMethod" label="HTTP_METHOD" width="130"/>
            <el-table-column prop="url" label="API_URL" />
            <el-table-column prop="requestMediaType" label="REQUEST_MEDIA_TYPE" width="240" />  
            <el-table-column fixed="right" label="Operations" width="120">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle size="small" title="Edit" @click="handleAddOrEdit(scope.row.id)"/>
                    <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.$index, scope.row)" v-if="false">
                        <template #reference>
                            <el-button type="danger" :icon="Delete" circle size="small" title="Delete"/>
                        </template>
                    </el-popconfirm>
                    
                </template>
            </el-table-column>
        </el-table>

        <TableEdit :data="data" @closeDialogEmit="setDialogVisiable(false)" @refreshEmit="loadTableData()"></TableEdit>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { Plus, Edit, Delete, Open } from '@element-plus/icons-vue'
import JsonViewer from 'vue-json-viewer'
import TableSearch from './Search.vue'
import TableEdit from './Edit.vue'
import Utils from '@/util/utils.js'
import { useProject } from "@/store/store.js"
 // 使普通数据变响应式的函数  
import { storeToRefs } from 'pinia'
// 实例化仓库函数
const store = useProject()
// 解构并使数据具有响应式 ref
const { activedProjectId } = storeToRefs(store)
const $axios = inject('$axios')

const data = ref({
    visiable: false,
    id: null,
})

const tableDataList = ref([])

function loadTableData(args) {
    args = args ? args : {}
    args.projectId = activedProjectId.value
    $axios.get('/api-definition/page', {params: args}).then((response) => {
        tableDataList.value = response.data.records
    })
}

function handleAddOrEdit(id) {
    data.value.id = id ? id : null
    setDialogVisiable(true)
}
function handleDelete(index, row) {
    $axios.delete('/api-definition/' + row.id).then((response) => {
        tableDataList.value.splice(index, 1)
    })
}

function setDialogVisiable(isVisiable) {
    data.value.visiable = isVisiable
}

onMounted(() => {
    loadTableData()
})
</script>

<style scoped>
img{
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 450px;
}
</style>
