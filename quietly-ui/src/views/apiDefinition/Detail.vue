<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="Details">
        <el-descriptions title="Api Definition Details" direction="horizontal" :column="1" :size="28" border>
            <el-descriptions-item label="NAME">{{apiDefinition.name}}</el-descriptions-item>
            <el-descriptions-item label="HTTP_METHOD">{{apiDefinition.httpMethod}}</el-descriptions-item>
            <el-descriptions-item label="REQUEST_MEDIA_TYPE">{{apiDefinition.requestMediaType}}</el-descriptions-item>
            <el-descriptions-item label="URL">{{apiDefinition.url}}</el-descriptions-item>
            <el-descriptions-item label="DESCRIPTION">{{apiDefinition.description}}</el-descriptions-item>
            <el-descriptions-item label="HEADERS_SAMPLE">{{apiDefinition.headersSample}}</el-descriptions-item>
            <el-descriptions-item label="BODY_ARGS_SAMPLE">{{apiDefinition.bodyArgsSample}}</el-descriptions-item>
        </el-descriptions>
    </el-dialog>
</template>

<script setup>
import { reactive, ref, toRef, toRefs, inject } from 'vue'
const $axios = inject('$axios')

const props = defineProps({'data': Object})
const data = toRef(props, 'data')
const apiDefinition = ref(null)

function initApiDefinitionDetail(id) {
    $axios.get('/api-definition/' + id).then((response) => {
        apiDefinition.value = response.data
    })
}

function onOpenDialog() {
    initApiDefinitionDetail(data.value.id)
}
</script>
