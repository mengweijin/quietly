<template>
    <div style="padding: 20px 20px">
        <el-form :inline="true" :model="formInline" class="demo-form-inline">
            <el-form-item label="Approved by">
                <el-input v-model="formInline.user" placeholder="Approved by" />
            </el-form-item>
            <el-form-item label="Activity zone">
                <el-select v-model="formInline.region" placeholder="Activity zone">
                    <el-option label="Zone one" value="shanghai" />
                    <el-option label="Zone two" value="beijing" />
                </el-select>
            </el-form-item>
            <el-form-item>
            <el-button type="primary" @click="onSubmit">Query</el-button>
            </el-form-item>
        </el-form>
        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus">添加</el-button>
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
                    <el-button type="primary" :icon="Edit" circle size="small" @click="handleEdit(scope.$index, scope.row)"/>
                    <el-button type="danger" :icon="Delete" circle size="small" @click="handleDelete(scope.$index, scope.row)"/>
                </template>
            </el-table-column>
        </el-table>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { Plus, Delete, Edit } from '@element-plus/icons-vue'
const $axios = inject('$axios')

const formInline = reactive({
  user: '',
  region: '',
})
const projectList = ref([])

const setProjectList = () => {
  $axios.get('/project/list').then((response) => {
    projectList.value = response.data
  })
}

const handleEdit = (index, row) => {
  console.log(index, row)
}
const handleDelete = (index, row) => {
  $axios.delete('/project/' + row.id)
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
