<template>
    <el-dialog v-model="dialogVisible" @open="onOpenDialog" @close="onCloseDialog" title="Add/Edit Project">
        <el-form ref="formRef" :model="form" :rules="rules">
        <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="form.id">
            <el-input v-model="form.id" disabled autocomplete="off" />
        </el-form-item>
        <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.name" autocomplete="off" />
        </el-form-item>
        <el-form-item label="BASE_URL" prop="baseUrl" :label-width="formLabelWidth">
            <el-input v-model="form.baseUrl" autocomplete="off" />
        </el-form-item>
        </el-form>
        <template #footer>
        <span class="dialog-footer">
            <el-button @click="onCloseDialog()">Cancel</el-button>
            <el-button type="primary" @click="resetForm()">Reset</el-button>
            <el-button type="primary" @click="submitForm()">Confirm</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, ref, toRef } from 'vue'
const $axios = inject('$axios')

/**
 * defineProps()接收父组件传递来的数据
 * defineEmits()抛出父组件将响应的方法
 */
const props = defineProps({'dialogVisible': Boolean, 'rowData': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])

const formLabelWidth = '140px'

const formRef = ref(null)
const form = ref({
    id: null,
    name: null,
    baseUrl: null,
})

const rules = reactive({
  name: [
    { required: true, message: 'Please input name', trigger: 'blur' },
    { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
  ],
})

const submitForm = () => {
    formRef.value.validate((valid, fields) => {
        if (valid) {
            if(form.value.id) {
                $axios.put('/project', form.value).then((response) => {
                    resetForm()
                    onCloseDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/project', form.value).then((response) => {
                    resetForm()
                    onCloseDialog()
                    refreshTable()
                })
            }
        } else {
        console.log('error submit!', fields)
        }
    })
}

function onOpenDialog() {
    let rowRef = toRef(props, 'rowData')
    if(rowRef && rowRef.value && rowRef.value.id) {
        form.value = rowRef.value
    }
}
function resetForm() {
    formRef.value.resetFields() 
}
function onCloseDialog() {
    emit('closeDialogEmit', false)
}
function refreshTable() {
    emit('refreshEmit')
}

</script>
