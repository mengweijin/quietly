<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form.entity" :rules="rules">
        <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
            <el-input v-model="form.entity.id" disabled autocomplete="off" />
        </el-form-item>
        <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.entity.name" clearable autocomplete="off" />
        </el-form-item>
        <el-form-item label="DB_TYPE" prop="dbType" :label-width="formLabelWidth">
            <el-select v-model="form.entity.dbType" clearable filterable>
                <el-option v-for="item in dbTypeOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item> 
        <el-form-item label="JDBC_URL" prop="url" :label-width="formLabelWidth">
            <el-input v-model="form.entity.url" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="USERNAME" prop="username" :label-width="formLabelWidth">
            <el-input v-model="form.entity.username" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="PASSWORD" prop="password" :label-width="formLabelWidth">
            <el-input v-model="form.entity.password" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="AS_DEFAULT" prop="asDefault" :label-width="formLabelWidth">
            <el-input v-model="form.entity.asDefault" clearable autocomplete="off" disabled/>
        </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="closeDialog()">Cancel</el-button>
            <el-button type="primary" @click="resetForm()" v-if="!data.id">Reset</el-button>
            <el-button type="primary" @click="submitForm()">Confirm</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, ref, toRef, toRefs, inject } from 'vue'
import { useProject } from "@/store/store.js"
import { storeToRefs } from 'pinia'
const store = useProject()
const { activedProjectId } = storeToRefs(store)
const $axios = inject('$axios')

const formLabelWidth = ref('140px')
const props = defineProps({'data': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])
const dbTypeOptions = ref([])
const data = toRef(props, 'data')
const formRef = ref(null)
const form = reactive({
    entity: {
        id: null,
        name: null,
        dbType: null,
        url: null,
        username: null,
        password: null,
        asDefault: null,
        projectId: null
    }
})

const rules = reactive({
    name: [
        { required: true, message: 'Please input name', trigger: 'blur' },
        { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
    ],
    dbType: [{required: true }],
    url: [{required: true }],
    username: [{required: true }]
})

const submitForm = () => {
    formRef.value.validate((valid, fields) => {
        if (valid) {
            if(form.entity.id) {
                $axios.put('/datasource', form.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/datasource', form.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            }
        } else {
            console.log('error submit!', fields)
        }
    })
}

function initDbTypeOptions() {
    $axios.get('/datasource/getDbTypes').then((response) => {
        dbTypeOptions.value = response.data
    })
}

function onOpenDialog() {
    initDbTypeOptions()
    if(data.value.id) {
        $axios.get('/datasource/' + data.value.id).then((response) => {
            form.entity = response.data
            form.entity.projectId = activedProjectId.value
        })
    } else {
        form.entity = {
            asDefault: 'N',
            projectId: activedProjectId.value
        }
    }
}
function resetForm() {
    formRef.value.resetFields() 
}
function closeDialog() {
    emit('closeDialogEmit')
}
function refreshTable() {
    emit('refreshEmit')
}
</script>
