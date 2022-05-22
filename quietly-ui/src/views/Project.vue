<template>
    <div style="padding: 20px 20px">
        <ProjectSearch @searchEmit="setTableData"></ProjectSearch>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="projectList" stripe border>
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
            <el-table-column fixed="right" label="Operations">
                <template #default="scope">
                    <el-button type="primary" :icon="Edit" circle size="small" @click="handleAddOrEdit(scope.$index, scope.row)"/>
                    <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.$index, scope.row)" >
                        <template #reference>
                            <el-button type="danger" :icon="Delete" circle size="small"/>
                        </template>
                    </el-popconfirm>
                    
                </template>
            </el-table-column>
        </el-table>

        <ProjectEdit :dialogVisible="dialogVisible" :rowData="rowData" @closeDialogEmit="setDialogVisible(false)" @refreshEmit="setTableData()"></ProjectEdit>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { Plus, Edit, Delete } from '@element-plus/icons-vue'
import ProjectSearch from '@/components/project/Search.vue'
import ProjectEdit from '@/components/project/Edit.vue'
const $axios = inject('$axios')

const dialogVisible = ref(false)
const rowData = ref(null)

const projectList = ref([])

function setTableData(args) {
    $axios.get('/project/list', {params: args}).then((response) => {
        projectList.value = response.data
    })
}

function handleDelete(index, row) {
    $axios.delete('/project/' + row.id).then((response) => {
        projectList.value.splice(index, 1)
    })
}

function handleAddOrEdit(index, row) {
    if(row) {
        rowData.value = row
    } else {
        rowData.value = null
    }
    setDialogVisible(true)
}

function setDialogVisible(isVisible) {
    dialogVisible.value = isVisible
}

onMounted(() => {
    setTableData()
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
