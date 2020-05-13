$(function(){
    loadArticleTypeList();
    layui.use(['form','upload'], function(){
        var form = layui.form
            ,upload = layui.upload;
        //监听提交
        layui.use('form', function(){
            var form = layui.form;
            //监听提交
            form.on('submit(save)', function(data){
                if(data.field.id==null||data.field.id==''){
                    Common.ajax('articleManage/article',data.field,true,'POST',null);
                }else{
                    Common.ajax('articleManage/article',data.field,true,'PUT',articleUpdateSuccess);
                }
                return false;
            });
        });
        //上传封面图片
        var showimgInst = upload.render({
            elem: '#showimg'
            ,url: Common.ctxPath+'common/uploadSingle'
            ,accept: 'images' //普通文件
            ,acceptMime: 'image/*'
            ,field:'file'
            ,data: {"prefixPath": 'article/showimg'}
            ,before: function () {
                Common.load();
            }
            ,done: function(res){
                Common.closeload();
                if(res.returnCode == '0'){//成功
                    $("#showimg").attr("src",Common.ctxPath+res.data.fileUrl);
                    $("#infoCoverimg").val(res.data.fileUrl);
                    Common.success("上传成功");
                }else{
                    Common.info(res.returnMessage);
                }
            }
            ,error: function(){
                Common.closeload();
                Common.info("请求异常");
            }
        });
        //上传附件
        var infoAttachmentsInst = upload.render({
            elem: '#infoAttachmentsButton'
            ,url: Common.ctxPath+'common/uploadSingle'
            ,accept: 'file' //普通文件
            ,field:'file'
            ,data: {"prefixPath": 'article/attachments'}
            ,before: function () {
                Common.load();
            }
            ,done: function(res){
                Common.closeload();
                if(res.returnCode == '0'){//成功
                    var filepath = encodeURIComponent(encodeURIComponent(res.data.fileUrl));
                    var downloadPath = Common.ctxPath+'common/downFile?filepath='+filepath
                    var attachmentHtml = '<a href="'+downloadPath+'">'+res.data.fileName+'</a><i onclick="removeAttachment();" class="layui-icon layui-icon-delete" style="font-size: 24px; color: #1E9FFF;vertical-align: bottom;cursor: pointer;"></i>';
                    $("#infoAttachmentsFile").html(attachmentHtml);
                    $("#infoAttachments").val(res.data.fileUrl);
                    $("#infoFilename").val(res.data.fileName);
                    Common.success("上传成功");
                }else{
                    Common.info(res.returnMessage);
                }
            }
            ,error: function(){
                Common.closeload();
                Common.info("请求异常");
            }
        });
    });
});
function articleUpdateSuccess(data){
    parent.articleUpdateSuccess();
}

function removeAttachment(){
    $("#infoAttachmentsFile").html('');
}

function loadArticleTypeList(){
    Common.ajax('articleTypeManage/allArticleTypeListData',null,true,'GET',initArticleType);
}
function initArticleType(data) {
    if(data != null && data.length > 0){
        var articleTypeObj = $("#typeId");
        var checkTypeId = $("#editTypeId").val();
        for(var i = 0;i<data.length;i++){
            var optionHtml = '<option value="'+data[i].id+'"';
            if(!$.isEmpty(checkTypeId) && checkTypeId == data[i].id){
                optionHtml += ' selected';
            }
            optionHtml += '>'+data[i].typeName+'</option>';
            articleTypeObj.append(optionHtml);
        }
        layui.use(['form'], function() {
            var form = layui.form //表格
            form.render('select');
        });
    }
}

function saveArticle(){
    $("#permissionSubmit").click();
}