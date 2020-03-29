var rolesObj;
/**
 * 弹出层中的机构树点击时触发，主要用于给页面元素赋值
 * @param treeNode
 */
function iframeOrgNodeClick(treeNode){
    document.getElementById('orgCode').value=treeNode.id;
    document.getElementById('orgName').value=treeNode.name;
    Common.ajax('roleManage/roleList/'+treeNode.id,null,true,'GET',updateRolesObj);
}
$(function(){
    $("#orgName").on("click",function(){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //
                title:'机构选择',
                scrollbar: false, //  滚动条 禁止
                // closeBtn: 0,
                area: ['265px', '350px'], //宽高
                content: Common.ctxPath+'orgManage/orgtree/none'
            });
        });
    });
    rolesObj = xmSelect.render({
        el: '#roles',
        name: 'roleIds',
        language: 'zn',
        filterable: true,
        // layVerify: 'required',
        layVerType: 'tips',
        toolbar: {
            show: true,
            list: [ 'ALL', 'CLEAR', 'REVERSE' ]
        },
        prop: {
            name: 'roleName',
            value: 'id',
        },
        data: []
    });
    if(!$.isEmpty($("#id").val())){
        var orgCode = $("#orgCode").val();
        Common.ajax('roleManage/roleList/'+orgCode,null,true,'GET',updateRolesObj);
    }

    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(save)', function(data){
            if(data.field.id==null||data.field.id==''){
                Common.ajax('userManage/user',$("#form1").serialize(),true,'POST',userSaveSuccess);
            }else{
                Common.ajax('userManage/user',$("#form1").serialize(),true,'PUT',userSaveSuccess);
            }
            return false;
        });
    });
});
function updateRolesObj(data) {
    rolesObj.update({
        data: data,
        autoRow: true,
    });
    //
    var id = $("#id").val();
    if(id != null && id != ''){//编辑页面
        Common.ajax('roleManage/userRoleList/'+id,null,true,'GET',setRolesValue);
    }
}
function setRolesValue(data){
    rolesObj.append(data);
}
function userSaveSuccess(data){
    parent.userSaveOrUpdateSuccess();
}