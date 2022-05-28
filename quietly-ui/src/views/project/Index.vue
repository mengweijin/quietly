<template>
    <div>
        <TableSearch @searchEmit="loadTableData"></TableSearch>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="projectDataList" stripe highlight-current-row height="calc(100vh - 60px - 40px - 145px)">
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="name" label="NAME" />
            <el-table-column prop="baseUrl" label="BASE_URL" />
            <el-table-column prop="createTime" label="CREATE_TIME" width="180">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <el-icon><timer /></el-icon>
                        <span style="margin-left: 10px">{{ scope.row.createTime }}</span>
                    </div>
                </template>
            </el-table-column>                   
            <el-table-column prop="updateTime" label="UPDATE_TIME" width="180">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <el-icon><timer /></el-icon>
                        <span style="margin-left: 10px">{{ scope.row.updateTime }}</span>
                    </div>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" width="200">
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
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import TableSearch from './Search.vue'
import TableEdit from './Edit.vue'
import { useProject } from "@/store/store.js"
 // 使普通数据变响应式的函数  
import { storeToRefs } from 'pinia'
const $axios = inject('$axios')
// 实例化仓库函数
const store = useProject()
// 解构并使数据具有响应式 ref
const { projectDataList } = storeToRefs(store)

const data = ref({
    visiable: false,
    id: null
})

function loadTableData(args) {
    $axios.get('/project/list', {params: args}).then((response) => {
        projectDataList.value = response.data
    })
}

function handleDelete(index, row) {
    $axios.delete('/project/' + row.id).then((response) => {
        projectDataList.value.splice(index, 1)
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
    //setTableData()
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
