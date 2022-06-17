import{r as g,o as C,c as w,w as t,a as e,u as d,s as S,b as x,d as L,E as j,i as F,e as I,f as A,t as P,g as q,h,j as U,_ as z,k as W,l as G,m as H,p as J,n as K,q as R,v as Q,x as X}from"./index.bb53805e.js";import{E as B,a as T,b as N,c as Y,d as Z,e as ee,f as te}from"./el-overlay.73474528.js";const le=x("Search"),oe=x("Reset"),ae={emits:["searchEmit"],setup(k,{emit:f}){const b=g(null),r=g({id:null,name:null}),v=()=>{b.value.resetFields()};function c(){f("searchEmit",L.trimObjectValue(r.value))}return(m,n)=>{const l=j,s=B,i=T,_=N;return C(),w(_,{ref_key:"searchFormRef",ref:b,model:r.value,inline:!0},{default:t(()=>[e(s,{label:"ID",prop:"id"},{default:t(()=>[e(l,{modelValue:r.value.id,"onUpdate:modelValue":n[0]||(n[0]=u=>r.value.id=u),clearable:""},null,8,["modelValue"])]),_:1}),e(s,{label:"NAME",prop:"name"},{default:t(()=>[e(l,{modelValue:r.value.name,"onUpdate:modelValue":n[1]||(n[1]=u=>r.value.name=u),clearable:""},null,8,["modelValue"])]),_:1}),e(s,null,{default:t(()=>[e(i,{type:"primary",icon:d(S),onClick:n[2]||(n[2]=u=>c())},{default:t(()=>[le]),_:1},8,["icon"]),e(i,{type:"primary",onClick:n[3]||(n[3]=u=>v())},{default:t(()=>[oe]),_:1})]),_:1})]),_:1},8,["model"])}}},ne={class:"dialog-footer"},se=x("Cancel"),ie=x("Reset"),re=x("Confirm"),ue={props:{data:Object},emits:["closeDialogEmit","refreshEmit"],setup(k,{emit:f}){const b=k,r=F("$axios"),v=I();A(v);const c=g("140px"),m=P(b,"data"),n=g(null),l=g({entity:{id:null,name:null,baseUrl:null}}),s=q({name:[{required:!0,message:"Please input name",trigger:"blur"},{min:1,max:50,message:"Length should be 1 to 50",trigger:"blur"}]}),i=()=>{n.value.validate((E,o)=>{E?l.value.entity.id?r.put("/project",l.value.entity).then(a=>{y(),V()}):r.post("/project",l.value.entity).then(a=>{y(),V()}):console.log("error submit!",o)})};function _(){m.value.id?r.get("/project/"+m.value.id).then(E=>{l.value.entity=E.data}):l.value.entity={}}function u(){n.value.resetFields()}function y(){f("closeDialogEmit")}function V(){f("refreshEmit")}return(E,o)=>{const a=j,$=B,M=N,D=T,O=Y;return C(),w(O,{modelValue:d(m).visiable,"onUpdate:modelValue":o[6]||(o[6]=p=>d(m).visiable=p),onOpen:_,title:d(m).id?"Edit":"Add"},{footer:t(()=>[h("span",ne,[e(D,{onClick:o[3]||(o[3]=p=>y())},{default:t(()=>[se]),_:1}),d(m).id?U("",!0):(C(),w(D,{key:0,type:"primary",onClick:o[4]||(o[4]=p=>u())},{default:t(()=>[ie]),_:1})),e(D,{type:"primary",onClick:o[5]||(o[5]=p=>i())},{default:t(()=>[re]),_:1})])]),default:t(()=>[e(M,{ref_key:"formRef",ref:n,model:l.value.entity,rules:d(s)},{default:t(()=>[d(m).id?(C(),w($,{key:0,label:"ID",prop:"id","label-width":c.value},{default:t(()=>[e(a,{modelValue:l.value.entity.id,"onUpdate:modelValue":o[0]||(o[0]=p=>l.value.entity.id=p),disabled:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"])):U("",!0),e($,{label:"NAME",prop:"name","label-width":c.value},{default:t(()=>[e(a,{modelValue:l.value.entity.name,"onUpdate:modelValue":o[1]||(o[1]=p=>l.value.entity.name=p),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),e($,{label:"BASE_URL",prop:"baseUrl","label-width":c.value},{default:t(()=>[e(a,{modelValue:l.value.entity.baseUrl,"onUpdate:modelValue":o[2]||(o[2]=p=>l.value.entity.baseUrl=p),clearable:"",autocomplete:"off",placeholder:"For Example: http://localhost:8080/"},null,8,["modelValue"])]),_:1},8,["label-width"])]),_:1},8,["model","rules"])]),_:1},8,["modelValue","title"])}}};const de={class:"flex",style:{margin:"10px 10px"}},me=x("\u6DFB\u52A0"),pe={style:{display:"flex","align-items":"center"}},ce={style:{"margin-left":"10px"}},_e={style:{display:"flex","align-items":"center"}},fe={style:{"margin-left":"10px"}},ve={setup(k){const f=F("$axios"),b=I(),{projectDataList:r}=A(b),v=g({visiable:!1,id:null});function c(s){f.get("/project/list",{params:s}).then(i=>{r.value=i.data})}function m(s,i){f.delete("/project/"+i.id).then(_=>{r.value.splice(s,1)})}function n(s){v.value.id=s||null,l(!0)}function l(s){v.value.visiable=s}return W(()=>{}),(s,i)=>{const _=T,u=Z,y=K("timer"),V=G,E=ee,o=te;return C(),H("div",null,[e(ae,{onSearchEmit:c}),h("div",de,[e(_,{type:"primary",icon:d(J),onClick:i[0]||(i[0]=a=>n())},{default:t(()=>[me]),_:1},8,["icon"])]),e(o,{data:d(r),stripe:"","highlight-current-row":"",height:"calc(100vh - 60px - 40px - 145px)"},{default:t(()=>[e(u,{prop:"id",label:"ID",width:"180"}),e(u,{prop:"name",label:"NAME"}),e(u,{prop:"baseUrl",label:"BASE_URL"}),e(u,{prop:"createTime",label:"CREATE_TIME",width:"180"},{default:t(a=>[h("div",pe,[e(V,null,{default:t(()=>[e(y)]),_:1}),h("span",ce,R(a.row.createTime),1)])]),_:1}),e(u,{prop:"updateTime",label:"UPDATE_TIME",width:"180"},{default:t(a=>[h("div",_e,[e(V,null,{default:t(()=>[e(y)]),_:1}),h("span",fe,R(a.row.updateTime),1)])]),_:1}),e(u,{fixed:"right",label:"Operations",width:"200"},{default:t(a=>[e(_,{type:"primary",icon:d(Q),circle:"",size:"small",title:"Edit",onClick:$=>n(a.row.id)},null,8,["icon","onClick"]),e(E,{title:"Are you sure to delete this?",onConfirm:$=>m(a.$index,a.row)},{reference:t(()=>[e(_,{type:"danger",icon:d(X),circle:"",size:"small",title:"Delete"},null,8,["icon"])]),_:2},1032,["onConfirm"])]),_:1})]),_:1},8,["data"]),e(ue,{data:v.value,onCloseDialogEmit:i[1]||(i[1]=a=>l(!1)),onRefreshEmit:i[2]||(i[2]=a=>c())},null,8,["data"])])}}};var Ee=z(ve,[["__scopeId","data-v-92e26c68"]]);export{Ee as default};
