<template>
    <div>
        <el-breadcrumb separator="|" style="margin-bottom: 20px;">
            <el-breadcrumb-item>测试用例 Step 详情</el-breadcrumb-item>
            <el-breadcrumb-item>测试用例 ID：{{ route.query.caseId }}</el-breadcrumb-item>
            <el-breadcrumb-item>测试用例名称：{{ route.query.name }}</el-breadcrumb-item>
        </el-breadcrumb>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="tableDataList" stripe highlight-current-row height="calc(100vh - 60px - 40px - 145px)">
            <el-table-column type="expand">
                <template #default="props">
                    <div>
                        <p>ID: {{ props.row.id }}</p>
                        <p>CASE_ID: {{ props.row.caseId }}</p>
                        <p>CREATE_TIME: {{ props.row.createTime }}</p>
                        <p>UPDATE_TIME: {{ props.row.updateTime }}</p>
                        <p>EXPECT_VALUE: {{ props.row.expectValue }}</p>
                        <p>ACTUAL_VALUE: {{ props.row.actualValue }}</p>
                        <p>ERROR_INFO: {{ props.row.errorInfo }}</p>
                        <p>DATASOURCE_ID: {{ props.row.datasourceId }}</p>
                        <p>API_ID: {{ props.row.apiId }}</p>
                        <p>API_ARGS: {{ props.row.apiArgs }}</p>
                        <p style="border-style:solid;border-width:1px;">API_ARGS: <json-viewer copyable preview-mode show-double-quotes :value="JSON.parse(props.row.apiArgs)"></json-viewer></p>
                        <p style="border-style:solid;border-width:1px;">API_REQUEST_ACTUAL_INFO: <json-viewer copyable preview-mode show-double-quotes :value="JSON.parse(props.row.apiRequestActualInfo)"></json-viewer></p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="stepType" label="STEP_TYPE" width="350" />
            <el-table-column prop="expression" label="EXPRESSION" />
            <el-table-column prop="expectValue" label="EXPECT_VALUE" />
            <el-table-column prop="seq" label="SEQUENCE" width="160">
                <template #default="scope">
                    <el-button type="primary" round size="small" :icon="Top" title="up" :disabled="scope.$index == 0" @click="handleSequenceUp(scope.$index, scope.row)"></el-button>
                    <el-button round size="small">{{ scope.row.seq }}</el-button>
                    <el-button type="primary" round size="small" :icon="Bottom" title="down" :disabled="scope.$index >= tableDataList.length - 1" @click="handleSequenceDown(scope.$index, scope.row)"></el-button>
                </template>
            </el-table-column>
            <el-table-column prop="status" label="STATUS" width="100">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <el-button v-if="scope.row.status == 'SUCCESS'" type="success" round size="small">{{ scope.row.status }}</el-button>
                        <el-button v-else-if="scope.row.status == 'FAILED'" type="danger" round size="small">{{ scope.row.status }}</el-button>
                        <el-button v-else-if="scope.row.status == 'QUEUING'" type="warning" round size="small">{{ scope.row.status }}</el-button>
                        <el-button v-else-if="scope.row.status == 'CANCELED'" type="warning" round size="small">{{ scope.row.status }}</el-button>
                        <el-button v-else type="primary" round size="small">{{ scope.row.status }}</el-button>
                    </div>
                </template>
            </el-table-column>
            <el-table-column fixed="right" label="Operations" width="120">
                <template #default="scope">
                    <el-button type="primary" :icon="CopyDocument" circle size="small" title="Copy" @click="handleCopy(scope.row.id)"/>
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
import { useRoute } from 'vue-router'
import JsonViewer from 'vue-json-viewer'
import { Plus, Edit, Delete, CopyDocument, Bottom, Top } from '@element-plus/icons-vue'
import TableEdit from './Edit.vue'
const route = useRoute()
const $axios = inject('$axios')
const caseId = ref(route.query.caseId)

const tableDataList = ref([])

const data = ref({
    visiable: false,
    id: null
})

function loadTableData(args) {
    args = args ? args : {}
    args.caseId = caseId.value
    $axios.get('/step-definition/list', {params: args}).then((response) => {
        tableDataList.value = response.data
    })
}
function handleCopy(id) {
    $axios.post('/step-definition/copy/' + id).then((response) => {
        loadTableData()
    })
}
function handleSequenceUp(index, row) {
    $axios.post('/step-definition/sequenceUp/' + row.id).then((response) => {
        loadTableData()
    })
}
function handleSequenceDown(index, row) {
    $axios.post('/step-definition/sequenceDown/' + row.id).then((response) => {
        loadTableData()
    })
}
function handleDelete(index, row) {
    $axios.delete('/step-definition/' + row.id).then((response) => {
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
    loadTableData()
})

</script>

<style scoped>

</style>
