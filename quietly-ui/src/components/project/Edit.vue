<template>
    <el-dialog v-model="editProjectDialogVisible" @close="closeDialog" title="Add/Edit Project">
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
            <el-button @click="closeDialog()">Cancel</el-button>
            <el-button type="primary" @click="resetForm()">Reset</el-button>
            <el-button type="primary" @click="submitForm()">Confirm</el-button>
        </span>
        </template>
    </el-dialog>
</template>

<script setup>
import { reactive, ref, getCurrentInstance } from 'vue'

const { ctx } = getCurrentInstance()
const $axios = inject('$axios')


/**
 * defineProps()接收父组件传递来的数据
 * defineEmits()抛出父组件将响应的方法
 */
const props = defineProps(['editProjectDialogVisible'])
const emit = defineEmits(['dialogEmit'])

const dialogEmit = (isVisible) => {
    emit('dialogEmit', isVisible)
}

const formLabelWidth = '140px'

const formRef = ref(null)
const form = reactive({
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
        if(form.id) {
            $axios.put('/project').then((response) => {
                closeDialog()
            })
        } else {
            $axios.post('/project').then((response) => {
                closeDialog()
            })
        }
    } else {
      console.log('error submit!', fields)
    }
  })
}

const resetForm = () => { 
    formRef.value.resetFields() 
}

const closeDialog = () => {
    dialogEmit(false)
}
</script>
