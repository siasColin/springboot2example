$(function(){
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(save)', function(data){
            if(data.field.id==null||data.field.id==''){
                Common.ajax('quartzManage/quartz',$("#form1").serialize(),true,'POST',quartzSaveSuccess);
            }else{
                Common.ajax('quartzManage/quartz',$("#form1").serialize(),true,'PUT',quartzSaveSuccess);
            }
            return false;
        });
    });
});
function quartzSaveSuccess(data){
    parent.quartzSaveOrUpdateSuccess();
}