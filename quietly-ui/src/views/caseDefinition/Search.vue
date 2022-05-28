<template>
    <el-form ref="searchFormRef" :model="searchForm" :inline="true">
        <el-form-item label="ID" prop="id">
            <el-input v-model="searchForm.id" clearable />
        </el-form-item>
        <el-form-item label="NAME" prop="name">
            <el-input v-model="searchForm.name" clearable />
        </el-form-item>
        <el-form-item label="STATUS" prop="status">
            <el-select v-model="searchForm.status" clearable filterable>
                <el-option v-for="item in caseStepStatusOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item> 
        <el-form-item label="ENABLED" prop="enabled">
            <el-select v-model="searchForm.enabled" clearable filterable>
                <el-option v-for="item in enabledOptions" :key="item" :label="item" :value="item"/>
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
const caseStepStatusOptions = ref([])
const enabledOptions = ref(['Y', 'N'])
const searchFormRef = ref(null)
const searchForm = ref({
    id: null,
    name: null,
    status: null,
    enabled: null,
})

const resetForm = () => {
    searchFormRef.value.resetFields() 
}
function handleSearch() {
    emit('searchEmit', Utils.trimObjectValue(searchForm.value))
}
function initCaseStepStatusOptions() {
    $axios.get('/case-definition/getCaseStepStatusEnums').then((response) => {
        caseStepStatusOptions.value = response.data
    })
}
onMounted(() => {
    initCaseStepStatusOptions()
})
</script>