<template>
    <el-form ref="searchFormRef" :model="searchForm" :inline="true">
        <el-form-item label="ID" prop="id">
            <el-input v-model="searchForm.id" clearable />
        </el-form-item>
        <el-form-item label="NAME" prop="name">
            <el-input v-model="searchForm.name" clearable />
        </el-form-item>
        <el-form-item label="DB_TYPE" prop="dbType">
            <el-select v-model="searchForm.dbType" clearable filterable>
                <el-option v-for="item in dbTypeOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item> 
        <el-form-item>
            <el-button type="primary" :icon="Search" @click="handleSearch()">Search</el-button>
            <el-button type="primary" @click="resetForm()">Reset</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { reactive, ref, toRef, inject } from 'vue'
import { Search } from '@element-plus/icons-vue'
import Utils from '@/util/utils.js'
const $axios = inject('$axios')
const emit = defineEmits(['searchEmit'])
const dbTypeOptions = ref([])
const searchFormRef = ref(null)
const searchForm = ref({
    id: null,
    name: null,
    dbType: null,
})

const resetForm = () => {
    searchFormRef.value.resetFields() 
}
function handleSearch() {
    emit('searchEmit', Utils.trimObjectValue(searchForm.value))
}
function initDbTypeOptions() {
    $axios.get('/datasource/getDbTypes').then((response) => {
        dbTypeOptions.value = response.data
    })
}
onMounted(() => {
    initDbTypeOptions()
})
</script>