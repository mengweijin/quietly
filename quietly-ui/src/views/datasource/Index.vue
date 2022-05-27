<template>
    <div style="padding: 20px 20px">
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
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="name" label="NAME" width="200"/>
            <el-table-column prop="dbType" label="DB_TYPE" width="100"/>
            <el-table-column prop="url" label="JDBC_URL" />
            <el-table-column prop="username" label="USERNAME" width="120" />
            <el-table-column prop="password" label="PASSWORD" width="150" />
            <el-table-column prop="asDefault" label="AS_DEFAULT" width="150">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <el-popconfirm title="Are you sure to set this as default datasource?" @confirm="handleSetAsDefault(scope.row.id)" >
                            <template #reference>
                                <el-button v-if="scope.row.asDefault == 'Y'" type="success" round size="small" title="Set as default">{{ scope.row.asDefault }}</el-button>
                                <el-button v-else type="danger" round size="small" title="Set as default">{{ scope.row.asDefault }}</el-button>
                            </template>
                        </el-popconfirm>
                    </div>
                </template>
            </el-table-column>   
            <el-table-column fixed="right" label="Operations" width="120">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle size="small" title="Edit" @click="handleAddOrEdit(scope.row.id)"/>
                    <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.$index, scope.row)" >
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
import TableSearch from './Search.vue'
import TableEdit from './Edit.vue'
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
    projectId: null
})

const tableDataList = ref([])

function loadTableData(args) {
    if(!args) {
        args = {}
    }
    args.projectId = activedProjectId.value
    $axios.get('/datasource/list', {params: args}).then((response) => {
        tableDataList.value = response.data
    })
}

function handleSetAsDefault(id) {
    $axios.post('/datasource/setAsDefault/' + id + "?projectId=" + activedProjectId.value).then((response) => {
        loadTableData()
    })
}
function handleAddOrEdit(id) {
    data.value.id = id ? id : null
    setDialogVisiable(true)
}
function handleDelete(index, row) {
    $axios.delete('/datasource/' + row.id).then((response) => {
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
