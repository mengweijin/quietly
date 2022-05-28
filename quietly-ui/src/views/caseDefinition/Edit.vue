<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form.entity" :rules="rules">
        <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
            <el-input v-model="form.entity.id" disabled autocomplete="off" />
        </el-form-item>
        <el-form-item label="NAME" prop="name" :label-width="formLabelWidth">
            <el-input v-model="form.entity.name" clearable autocomplete="off" />
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
        description: null,
        projectId: null
    }
})

const rules = reactive({
    name: [
        { required: true, message: 'Please input name', trigger: 'blur' },
        { min: 1, max: 50, message: 'Length should be 1 to 50', trigger: 'blur' },
    ],
    description: [{required: true}]
})

const submitForm = () => {
    formRef.value.validate((valid, fields) => {
        if (valid) {
            if(form.entity.id) {
                $axios.put('/case-definition', form.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/case-definition', form.entity).then((response) => {
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
        $axios.get('/case-definition/' + data.value.id).then((response) => {
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
