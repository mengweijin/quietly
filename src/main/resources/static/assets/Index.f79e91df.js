import{i as F,r as _,k as L,o as r,c as p,w as a,a as l,m as N,bl as j,as as z,u as i,s as Q,b as m,d as J,E as B,bu as W,bv as Z,e as q,f as M,t as H,g as P,h as V,j as U,_ as K,cZ as X,c_ as ee,p as te,q as C,c$ as le,d0 as ne,v as ae,d1 as oe,x as ie}from"./index.bb53805e.js";import{E as Y,a as O,b as G,c as se,d as re,e as ue,f as de}from"./el-overlay.73474528.js";import"./vue-json-viewer.9f9e76bd.js";const pe=m("Search"),ce=m("Reset"),me={emits:["searchEmit"],setup(A,{emit:$}){const S=F("$axios"),b=_([]),k=_(["Y","N"]),h=_(null),u=_({id:null,name:null,status:null,enabled:null}),c=()=>{h.value.resetFields()};function E(){$("searchEmit",J.trimObjectValue(u.value))}function o(){S.get("/case-definition/getCaseStepStatusEnums").then(D=>{b.value=D.data})}return L(()=>{o()}),(D,d)=>{const I=B,y=Y,g=W,x=Z,v=O,e=G;return r(),p(e,{ref_key:"searchFormRef",ref:h,model:u.value,inline:!0},{default:a(()=>[l(y,{label:"ID",prop:"id"},{default:a(()=>[l(I,{modelValue:u.value.id,"onUpdate:modelValue":d[0]||(d[0]=n=>u.value.id=n),clearable:""},null,8,["modelValue"])]),_:1}),l(y,{label:"NAME",prop:"name"},{default:a(()=>[l(I,{modelValue:u.value.name,"onUpdate:modelValue":d[1]||(d[1]=n=>u.value.name=n),clearable:""},null,8,["modelValue"])]),_:1}),l(y,{label:"STATUS",prop:"status"},{default:a(()=>[l(x,{modelValue:u.value.status,"onUpdate:modelValue":d[2]||(d[2]=n=>u.value.status=n),clearable:"",filterable:""},{default:a(()=>[(r(!0),N(z,null,j(b.value,n=>(r(),p(g,{key:n,label:n,value:n},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(y,{label:"ENABLED",prop:"enabled"},{default:a(()=>[l(x,{modelValue:u.value.enabled,"onUpdate:modelValue":d[3]||(d[3]=n=>u.value.enabled=n),clearable:"",filterable:""},{default:a(()=>[(r(!0),N(z,null,j(k.value,n=>(r(),p(g,{key:n,label:n,value:n},null,8,["label","value"]))),128))]),_:1},8,["modelValue"])]),_:1}),l(y,null,{default:a(()=>[l(v,{type:"primary",icon:i(Q),onClick:d[4]||(d[4]=n=>E())},{default:a(()=>[pe]),_:1},8,["icon"]),l(v,{type:"primary",onClick:d[5]||(d[5]=n=>c())},{default:a(()=>[ce]),_:1})]),_:1})]),_:1},8,["model"])}}},fe={class:"dialog-footer"},_e=m("Cancel"),be=m("Reset"),ye=m("Confirm"),ve={props:{data:Object},emits:["closeDialogEmit","refreshEmit"],setup(A,{emit:$}){const S=A,b=q(),{activedProjectId:k}=M(b),h=F("$axios"),u=_("200px");_([]),_([]);const c=H(S,"data"),E=_(null),o=P({entity:{id:null,name:null,description:null,projectId:null}}),D=P({name:[{required:!0,message:"Please input name",trigger:"blur"},{min:1,max:50,message:"Length should be 1 to 50",trigger:"blur"}],description:[{required:!0}]}),d=()=>{E.value.validate((v,e)=>{v?o.entity.id?h.put("/case-definition",o.entity).then(n=>{g(),x()}):h.post("/case-definition",o.entity).then(n=>{g(),x()}):console.log("error submit!",e)})};function I(){c.value.id?h.get("/case-definition/"+c.value.id).then(v=>{o.entity=v.data,o.entity.projectId=k.value}):o.entity={projectId:k.value}}function y(){E.value.resetFields()}function g(){$("closeDialogEmit")}function x(){$("refreshEmit")}return(v,e)=>{const n=B,s=Y,f=G,w=O,R=se;return r(),p(R,{modelValue:i(c).visiable,"onUpdate:modelValue":e[6]||(e[6]=t=>i(c).visiable=t),onOpen:I,title:i(c).id?"Edit":"Add"},{footer:a(()=>[V("span",fe,[l(w,{onClick:e[3]||(e[3]=t=>g())},{default:a(()=>[_e]),_:1}),i(c).id?U("",!0):(r(),p(w,{key:0,type:"primary",onClick:e[4]||(e[4]=t=>y())},{default:a(()=>[be]),_:1})),l(w,{type:"primary",onClick:e[5]||(e[5]=t=>d())},{default:a(()=>[ye]),_:1})])]),default:a(()=>[l(f,{ref_key:"formRef",ref:E,model:i(o).entity,rules:i(D)},{default:a(()=>[i(c).id?(r(),p(s,{key:0,label:"ID",prop:"id","label-width":u.value},{default:a(()=>[l(n,{modelValue:i(o).entity.id,"onUpdate:modelValue":e[0]||(e[0]=t=>i(o).entity.id=t),disabled:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"])):U("",!0),l(s,{label:"NAME",prop:"name","label-width":u.value},{default:a(()=>[l(n,{modelValue:i(o).entity.name,"onUpdate:modelValue":e[1]||(e[1]=t=>i(o).entity.name=t),clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"]),l(s,{label:"DESCRIPTION",prop:"description","label-width":u.value},{default:a(()=>[l(n,{modelValue:i(o).entity.description,"onUpdate:modelValue":e[2]||(e[2]=t=>i(o).entity.description=t),type:"textarea",clearable:"",autocomplete:"off"},null,8,["modelValue"])]),_:1},8,["label-width"])]),_:1},8,["model","rules"])]),_:1},8,["modelValue","title"])}}};const Ee={class:"flex",style:{margin:"10px 10px"}},Ce=m("\u6DFB\u52A0"),he={style:{display:"flex","align-items":"center"}},ge={style:{display:"flex","align-items":"center"}},we={setup(A){const $=q(),{activedProjectId:S}=M($),b=F("$axios"),k=X(),h=ee(),u=_(h.query.caseType);debugger;const c=_({visiable:!1,id:null}),E=_([]);function o(e){e=e||{},e.projectId=S.value,e.caseType=u.value,b.get("/case-definition/page",{params:e}).then(n=>{E.value=n.data.records})}function D(e){let n=e.enabled=="Y"?"N":"Y";b.post("/case-definition/changeEnabled/"+e.id+"?enabled="+n).then(s=>{e.enabled=n})}function d(e){k.push({path:"/quietly/step-definition",query:{caseId:e.id,name:e.name}})}function I(e){b.get("/case-definition/runCase/"+e.id).then(n=>{e.status="QUEUING"})}function y(e){c.value.id=e||null,v(!0)}function g(e){b.post("/case-definition/copy/"+e).then(n=>{o()})}function x(e,n){b.delete("/case-definition/"+n.id).then(s=>{E.value.splice(e,1)})}function v(e){c.value.visiable=e}return L(()=>{o()}),(e,n)=>{const s=O,f=re,w=ue,R=de;return r(),N("div",null,[l(me,{onSearchEmit:o}),V("div",Ee,[l(s,{type:"primary",icon:i(te),onClick:n[0]||(n[0]=t=>y())},{default:a(()=>[Ce]),_:1},8,["icon"])]),l(R,{data:E.value,stripe:"","highlight-current-row":"",height:"calc(100vh - 60px - 40px - 150px)"},{default:a(()=>[l(f,{type:"expand"},{default:a(t=>[V("div",null,[V("p",null,"PROJECT_ID: "+C(t.row.projectId),1),V("p",null,"DESCRIPTION: "+C(t.row.description),1)])]),_:1}),l(f,{prop:"id",label:"ID",width:"180"}),l(f,{prop:"name",label:"NAME"}),l(f,{prop:"caseType",label:"CASE_TYPE",width:"120"}),l(f,{prop:"status",label:"STATUS",width:"130"},{default:a(t=>[V("div",he,[t.row.status=="SUCCESS"?(r(),p(s,{key:0,type:"success",round:"",size:"small"},{default:a(()=>[m(C(t.row.status),1)]),_:2},1024)):t.row.status=="FAILED"?(r(),p(s,{key:1,type:"danger",round:"",size:"small"},{default:a(()=>[m(C(t.row.status),1)]),_:2},1024)):t.row.status=="QUEUING"?(r(),p(s,{key:2,type:"warning",round:"",size:"small"},{default:a(()=>[m(C(t.row.status),1)]),_:2},1024)):t.row.status=="CANCELED"?(r(),p(s,{key:3,type:"warning",round:"",size:"small"},{default:a(()=>[m(C(t.row.status),1)]),_:2},1024)):(r(),p(s,{key:4,type:"primary",round:"",size:"small"},{default:a(()=>[m(C(t.row.status),1)]),_:2},1024))])]),_:1}),l(f,{prop:"enabled",label:"ENABLED",width:"130"},{default:a(t=>[V("div",ge,[l(w,{title:"Are you sure to enable or disable this one?",onConfirm:T=>D(t.row)},{reference:a(()=>[t.row.enabled=="Y"?(r(),p(s,{key:0,type:"success",round:"",size:"small",title:"switch to enable or disable"},{default:a(()=>[m(C(t.row.enabled),1)]),_:2},1024)):(r(),p(s,{key:1,type:"danger",round:"",size:"small",title:"Set as default"},{default:a(()=>[m(C(t.row.enabled),1)]),_:2},1024))]),_:2},1032,["onConfirm"])])]),_:1}),l(f,{prop:"createTime",label:"CREATE_TIME",width:"180"}),l(f,{prop:"updateTime",label:"UPDATE_TIME",width:"180"}),l(f,{fixed:"right",label:"Operations",width:"240"},{default:a(t=>[l(s,{type:"primary",icon:i(le),circle:"",size:"small",title:"Step setting",onClick:T=>d(t.row)},null,8,["icon","onClick"]),t.row.enabled=="Y"&&(t.row.status=="CREATED"||t.row.status=="SUCCESS"||t.row.status=="FAILED"||t.row.status=="CANCELED")?(r(),p(w,{key:0,title:"Are you sure to run this test case?",onConfirm:T=>I(t.row)},{reference:a(()=>[l(s,{type:"primary",icon:i(ne),circle:"",size:"small",title:"Run Case"},null,8,["icon"])]),_:2},1032,["onConfirm"])):U("",!0),l(s,{type:"primary",icon:i(ae),circle:"",size:"small",title:"Edit",onClick:T=>y(t.row.id)},null,8,["icon","onClick"]),l(w,{title:"Are you sure to copy from this?",onConfirm:T=>g(t.row.id)},{reference:a(()=>[l(s,{type:"primary",icon:i(oe),circle:"",size:"small",title:"Copy"},null,8,["icon"])]),_:2},1032,["onConfirm"]),l(w,{title:"Are you sure to delete this?",onConfirm:T=>x(t.$index,t.row)},{reference:a(()=>[l(s,{type:"danger",icon:i(ie),circle:"",size:"small",title:"Delete"},null,8,["icon"])]),_:2},1032,["onConfirm"])]),_:1})]),_:1},8,["data"]),l(ve,{data:c.value,onCloseDialogEmit:n[1]||(n[1]=t=>v(!1)),onRefreshEmit:n[2]||(n[2]=t=>o())},null,8,["data"])])}}};var ke=K(we,[["__scopeId","data-v-5c2a115d"]]);export{ke as default};