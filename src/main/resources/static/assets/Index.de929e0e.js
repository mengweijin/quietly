import{i as S,r as E,k as P,o as c,c as v,w as t,a as l,m as O,bl as B,as as N,u as o,s as X,b as D,d as Z,E as L,bu as M,bv as q,e as Y,f as z,t as ee,g as F,h as T,j as R,_ as le,p as te,q as I,v as ae}from"./index.bb53805e.js";import{E as J,a as j,b as W,c as oe,d as ne,e as se,f as ue}from"./el-overlay.73474528.js";const de=D("Search"),ie=D("Reset"),re={emits:["searchEmit"],setup(A,{emit:g}){const h=S("$axios"),V=E([]),_=E(null),u=E({id:null,name:null,dbType:null}),i=()=>{_.value.resetFields()};function x(){g("searchEmit",Z.trimObjectValue(u.value))}function m(){h.get("/datasource/getDbTypes").then(b=>{V.value=b.data})}return P(()=>{m()}),(b,e)=>{const r=L,f=J,p=M,C=q,$=j,s=W;return c(),v(s,{ref_key:"searchFormRef",ref:_,model:u.value,inline:!0},{default:t(()=>[l(f,{label:"ID",prop:"id"},{default:t(()=>[l(r,{modelValue:u.value.id,"onUpdate:modelValue":e[0]||(e[0]=d=>u.value.id=d),clearable:""},null,8,["modelValue"])]),_:1}),l(f,{label:"NAME",prop:"name"},{default:t(()=>[l(r,{modelValue:u.value.name,"onUpdate:modelValue":e[1]||(e[1]=d=>u.value.name=d),clearable:""},null,8,["modelValue"])]),_:1}),l(f,{label:"DB_TYPE",prop:"dbType"},{default:t(()=>[l(C,{modelValue:u.value.dbType,"onUpdate:modelValue":e[2]||(e[2]=d=>u.value.dbType=d),clearable:"",filterable:""},{default:t(()=>[(c(!0),O(N,null,B(V.value,d=>(c(),v(p,{key:d,label:d,value:d},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(f,null,{default:t(()=>[l($,{type:"primary",icon:o(X),onClick:e[3]||(e[3]=d=>x())},{default:t(()=>[de]),_:1},8,["icon"]),l($,{type:"primary",onClick:e[4]||(e[4]=d=>i())},{default:t(()=>[ie]),_:1})]),_:1})]),_:1},8,["model"])}}},pe={class:"dialog-footer"},me=D("Cancel"),fe=D("Reset"),ce=D("Confirm"),_e={props:{data:Object},emits:["closeDialogEmit","refreshEmit"],setup(A,{emit:g}){const h=A,V=Y(),{activedProjectId:_}=z(V),u=S("$axios"),i=E("140px"),x=E([]),m=ee(h,"data"),b=E(null),e=F({entity:{id:null,name:null,dbType:null,url:null,username:null,password:null,asDefault:null,projectId:null}}),r=F({name:[{required:!0,message:"Please input name",trigger:"blur"},{min:1,max:50,message:"Length should be 1 to 50",trigger:"blur"}],dbType:[{required:!0}],url:[{required:!0}],username:[{required:!0}]}),f=()=>{b.value.validate((k,a)=>{k?e.entity.id?u.put("/datasource",e.entity).then(y=>{s(),d()}):u.post("/datasource",e.entity).then(y=>{s(),d()}):console.log("error submit!",a)})};function p(){u.get("/datasource/getDbTypes").then(k=>{x.value=k.data})}function C(){p(),m.value.id?u.get("/datasource/"+m.value.id).then(k=>{e.entity=k.data,e.entity.projectId=_.value}):e.entity={asDefault:"N",projectId:_.value}}function $(){b.value.resetFields()}function s(){g("closeDialogEmit")}function d(){g("refreshEmit")}return(k,a)=>{const y=L,w=J,G=M,H=q,K=W,U=j,Q=oe;return c(),v(Q,{modelValue:o(m).visiable,"onUpdate:modelValue":a[10]||(a[10]=n=>o(m).visiable=n),onOpen:C,title:o(m).id?"Edit":"Add"},{footer:t(()=>[T("span",pe,[l(U,{onClick:a[7]||(a[7]=n=>s())},{default:t(()=>[me]),_:1}),o(m).id?R("",!0):(c(),v(U,{key:0,type:"primary",onClick:a[8]||(a[8]=n=>$())},{default:t(()=>[fe]),_:1})),l(U,{type:"primary",onClick:a[9]||(a[9]=n=>f())},{default:t(()=>[ce]),_:1})])]),default:t(()=>[l(K,{ref_key:"formRef",ref:b,model:o(e).entity,rules:o(r)},{default:t(()=>[o(m).id?(c(),v(w,{key:0,label:"ID",prop:"id","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.id,"onUpdate:modelValue":a[0]||(a[0]=n=>o(e).entity.id=n),disabled:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"])):R("",!0),l(w,{label:"NAME",prop:"name","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.name,"onUpdate:modelValue":a[1]||(a[1]=n=>o(e).entity.name=n),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),l(w,{label:"DB_TYPE",prop:"dbType","label-width":i.value},{default:t(()=>[l(H,{modelValue:o(e).entity.dbType,"onUpdate:modelValue":a[2]||(a[2]=n=>o(e).entity.dbType=n),clearable:"",filterable:""},{default:t(()=>[(c(!0),O(N,null,B(x.value,n=>(c(),v(G,{key:n,label:n,value:n},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1},8,["label-width"]),l(w,{label:"JDBC_URL",prop:"url","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.url,"onUpdate:modelValue":a[3]||(a[3]=n=>o(e).entity.url=n),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),l(w,{label:"USERNAME",prop:"username","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.username,"onUpdate:modelValue":a[4]||(a[4]=n=>o(e).entity.username=n),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),l(w,{label:"PASSWORD",prop:"password","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.password,"onUpdate:modelValue":a[5]||(a[5]=n=>o(e).entity.password=n),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),l(w,{label:"AS_DEFAULT",prop:"asDefault","label-width":i.value},{default:t(()=>[l(y,{modelValue:o(e).entity.asDefault,"onUpdate:modelValue":a[6]||(a[6]=n=>o(e).entity.asDefault=n),clearable:"",autocomplete:"off",disabled:""},null,8,["modelValue"])]),_:1},8,["label-width"])]),_:1},8,["model","rules"])]),_:1},8,["modelValue","title"])}}};const be={class:"flex",style:{margin:"10px 10px"}},ye=D("\u6DFB\u52A0"),ve={style:{display:"flex","align-items":"center"}},Ee={setup(A){const g=Y(),{activedProjectId:h}=z(g),V=S("$axios"),_=E({visiable:!1,id:null}),u=E([]);function i(e){e||(e={}),e.projectId=h.value,V.get("/datasource/list",{params:e}).then(r=>{u.value=r.data})}function x(e){V.post("/datasource/setAsDefault/"+e+"?projectId="+h.value).then(r=>{i()})}function m(e){_.value.id=e||null,b(!0)}function b(e){_.value.visiable=e}return P(()=>{i()}),(e,r)=>{const f=j,p=ne,C=se,$=ue;return c(),O("div",null,[l(re,{onSearchEmit:i}),T("div",be,[l(f,{type:"primary",icon:o(te),onClick:r[0]||(r[0]=s=>m())},{default:t(()=>[ye]),_:1},8,["icon"])]),l($,{data:u.value,stripe:"","highlight-current-row":"",height:"calc(100vh - 60px - 40px - 150px)"},{default:t(()=>[l(p,{type:"expand"},{default:t(s=>[T("div",null,[T("p",null,"PROJECT_ID: "+I(s.row.projectId),1),T("p",null,"CREATE_TIME: "+I(s.row.createTime),1),T("p",null,"UPDATE_TIME: "+I(s.row.updateTime),1)])]),_:1}),l(p,{prop:"id",label:"ID",width:"180"}),l(p,{prop:"name",label:"NAME",width:"200"}),l(p,{prop:"dbType",label:"DB_TYPE",width:"100"}),l(p,{prop:"url",label:"JDBC_URL"}),l(p,{prop:"username",label:"USERNAME",width:"120"}),l(p,{prop:"password",label:"PASSWORD",width:"150"}),l(p,{prop:"asDefault",label:"AS_DEFAULT",width:"150"},{default:t(s=>[T("div",ve,[l(C,{title:"Are you sure to set this as default datasource?",onConfirm:d=>x(s.row.id)},{reference:t(()=>[s.row.asDefault=="Y"?(c(),v(f,{key:0,type:"success",round:"",size:"small",title:"Set as default",disabled:""},{default:t(()=>[D(I(s.row.asDefault),1)]),_:2},1024)):(c(),v(f,{key:1,type:"danger",round:"",size:"small",title:"Set as default"},{default:t(()=>[D(I(s.row.asDefault),1)]),_:2},1024))]),_:2},1032,["onConfirm"])])]),_:1}),l(p,{fixed:"right",label:"Operations",width:"120"},{default:t(s=>[l(f,{type:"primary",icon:o(ae),circle:"",size:"small",title:"Edit",onClick:d=>m(s.row.id)},null,8,["icon","onClick"]),R("",!0)]),_:1})]),_:1},8,["data"]),l(_e,{data:_.value,onCloseDialogEmit:r[1]||(r[1]=s=>b(!1)),onRefreshEmit:r[2]||(r[2]=s=>i())},null,8,["data"])])}}};var we=le(Ee,[["__scopeId","data-v-741bec4e"]]);export{we as default};
