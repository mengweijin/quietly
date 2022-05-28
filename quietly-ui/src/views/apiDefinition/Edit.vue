<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form.entity" :rules="rules">
        <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
            <el-input v-model="form.entity.id" disabled autocomplete="off" />
        </el-form-item>
        <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.entity.name" clearable autocomplete="off" />
        </el-form-item>
        <el-form-item label="HTTP_METHOD" prop="httpMethod" :label-width="formLabelWidth">
            <el-select v-model="form.entity.httpMethod" clearable filterable>
                <el-option v-for="item in httpMethodOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item> 
        <el-form-item label="API_URL" prop="url" :label-width="formLabelWidth">
            <el-input v-model="form.entity.url" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="REQUEST_MEDIA_TYPE" prop="requestMediaType" :label-width="formLabelWidth">
            <el-select v-model="form.entity.requestMediaType" clearable filterable>
                <el-option v-for="item in mediaTypeOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item>
        <el-form-item label="HEADERS_SAMPLE" prop="headersSample" :label-width="formLabelWidth">
            <el-input v-model="form.entity.headersSample" type="textarea" clearable autocomplete="off" placeholder='For Example: {"token": "${token}"}'/>
        </el-form-item>
        <el-form-item label="BODY_ARGS_SAMPLE" prop="bodyArgsSample" :label-width="formLabelWidth">
            <el-input v-model="form.entity.bodyArgsSample" type="textarea" clearable autocomplete="off" placeholder='For Example: {"id": 100, "name": "Jack"}'/>
        </el-form-item>
        <el-form-item label="DESCRIPTION" prop="description" :label-width="formLabelWidth">
            <el-input v-model="form.entity.description" type="textarea" clearable autocomplete="off"/>
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

const formLabelWidth = ref('200px')
const props = defineProps({'data': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])
const httpMethodOptions = ref([])
const mediaTypeOptions = ref([])
const data = toRef(props, 'data')
const formRef = ref(null)
const form = reactive({
    entity: {
        id: null,
        name: null,
        httpMethod: null,
        url: null,
        requestMediaType: null,
        headersSample: null,
        bodyArgsSample: null,
        description: null,
        projectId: null
    }
})

const rules = reactive({
    name: [
        { required: true, message: 'Please input name', trigger: 'blur' },
        { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
    ],
    httpMethod: [{required: true }],
    url: [{required: true }],
    requestMediaType: [{required: true }]
})

const submitForm = () => {
    formRef.value.validate((valid, fields) => {
        if (valid) {
            if(form.entity.id) {
                $axios.put('/api-definition', form.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/api-definition', form.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            }
        } else {
            console.log('error submit!', fields)
        }
    })
}

function initHttpMethodOptions() {
    $axios.get('/api-definition/getHttpMethods').then((response) => {
        httpMethodOptions.value = response.data
    })
}

function initMediaTypeOptions() {
    $axios.get('/api-definition/getMediaTypes').then((response) => {
        mediaTypeOptions.value = response.data
    })
}

function onOpenDialog() {
    initHttpMethodOptions()
    initMediaTypeOptions()
    if(data.value.id) {
        $axios.get('/api-definition/' + data.value.id).then((response) => {
            form.entity = response.data
            form.entity.projectId = activedProjectId.value
        })
    } else {
        form.entity = {
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
