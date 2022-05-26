<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
            <el-input v-model="form.id" disabled autocomplete="off" />
        </el-form-item>
        <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.name" clearable autocomplete="off" />
        </el-form-item>
        <el-form-item label="DB_TYPE" prop="dbType" :label-width="formLabelWidth">
            <el-select v-model="form.dbType" clearable filterable>
                <el-option v-for="item in dbTypeOptions" :key="item" :label="item" :value="item"/>
            </el-select>
        </el-form-item> 
        <el-form-item label="JDBC_URL" prop="url" :label-width="formLabelWidth">
            <el-input v-model="form.url" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="USERNAME" prop="username" :label-width="formLabelWidth">
            <el-input v-model="form.username" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="PASSWORD" prop="password" :label-width="formLabelWidth">
            <el-input v-model="form.password" clearable autocomplete="off"/>
        </el-form-item>
        <el-form-item label="AS_DEFAULT" prop="asDefault" :label-width="formLabelWidth">
            <el-input v-model="form.asDefault" clearable autocomplete="off" disabled/>
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
// toRefs: 转为普通对象，解构
import { reactive, ref, toRef, toRefs, inject } from 'vue'
const $axios = inject('$axios')

const formLabelWidth = ref('140px')
/**
 * defineProps()接收父组件传递来的数据
 * defineEmits()抛出父组件将响应的方法
 */
const props = defineProps({'data': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])

const dbTypeOptions = ref([])
function initDbTypeOptions() {
    $axios.get('/datasource/getDbTypes').then((response) => {
        dbTypeOptions.value = response.data
    })
}

/**
 * 这样获取到的是值传递，非响应式的。const data = ref(props.data);
 * 响应式应该这样获取：
 * 方法1：const msg = toRef(props, 'data');
 * 方法2：const { data } = toRefs(props);
 */
const data = toRef(props, 'data')

const formRef = ref(null)
const form = ref({
    id: null,
    name: null,
    dbType: null,
    url: null,
    username: null,
    password: null,
    asDefault: null,
    projectId: null
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
            if(form.value.id) {
                $axios.put('/datasource', form.value).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/datasource', form.value).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            }
        } else {
            console.log('error submit!', fields)
        }
    })
}

function onOpenDialog() {
    initDbTypeOptions()
    if(data.value.id) {
        $axios.get('/datasource/' + data.value.id).then((response) => {
            form.value.id = response.data.id
            form.value.name = response.data.name
            form.value.dbType = response.data.dbType
            form.value.url = response.data.url
            form.value.username = response.data.username
            form.value.password = response.data.password
            form.value.asDefault = response.data.asDefault
            form.value.projectId = response.data.projectId
        })
    } else {
        form.value = {
            asDefault: 'N'
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
