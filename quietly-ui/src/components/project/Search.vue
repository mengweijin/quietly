<template>
    <el-form ref="searchFormRef" :model="searchForm" :inline="true">
        <el-form-item label="ID" prop="id">
            <el-input v-model="searchForm.id" />
        </el-form-item>
        <el-form-item label="NAME" prop="name">
            <el-input v-model="searchForm.name" />
        </el-form-item>
        <el-form-item>
            <el-button type="primary" :icon="Search" @click="handleSearch()">Search</el-button>
            <el-button type="primary" @click="resetForm()">Reset</el-button>
        </el-form-item>
    </el-form>
</template>

<script setup>
import { reactive, ref, toRef } from 'vue'
import { Search } from '@element-plus/icons-vue'
const emit = defineEmits(['searchEmit'])

const searchFormRef = ref(null)
const searchForm = reactive({
    id: null,
    name: null,
})

const handleSearch = () => {
    // 处理 empty String issue
    let args = {
        id: formatValue(searchForm.id),
        name: formatValue(searchForm.name),
    }
    
    emit('searchEmit', args)
}
function resetForm() {
    searchFormRef.value.resetFields() 
}

function formatValue(val) {
    if(val != null && val != undefined && val.trim() != '') {
        return val.trim()
    }
    return null
}
</script>