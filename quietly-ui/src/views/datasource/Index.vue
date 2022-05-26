<template>
    <div style="padding: 20px 20px">
        <TableSearch @searchEmit="setTableData"></TableSearch>

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
            <el-table-column prop="name" label="NAME" />
            <el-table-column prop="dbType" label="DB_TYPE" width="100"/>
            <el-table-column prop="url" label="JDBC_URL" />
            <el-table-column prop="username" label="USERNAME" width="120" />
            <el-table-column prop="password" label="PASSWORD" />
            <el-table-column prop="asDefault" label="AS_DEFAULT" width="150"/>
            <el-table-column fixed="right" label="Operations">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle size="small" @click="handleAddOrEdit(scope.row.id)"/>
                    <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.$index, scope.row)" >
                        <template #reference>
                            <el-button type="danger" :icon="Delete" circle size="small"/>
                        </template>
                    </el-popconfirm>
                    
                </template>
            </el-table-column>
        </el-table>

        <TableEdit :data="data" @closeDialogEmit="setDialogVisiable(false)" @refreshEmit="setTableData()"></TableEdit>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import TableSearch from './Search.vue'
import TableEdit from './Edit.vue'
import { storeToRefs } from 'pinia'
import projectStore from '@/store/projectStore.js'
const { projectId } = storeToRefs(projectStore)
const $axios = inject('$axios')

debugger
const data = ref({
    visiable: false,
    id: null,
    projectId: null
})

const tableDataList = ref([])

function setTableData(args) {
    $axios.get('/datasource/list', {params: args}).then((response) => {
        tableDataList.value = response.data
    })
}

function handleDelete(index, row) {
    $axios.delete('/datasource/' + row.id).then((response) => {
        tableDataList.value.splice(index, 1)
    })
}

function handleAddOrEdit(id) {
    data.value.id = id ? id : null
    setDialogVisiable(true)
}

function setDialogVisiable(isVisiable) {
    data.value.visiable = isVisiable
}

onMounted(() => {
    debugger
    setTableData({projectId: projectId})
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
