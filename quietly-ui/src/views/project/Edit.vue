<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form.entity" :rules="rules">
            <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
                <el-input v-model="form.entity.id" disabled autocomplete="off" />
            </el-form-item>
            <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
                <el-input v-model="form.entity.name" clearable autocomplete="off" />
            </el-form-item>
            <el-form-item label="BASE_URL" prop="baseUrl" :label-width="formLabelWidth">
                <el-input v-model="form.entity.baseUrl" clearable autocomplete="off" placeholder="For Example: http://localhost:8080/"/>
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
import { reactive, ref, toRef, toRefs } from 'vue'
import { useProject } from "@/store/store.js"
 // 使普通数据变响应式的函数  
import { storeToRefs } from 'pinia'
const $axios = inject('$axios')
// 实例化仓库函数
const store = useProject()
// 解构并使数据具有响应式 ref
const { projectDataList } = storeToRefs(store)

const formLabelWidth = ref('140px')
/**
 * defineProps()接收父组件传递来的数据
 * defineEmits()抛出父组件将响应的方法
 */
const props = defineProps({'data': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])

/**
 * 这样获取到的是值传递，非响应式的。const data = ref(props.data);
 * 响应式应该这样获取：
 * 方法1：const msg = toRef(props, 'data');
 * 方法2：const { data } = toRefs(props);
 */
const data = toRef(props, 'data')

const formRef = ref(null)
const form = ref({
    entity: {
        id: null,
        name: null,
        baseUrl: null,
    }
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
            if(form.value.entity.id) {
                $axios.put('/project', form.value.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/project', form.value.entity).then((response) => {
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
    if(data.value.id) {
        $axios.get('/project/' + data.value.id).then((response) => {
            form.value.entity = response.data
        })
    } else {
        form.value.entity = {}
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
