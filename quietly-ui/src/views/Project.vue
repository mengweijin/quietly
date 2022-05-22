<template>
    <div style="padding: 20px 20px">
        <el-form :inline="true" :model="queryForm">
            <el-form-item label="ID" prop="id" :label-width="formLabelWidth">
                <el-input v-model="queryForm.id" />
            </el-form-item>
            <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
                <el-input v-model="queryForm.name" />
            </el-form-item>
            <el-form-item>
                <el-button type="primary" :icon="Search" @click="handleQuery()">Query</el-button>
            </el-form-item>
        </el-form>

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

        <ProjectEdit :dialogVisible="dialogVisible" :rowData="rowData" @closeDialogEmit="setDialogVisible(false)" @refreshEmit="setProjectList()"></ProjectEdit>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { Search, Plus, Edit, Delete } from '@element-plus/icons-vue'
import ProjectEdit from '@/components/project/Edit.vue'
const $axios = inject('$axios')

const queryForm = reactive({
    id: null,
    name: null,
})

const handleQuery = () => {
    setProjectList(queryForm)
}

const dialogVisible = ref(false)
const rowData = ref(null)

const projectList = ref([])

const setProjectList = (args) => {
  $axios.get('/project/list', {params: args}).then((response) => {
    projectList.value = response.data
  })
}

const handleDelete = (index, row) => {
  $axios.delete('/project/' + row.id).then((response) => {
    projectList.value.splice(index, 1)
  })
}

function handleAddOrEdit(index, row) {
    if(row) {
        rowData.value = row
    }
    setDialogVisible(true)
}

function setDialogVisible(isVisible) {
    dialogVisible.value = isVisible
}

onMounted(() => {
  setProjectList()
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
