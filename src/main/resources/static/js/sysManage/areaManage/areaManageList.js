var _header = $("meta[name='_csrf_header']").attr("content");
var _token =$("meta[name='_csrf']").attr("content");
var treeObj;
var setting = {
    view: {
        dblClickExpand: false,//双击节点时，是否自动展开父节点的标识
        showLine: true,//是否显示节点之间的连线
        // fontCss:{'color':'black','font-weight':'bold'},//字体样式函数
        selectedMulti: false //设置是否允许同时选中多个节点
    },
    data: {
        simpleData: {
            enable: true
        }
    },
    callback: {
        onClick: areaNodeOnClick
    }
};

//初始化页面
$(function(){
    loadAreaTree();
    $("#parentName").on("click",function(){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //
                title:'地区选择',
                scrollbar: false, //  滚动条 禁止
                // closeBtn: 0,
                area: ['265px', '350px'], //宽高
                content: Common.ctxPath+'areaManage/areatree/none'
            });
        });
    });
    $("#chongzhi").click(function () {
        $("input").val("");
    });
});

/**
 * 加载地区树
 */
function loadAreaTree(){
    var param = {};
    // param.areaName = "河南省";
    $.ajax({
        async:true,
        type: "GET",
        url: Common.ctxPath+'areaManage/areaListTree',
        data:param,
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success: function(rsp){
            if(rsp.returnCode == '0'){
                treeObj = $.fn.zTree.init($("#area_tree"), setting, rsp.data);
                expandRoot();
            }else{
                Common.error(rsp.returnMessage);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            // 通过XMLHttpRequest取得响应头，sessionstatus，
            if(sessionstatus == "sessionTimeOut"){
                window.location.replace("/");
            }else {
                Common.error("请求异常")
            }
        }
    });
}

function areaidFilter(node) {
    return (node.param.areaid == $("#id").val());
}

function areaNodeOnClick(event, treeId, treeNode) {
    $.ajax({
        async:true,
        type: "GET",
        url: Common.ctxPath+'areaManage/area/'+treeNode.param.areaid,
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success: function(rsp){
            if(rsp.returnCode == '0'){
                var data = rsp.data;
                layui.use(['form','util'], function(){
                    var form = layui.form;
                    var util = layui.util;
                    $("#areaCode").val(data.areaCode);
                    $('select[name="areaLevel"]').val(data.areaLevel);
                    $("#areaName").val(data.areaName);
                    /*$("#createTime").val(util.toDateString(data.createTime, 'yyyy-MM-dd HH:mm:ss'));*/
                    $("#id").val(data.id);
                    $("#latitude").val(data.latitude);
                    $("#longitude").val(data.longitude);
                    $("#parentCode").val(data.parentCode);
                    $("#parentName").val(data.parentName);
                    form.render();
                });
            }else{
                Common.error(rsp.returnMessage);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            // 通过XMLHttpRequest取得响应头，sessionstatus，
            if(sessionstatus == "sessionTimeOut"){
                window.location.replace("/");
            }else {
                Common.error("请求异常")
            }
        }
    });
};

function expandRoot(){
    var nodes = treeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

layui.use(['form','upload'], function(){
    var form = layui.form
    ,upload = layui.upload;
    form.on('submit(saveOrUpdate)', function(data){
        if(data.field.id==null||data.field.id==''){ //保存
            //判断当前添加的地区是否已存在
            $.ajax({
                type : 'GET',
                url: Common.ctxPath+"areaManage/areaOnCode/"+data.field.areaCode,//请求的action路径
                error: function () {//请求失败处理函数
                    Common.error('请求失败!')
                },
                success:function(obj) {
                    if(obj.data !=null && obj.data != '' && obj.data.areaCode != null && obj.data.areaCode != ''){
                        Common.info("地区编码已经存在");
                        return;
                    }else{
                        $.ajax({
                            type : 'POST',
                            url : Common.ctxPath+'areaManage/area',
                            data :$("#form1").serialize(),
                            dataType : 'json',
                            beforeSend : function(xhr) {
                                xhr.setRequestHeader(_header, _token);
                            },
                            success : function(rsp) {
                                if(rsp.returnCode == '0'){
                                    //将新增地区异步添加到zTree树
                                    var pNode = treeObj.getNodeByParam("id",data.field.parentCode);
                                    var newNode = {};
                                    newNode.id = rsp.data.areaCode;
                                    newNode.pId = rsp.data.parentCode;
                                    newNode.name = rsp.data.areaName;
                                    newNode.param = {"areaid":rsp.data.id};
                                    newNode = treeObj.addNodes(pNode, newNode);
                                    Common.openConfirm("添加成功，是否重置表单？",function () {
                                        $("input").val("");
                                    })
                                }else{
                                    Common.error(rsp.returnMessage);
                                }
                            },
                            error : function(rsp) {
                                Common.error("保存失败");
                            }
                        });
                    }
                }
            });
        }else{
            $.ajax({
                type : 'PUT',
                url : Common.ctxPath+'areaManage/area',
                data :$("#form1").serialize(),
                dataType : 'json',
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(_header, _token);
                },
                success : function(rsp) {
                    if(rsp.returnCode == '0'){
                        var selectNode = treeObj.getSelectedNodes()[0];
                        if(selectNode.pId == rsp.data.parentCode){//节点位置不变
                            selectNode.id = rsp.data.areaCode
                            selectNode.name = rsp.data.areaName
                            treeObj.updateNode(selectNode);
                        }else{//节点位置改变
                            //先移除
                            treeObj.removeNode(selectNode);
                            //再新增
                            var pNode = treeObj.getNodeByParam("id",data.field.parentCode);
                            var newNode = {};
                            newNode.id = rsp.data.areaCode;
                            newNode.pId = rsp.data.parentCode;
                            newNode.name = rsp.data.areaName;
                            newNode.param = {"areaid":rsp.data.id};
                            newNode = treeObj.addNodes(pNode, newNode);
                        }
                        Common.success(rsp.returnMessage);
                    }else{
                        Common.error(rsp.returnMessage);
                    }
                },
                error : function(rsp) {
                    Common.error("修改失败");
                }
            });
        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
    form.on('submit(deleteOne)', function(data){
        $.ajax({
            type : 'DELETE',
            url : Common.ctxPath+'areaManage/area/'+data.field.id,
            dataType : 'json',
            beforeSend : function(xhr) {
                xhr.setRequestHeader(_header, _token);
            },
            success : function(rsp) {
                if(rsp.returnCode == '0'){
                    var selectNode = treeObj.getSelectedNodes()[0];
                    treeObj.removeNode(selectNode);
                    Common.success(rsp.returnMessage);
                }else{
                    Common.error(rsp.returnMessage);
                }
            },
            error : function(rsp) {
                Common.error("删除失败");
            }
        });
    });
    //上传
    /*var uploadInst = upload.render({
        elem: '#imageName'
        ,url: Common.ctxPath+'common/uploadSingle'
        ,accept: 'images' //普通文件
        ,acceptMime: 'image/!*'
        ,field:'file'
        ,data: {"prefixPath": 'logo'}
        ,before: function () {
            Common.load();
        }
        ,done: function(res){
            Common.closeload();
            if(res.returnCode == '0'){//成功
                Common.log(res.data.fileUrl);
                Common.info(res.data.fileUrl);
            }else{
                Common.info(res.returnMessage);
            }
        }
        ,error: function(){
            Common.closeload();
            Common.info("请求异常");
        }
    });*/
});