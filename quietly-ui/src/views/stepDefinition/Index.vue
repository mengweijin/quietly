<template>
    <div>
        <el-breadcrumb separator="|" style="margin-bottom: 20px;">
            <el-breadcrumb-item>测试用例 Step 详情</el-breadcrumb-item>
            <el-breadcrumb-item>测试用例 ID：{{ route.query.caseId }}</el-breadcrumb-item>
            <el-breadcrumb-item>测试用例名称：{{ route.query.name }}</el-breadcrumb-item>
        </el-breadcrumb>
        <el-breadcrumb separator="|" style="margin-bottom: 20px;">
            <el-breadcrumb-item><span style="color: red;">提示：单元格文字太多被隐藏，想复制文本？鼠标快速三次单机单元格，然后 Ctrl + C 即可复制。</span></el-breadcrumb-item>
        </el-breadcrumb>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="tableDataList" stripe highlight-current-row height="calc(100vh - 60px - 40px - 145px)">
            <el-table-column type="expand">
                <template #default="props">
                    <el-table :data="[tableDataList[props.$index]]" stripe>
                        <el-table-column prop="id" label="ID" width="180"></el-table-column>
                        <el-table-column prop="caseId" label="CASE_ID" width="180"></el-table-column>
                        <el-table-column prop="apiId" label="API_ID" width="180">
                            <template #default="scope">
                                <a href="#" title="Detail" v-if="scope.row.apiId" @click="openApiDetailDialog(scope.row.apiId)">{{scope.row.apiId}}</a>
                            </template>
                        </el-table-column>
                        <el-table-column prop="datasourceId" label="DATASOURCE_ID" width="180"></el-table-column>
                        <el-table-column prop="createTime" label="CREATE_TIME"></el-table-column>
                    </el-table>
                    <div>
                        <p><span style="font-weight: bold;">API_ARGS: </span>{{ props.row.apiArgs }}</p>
                        <p><span style="font-weight: bold;">API_REQUEST_ACTUAL_INFO:</span> {{ props.row.apiRequestActualInfo }}</p>
                        <p><span style="font-weight: bold;">EXPECT_VALUE:</span> {{ props.row.expectValue }}</p>
                        <p><span style="font-weight: bold;">ACTUAL_VALUE:</span> {{ props.row.actualValue }}</p>
                        <p><span style="font-weight: bold;">ERROR_INFO:</span> {{ props.row.errorInfo }}</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="stepType" label="STEP_TYPE" width="320"/>
            <el-table-column prop="expression" label="EXPRESSION" show-overflow-tooltip/>
            <el-table-column prop="expectValue" label="EXPECT_VALUE" show-overflow-tooltip/>
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
            <el-table-column prop="updateTime" label="UPDATE_TIME" width="180"></el-table-column>
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
        <ApiDetail :data="apiDetailData"></ApiDetail>
    </div>
</template>

<script setup>
import { ref, reactive, provide, inject, readonly } from "vue"
import { useRoute } from 'vue-router'
import JsonViewer from 'vue-json-viewer'
import { Plus, Edit, Delete, CopyDocument, Bottom, Top } from '@element-plus/icons-vue'
import TableEdit from './Edit.vue'
import ApiDetail from '@/views/apiDefinition/Detail.vue'
const route = useRoute()
const $axios = inject('$axios')
const caseId = ref(route.query.caseId)

const tableDataList = ref([])

const data = ref({
    visiable: false,
    id: null
})

const apiDetailData = ref({ visiable: false, id: null })

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
function openApiDetailDialog(apiId) {
    apiDetailData.value.id = apiId
    apiDetailData.value.visiable = true
}
onMounted(() => {
    loadTableData()
})

</script>

<style scoped>

</style>
