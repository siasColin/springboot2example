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
        onClick: orgNodeOnClick
    }
};

/**
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeAreaNodeClick(treeNode){
    document.getElementById('areaCode').value=treeNode.id;
    document.getElementById('areaName').value=treeNode.name;
}

/**
 * 弹出层中的机构树点击时触发，主要用于给页面元素赋值
 * @param treeNode
 */
function iframeOrgNodeClick(treeNode){
    document.getElementById('parentCode').value=treeNode.id;
    document.getElementById('parentName').value=treeNode.name;
}

//初始化页面
$(function(){
    loadOrgTree();

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

    $("#parentName").on("click",function(){
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

    $("#chongzhi").click(function () {
        $("input").val("");
        $("img").removeAttr("src");
    });
});


/**
 * 加载机构树
 */
function loadOrgTree(){
    var param = {};
    $.ajax({
        async:true,
        type: "GET",
        url: Common.ctxPath+'orgManage/orgListTree',
        data:param,
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success: function(rsp){
            if(rsp.returnCode == '0'){
                treeObj = $.fn.zTree.init($("#org_tree"), setting, rsp.data);
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


function expandRoot(){
    var nodes = treeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

layui.use(['form','upload','layer'], function(){
    var form = layui.form
    ,layer = layui.layer
    ,upload = layui.upload;

    form.on('submit(saveOrUpdate)', function(data){
        if(data.field.id==null||data.field.id==''){ //保存
            //判断当前添加的机构编码是否已存在
            $.ajax({
                type : 'GET',
                url: Common.ctxPath+"orgManage/orgOnCode/"+data.field.orgCode,//请求的action路径
                error: function () {//请求失败处理函数
                    Common.error('请求失败!')
                },
                success:function(obj) {
                    if(obj.data !=null && obj.data != '' && obj.data.orgCode != null && obj.data.orgCode != ''){
                        Common.info("机构编码已经存在");
                        return;
                    }else{
                        $.ajax({
                            type : 'POST',
                            url : Common.ctxPath+'orgManage/org',
                            data :$("#form1").serialize(),
                            dataType : 'json',
                            beforeSend : function(xhr) {
                                xhr.setRequestHeader(_header, _token);
                            },
                            success : function(rsp) {
                                if(rsp.returnCode == '0'){
                                    //将新增机构异步添加到zTree树
                                    var pNode = treeObj.getNodeByParam("id",data.field.parentCode);
                                    var newNode = {};
                                    newNode.id = rsp.data.orgCode;
                                    newNode.pId = rsp.data.parentCode;
                                    newNode.name = rsp.data.orgName;
                                    newNode.param = {"orgid":rsp.data.id};
                                    newNode = treeObj.addNodes(pNode, newNode);
                                    Common.openConfirm("添加成功，是否重置表单？",function () {
                                        $("input").val("");
                                        $("img").removeAttr("src");
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
            var selectNode = treeObj.getSelectedNodes()[0];
            if(selectNode.id != data.field.orgCode){//orgCode被修改
                //判断当前添加的机构编码是否已存在
                $.ajax({
                    type : 'GET',
                    url: Common.ctxPath+"orgManage/orgOnCode/"+data.field.orgCode,//请求的action路径
                    error: function () {//请求失败处理函数
                        Common.error('请求失败!')
                    },
                    success:function(obj) {
                        if(obj.data !=null && obj.data != '' && obj.data.orgCode != null && obj.data.orgCode != ''){
                            Common.info("机构编码已经存在");
                            return;
                        }else{
                            //由于orgCode作为和其他表的关联字段，如果修改orgCode则需要提醒用户是否同步修改关联表
                            //首先判断该机构编码是否被其他表引用
                            var isQuote = checkRelation(selectNode.id).isQuote;
                            if(isQuote){//如果被引用提示用户将同时更新被引用的地方
                                Common.openConfirm("机构编码存在外键引用，需要同步更新，是否继续？",function(){
                                    $.ajax({
                                        type : 'PUT',
                                        url : Common.ctxPath+'orgManage/org/'+selectNode.id,
                                        data :$("#form1").serialize(),
                                        dataType : 'json',
                                        beforeSend : function(xhr) {
                                            xhr.setRequestHeader(_header, _token);
                                        },
                                        success : function(rsp) {
                                            if(rsp.returnCode == '0'){
                                                //如果修改的节点是父节点（存在子节点）则影响相对较大，这里重新初始化ztree树
                                                if(selectNode.isParent){
                                                    //更新机构编码,影响ztree的整体结构。所以这里重新初始化ztree树
                                                    treeObj.destroy();
                                                    loadOrgTree();
                                                }else{
                                                    if(selectNode.pId == rsp.data.parentCode){//节点位置不变
                                                        selectNode.id = rsp.data.orgCode
                                                        selectNode.name = rsp.data.orgName
                                                        treeObj.updateNode(selectNode);
                                                    }else{//节点位置改变
                                                        //先移除
                                                        treeObj.removeNode(selectNode);
                                                        //再新增
                                                        var pNode = treeObj.getNodeByParam("id",data.field.parentCode);
                                                        var newNode = {};
                                                        newNode.id = rsp.data.orgCode;
                                                        newNode.pId = rsp.data.parentCode;
                                                        newNode.name = rsp.data.orgName;
                                                        newNode.param = {"orgid":rsp.data.id};
                                                        newNode = treeObj.addNodes(pNode, newNode);
                                                    }
                                                }
                                                Common.success(rsp.returnMessage);
                                            }else if(rsp.returnCode == '006'){//当前登录用户关联信息已更细，提示用户重新登录
                                                layer.confirm(rsp.returnMessage, {
                                                    btn: ['确定'], //按钮
                                                    shade:0.5
                                                }, function(){
                                                    $("#logout",parent.document).submit();
                                                });
                                            }else{
                                                Common.error(rsp.returnMessage);
                                            }
                                        },
                                        error : function(rsp) {
                                            Common.error("修改失败");
                                        }
                                    });
                                })
                            }else{//没有被引用，则可以直接更新
                                updateOrg(selectNode,data);
                            }
                        }
                    }
                });
            }else{
                updateOrg(selectNode,data);
            }

        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

    form.on('submit(deleteOne)', function(data){
        var checkObj = checkRelation(data.field.orgCode);
        if(checkObj.isQuote){//存在引用，不允许删除
            Common.info(checkObj.msg);
        }else{
            $.ajax({
                type : 'DELETE',
                url : Common.ctxPath+'orgManage/org/'+data.field.id,
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
        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });


    //上传
    var uploadInst = upload.render({
        elem: '#orgLogoPlugin'
        ,url: Common.ctxPath+'common/uploadSingle'
        ,accept: 'images' //普通文件
        ,acceptMime: 'image/*'
        ,field:'file'
        ,data: {"prefixPath": 'orgLogo'}
        ,before: function () {
            Common.load();
        }
        ,done: function(res){
            Common.closeload();
            if(res.returnCode == '0'){//成功
                $("#orglogoImg").attr("src",Common.ctxPath+res.data.fileUrl);
                $("#orgLogo").val(res.data.fileUrl);
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


function orgNodeOnClick(event, treeId, treeNode) {
    $("input").val("");
    $("img").removeAttr("src");
    $.ajax({
        async:true,
        type: "GET",
        url: Common.ctxPath+'orgManage/org/'+treeNode.param.orgid,
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success: function(rsp){
            if(rsp.returnCode == '0'){
                var data = rsp.data;
                layui.use(['form'], function(){
                    var form = layui.form;
                    $("#orgName").val(data.orgName);
                    $("#orgCode").val(data.orgCode);
                    $("#id").val(data.id);
                    $("#parentCode").val(data.parentCode);
                    $("#parentName").val(data.parentName);
                    $("#areaCode").val(data.areaCode);
                    $("#areaName").val(data.areaName);
                    $("#orgAddress").val(data.orgAddress);
                    $("#sortNum").val(data.sortNum);
                    if(!$.isEmpty(data.orgLogo)){
                        $("#orgLogo").val(data.orgLogo);
                        $("#orglogoImg").attr("src",Common.ctxPath+data.orgLogo);
                    }
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

/**
 * 检查orgCode和其他表的关联关系
 * @param orgCode
 */
function checkRelation(orgCode){
    var resultobj = {};
    //是否和其他表存在关联关系
    resultobj.isQuote = true;
    $.ajax({
        async:false,
        type : 'GET',
        url : Common.ctxPath+'orgManage/orgRelation/'+orgCode,
        dataType : 'json',
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success : function(rsp) {
            if(rsp.returnCode == '0'){
                resultobj.isQuote = true;
                resultobj.msg = rsp.data.msg;
            }else{
                resultobj.isQuote = false;
            }
        },
        error : function(rsp) {
            Common.error("验证机构编码关联关系失败！！！");
        }
    });
    return resultobj;
}


/**
 * 更新机构信息
 * @param selectNode
 * @param data
 */
function updateOrg(selectNode,data){
    $.ajax({
        type : 'PUT',
        url : Common.ctxPath+'orgManage/org',
        data :$("#form1").serialize(),
        dataType : 'json',
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success : function(rsp) {
            if(rsp.returnCode == '0'){
                if(selectNode.pId == rsp.data.parentCode){//节点位置不变
                    selectNode.id = rsp.data.orgCode
                    selectNode.name = rsp.data.orgName
                    treeObj.updateNode(selectNode);
                }else{//节点位置改变
                    if(selectNode.isParent){
                        //重新初始化ztree树
                        treeObj.destroy();
                        loadOrgTree();
                    }else{
                        //先移除
                        treeObj.removeNode(selectNode);
                        //再新增
                        var pNode = treeObj.getNodeByParam("id",data.field.parentCode);
                        var newNode = {};
                        newNode.id = rsp.data.orgCode;
                        newNode.pId = rsp.data.parentCode;
                        newNode.name = rsp.data.orgName;
                        newNode.param = {"orgid":rsp.data.id};
                        newNode = treeObj.addNodes(pNode, newNode);
                    }
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