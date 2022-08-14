<template>
    <el-dialog v-model="data.visiable" @open="onOpenDialog" :title="data.id ? 'Edit' : 'Add'">
        <el-form ref="formRef" :model="form.entity" :rules="rules">
            <el-form-item label="ID" prop="id" :label-width="formLabelWidth" v-if="data.id">
                <el-input v-model="form.entity.id" disabled autocomplete="off" />
            </el-form-item>
            <el-form-item label="STEP_TYPE" prop="stepType" :label-width="formLabelWidth">
                <el-select v-model="form.entity.stepType" clearable filterable style="width: 100%;" @change="setFormItemVisiable">
                    <el-option v-for="item in stepTypeOptions" :key="item.key" :label="'【' + item.key+ '】' + item.name" :value="item.key"/>
                </el-select>
            </el-form-item>
            <el-form-item label="EXPRESSION" prop="expression" :label-width="formLabelWidth" v-if="formItemVisiable.expressionShow">
                <el-input v-model="form.entity.expression" clearable autocomplete="off" placeholder="sql or json path grammar"/>
            </el-form-item>
            <el-form-item label="DATASOURCE_ID" prop="datasourceId" :label-width="formLabelWidth" v-if="formItemVisiable.datasourceIdShow">
                <el-col :span="10">
                    <el-input v-model="form.entity.datasourceId" clearable autocomplete="off"/>
                </el-col>
                <el-col :span="2" style="text-align: right;">Name:</el-col>
                <el-col :span="6"><el-input disabled/></el-col>
                <el-col :span="2" style="text-align: right;">Type:</el-col>
                <el-col :span="4"><el-input disabled/></el-col>
            </el-form-item>
            <el-form-item label="API_ID" prop="apiId" :label-width="formLabelWidth" v-if="formItemVisiable.apiIdShow">
                <el-col :span="10">
                    <el-input v-model="form.entity.apiId" clearable autocomplete="off"/>
                </el-col>
                <el-col :span="2" style="text-align: right;">URL:</el-col>
                <el-col :span="12"><el-input disabled/></el-col>
            </el-form-item>
            <el-form-item label="API_HEADERS" prop="apiHeaders" :label-width="formLabelWidth" v-if="formItemVisiable.apiArgsShow">
                <el-input v-model="form.entity.apiHeaders" type="textarea" clearable autocomplete="off" placeholder='Data Format: {"token": "${token}"}'/>
            </el-form-item>
            <el-form-item label="API_BODY_ARGS" prop="apiBodyArgs" :label-width="formLabelWidth" v-if="formItemVisiable.apiArgsShow">
                <el-input v-model="form.entity.apiBodyArgs" type="textarea" clearable autocomplete="off" placeholder='Data Format: {"username": "jack", "userId": "${userId}"}'/>
            </el-form-item>
            <el-form-item label="EXPECT_VALUE" prop="expectValue" :label-width="formLabelWidth" v-if="formItemVisiable.expectValueShow">
                <el-input v-model="form.entity.expectValue" type="textarea" clearable autocomplete="off" placeholder='For Example: {"username": "Jack"} or plain text'/>
            </el-form-item>
            <el-form-item label="SEQUENCE" prop="seq" :label-width="formLabelWidth">
                <el-col :span="10">
                    <el-input-number v-model="form.entity.seq" :min="1" :max="99999"/>
                </el-col>
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
const $axios = inject('$axios')

const formLabelWidth = ref('140px')

const props = defineProps({'data': Object})
const emit = defineEmits(['closeDialogEmit', 'refreshEmit'])
const data = toRef(props, 'data')
const stepTypeOptions = ref([])

const formRef = ref(null)
const form = ref({ entity: {} })
const formItemVisiable = ref({ 
    expressionShow: false,
    datasourceIdShow: false,
    apiIdShow: false,
    apiArgsShow: false,
    expectValueShow: false,
})

const rules = reactive({
  stepType: [{ required: true },],
})

const submitForm = () => {
    formRef.value.validate((valid, fields) => {
        if (valid) {
            if(form.value.entity.id) {
                $axios.put('/step-definition', form.value.entity).then((response) => {
                    closeDialog()
                    refreshTable()
                })
            } else {
                $axios.post('/step-definition', form.value.entity).then((response) => {
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
        $axios.get('/step-definition/' + data.value.id).then((response) => {
            form.value.entity = response.data
            debugger
            setFormItemVisiable(form.value.entity.stepType)
        })
    } else {
        form.value.entity = {
            caseId: data.value.caseId,
            seq: 1
        }
    }
    initStepTypeOptions()
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
function initStepTypeOptions() {
    $axios.get('/step-definition/getStepTypes').then((response) => {
        stepTypeOptions.value = response.data
    })
}
function resetFormItemVisiable() {
    formItemVisiable.value = { 
        expressionShow: false,
        datasourceIdShow: false,
        apiIdShow: false,
        apiArgsShow: false,
        expectValueShow: false,
    }
}
function setFormItemVisiable(val) {
    resetFormItemVisiable()
    if('ACTION_CALL_API' == val) {
        formItemVisiable.value.apiIdShow = true
        formItemVisiable.value.apiArgsShow = true
    } else if('ACTION_EXECUTE_SQL' == val) {
        formItemVisiable.value.expressionShow = true
        formItemVisiable.value.datasourceIdShow = true
    } else if('ASSERT_API_RESPONSE_HTTP_CODE' == val) {
        formItemVisiable.value.expectValueShow = true
    } else if('ASSERT_API_RESPONSE_BY_TEXT' == val){
        formItemVisiable.value.expectValueShow = true
    } else if(val.indexOf('ASSERT_API_') == 0) {
        formItemVisiable.value.expressionShow = true
        formItemVisiable.value.expectValueShow = true
    } else if(val.indexOf('ASSERT_DB_') == 0) {
        formItemVisiable.value.expressionShow = true
        formItemVisiable.value.datasourceIdShow = true
        formItemVisiable.value.expectValueShow = true
    } else {
        formItemVisiable.value.expressionShow = true
        formItemVisiable.value.datasourceIdShow = true
        formItemVisiable.value.apiIdShow = true
        formItemVisiable.value.apiArgsShow = true
        formItemVisiable.value.expectValueShow = true
    }
}
</script>
