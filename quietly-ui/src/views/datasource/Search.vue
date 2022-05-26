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
import { reactive, ref, toRef, inject } from 'vue'
import { Search } from '@element-plus/icons-vue'
import Utils from '@/util/utils.js'
const emit = defineEmits(['searchEmit'])

const searchFormRef = ref(null)
const searchForm = ref({
    id: null,
    name: null,
})

const handleSearch = () => {
    // 处理 empty String issue
    emit('searchEmit', Utils.trimObjectValue(searchForm.value))
}
const resetForm = () => {
    searchFormRef.value.resetFields() 
}

</script>