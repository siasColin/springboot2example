$(function(){
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(save)', function(data){
            var ids = $("#ids").val();
            if(!$.isEmpty(ids)){
                Common.openConfirm("是否确定重置?",function () {
                    Common.ajax('userManage/resetPwd',$("#form1").serialize(),true,'PUT',userSaveSuccess);
                });
            }
            return false;
        });
    });
});
function userSaveSuccess(data){
    parent.userSaveOrUpdateSuccess();
}