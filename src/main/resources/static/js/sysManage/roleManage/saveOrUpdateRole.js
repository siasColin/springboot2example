var systemPermissionsObj;
/**
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeAreaNodeClick(treeNode){
    document.getElementById('areaCode').value=treeNode.id;
    document.getElementById('areaName').value=treeNode.name;
}
$(function(){
    $("#areaName").on("click",function(){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //
                title:'地区选择',
                scrollbar: false, //  滚动条 禁止
                // closeBtn: 0,
                area: ['265px', '350px'], //宽高
                content: Common.ctxPath+'areaManage/childsAreatree/none'
            });
        });
    });

    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(save)', function(data){
            if(data.field.id==null||data.field.id==''){
                Common.ajax('roleManage/role',$("#form1").serialize(),true,'POST',roleSaveSuccess);
            }else{
                Common.ajax('roleManage/role',$("#form1").serialize(),true,'PUT',roleSaveSuccess);
            }
            return false;
        });
    });

    systemPermissionsObj = xmSelect.render({
        el: '#systemPermissions',
        name: 'systemPermissions',
        language: 'zn',
        filterable: true,
        // layVerify: 'required',
        layVerType: 'tips',
        toolbar: {
            show: true,
            list: [ 'ALL', 'CLEAR', 'REVERSE' ]
        },
        prop: {
            name: 'operateName',
            value: 'id',
        },
        data: []
    });
    Common.ajax('operatetypeManage/operatetypeList',null,true,'GET',updateSystemPermissionsObj);
});
function updateSystemPermissionsObj(data) {
    systemPermissionsObj.update({
        data: data,
        autoRow: true,
    });
    //
    var id = $("#id").val();
    if(id != null && id != ''){//编辑页面
        Common.ajax('operatetypeManage/operatetypeList/'+id,null,true,'GET',setSystemPermissionsValue);
    }
}
function setSystemPermissionsValue(data){
    systemPermissionsObj.append(data);
}
function roleSaveSuccess(data){
    parent.roleSaveOrUpdateSuccess();
}