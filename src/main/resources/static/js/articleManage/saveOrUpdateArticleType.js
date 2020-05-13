$(function(){
    layui.use('form', function(){
        var form = layui.form;
        //监听提交
        form.on('submit(save)', function(data){
            if(data.field.id==null||data.field.id==''){
                Common.ajax('articleTypeManage/articleType',$("#form1").serialize(),true,'POST',articleTypeSaveSuccess);
            }else{
                Common.ajax('articleTypeManage/articleType',$("#form1").serialize(),true,'PUT',articleTypeSaveSuccess);
            }
            return false;
        });
    });
});
function articleTypeSaveSuccess(data){
    parent.articleTypeSaveOrUpdateSuccess();
}