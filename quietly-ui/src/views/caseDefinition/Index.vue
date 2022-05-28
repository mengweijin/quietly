<template>
    <div>
        <TableSearch @searchEmit="loadTableData"></TableSearch>

        <div class="flex" style="margin: 10px 10px;">
            <el-button type="primary" :icon="Plus" @click="handleAddOrEdit()">添加</el-button>
        </div>

        <el-table :data="tableDataList" stripe highlight-current-row height="calc(100vh - 60px - 40px - 150px)">
            <el-table-column type="expand">
                <template #default="props">
                    <div>
                        <p>PROJECT_ID: {{ props.row.projectId }}</p>
                        <p>DESCRIPTION: {{ props.row.description }}</p>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="id" label="ID" width="180" />
            <el-table-column prop="name" label="NAME" />
            <el-table-column prop="status" label="STATUS" width="130">
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
            <el-table-column prop="enabled" label="ENABLED" width="130">
                <template #default="scope">
                    <div style="display: flex; align-items: center">
                        <el-popconfirm title="Are you sure to enable or disable this one?" @confirm="handleChangeEnabled(scope.row)" >
                            <template #reference>
                                <el-button v-if="scope.row.enabled == 'Y'" type="success" round size="small" title="switch to enable or disable">{{ scope.row.enabled }}</el-button>
                                <el-button v-else type="danger" round size="small" title="Set as default">{{ scope.row.enabled }}</el-button>
                            </template>
                        </el-popconfirm>
                    </div>
                </template>
            </el-table-column>
            <el-table-column prop="createTime" label="CREATE_TIME" width="180" />
            <el-table-column prop="updateTime" label="UPDATE_TIME" width="180" />
            <el-table-column fixed="right" label="Operations" width="240">
                <template #default="scope">
                    <el-button type="primary" :icon="Tickets" circle size="small" title="Step detail" @click="handleStepDetail(scope.row)"/>
                    <el-popconfirm title="Are you sure to run this test case?" @confirm="handleRunCase(scope.row)">
                        <template #reference>
                            <el-button type="primary" :icon="VideoPlay" circle size="small" title="Run Case"/>
                        </template>
                    </el-popconfirm>
                    
                    <el-button type="primary" :icon="Edit" circle size="small" title="Edit" @click="handleAddOrEdit(scope.row.id)"/>
                    <el-button type="primary" :icon="CopyDocument" circle size="small" title="Copy" @click="handleCopy(scope.row.id)"/>
                    <el-popconfirm title="Are you sure to delete this?" @confirm="handleDelete(scope.$index, scope.row)">
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
import { Plus, Edit, Delete, VideoPlay, CopyDocument, Tickets } from '@element-plus/icons-vue'
import JsonViewer from 'vue-json-viewer'
import TableSearch from './Search.vue'
import TableEdit from './Edit.vue'
import Utils from '@/util/utils.js'
import { useProject } from "@/store/store.js"
 // 使普通数据变响应式的函数  
import { storeToRefs } from 'pinia'
// 实例化仓库函数
const store = useProject()
// 解构并使数据具有响应式 ref
const { activedProjectId } = storeToRefs(store)
const $axios = inject('$axios')

const data = ref({
    visiable: false,
    id: null,
})

const tableDataList = ref([])

function loadTableData(args) {
    args = args ? args : {}
    args.projectId = activedProjectId.value
    $axios.get('/case-definition/page', {params: args}).then((response) => {
        tableDataList.value = response.data.records
    })
}

function handleChangeEnabled(row) {
    let enabled = row.enabled == 'Y' ? 'N' : 'Y'
    $axios.post('/case-definition/changeEnabled/' + row.id + '?enabled=' + enabled).then((response) => {
        row.enabled = enabled
    })
}

function handleRunCase(row) {
    $axios.get('/case-definition/runCase/' + row.id).then((response) => {
        row.status = 'QUEUING'
    })
}

function handleAddOrEdit(id) {
    data.value.id = id ? id : null
    setDialogVisiable(true)
}
function handleCopy(id) {
    $axios.post('/case-definition/copy/' + id).then((response) => {
        loadTableData()
    })
}
function handleDelete(index, row) {
    $axios.delete('/case-definition/' + row.id).then((response) => {
        tableDataList.value.splice(index, 1)
    })
}

function setDialogVisiable(isVisiable) {
    data.value.visiable = isVisiable
}

onMounted(() => {
    loadTableData()
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
