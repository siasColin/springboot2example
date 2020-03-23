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

/**
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeAreaNodeClick(treeNode){
    document.getElementById('parentCode').value=treeNode.id;
    document.getElementById('parentName').value=treeNode.name;
}

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
        layui.use(['form','util'], function(){
            var form = layui.form;
            $("#areaCode").val('');
            $('select[name="areaLevel"]').val(0);
            $("#areaName").val('');
            $("#id").val('');
            $("#latitude").val('');
            $("#longitude").val('');
            $("#parentCode").val('');
            $("#parentName").val('');
            form.render();
        });
    });
});

/**
 * 加载地区树
 */
function loadAreaTree(){
    var param = {};
    // param.areaName = "河南省";
    Common.ajax('areaManage/areaListTree',param,true,'GET',initAreaTree);
}

function initAreaTree(data){
    treeObj = $.fn.zTree.init($("#area_tree"), setting, data);
    expandRoot();
}

function areaidFilter(node) {
    return (node.param.areaid == $("#id").val());
}

function areaNodeOnClick(event, treeId, treeNode) {
    $("#chongzhi").click();
    Common.ajax('areaManage/area/'+treeNode.param.areaid,null,true,'GET',initAreaForm);
};

function initAreaForm(data){
    layui.use(['form','util'], function(){
        var form = layui.form;
        $("#areaCode").val(data.areaCode);
        $('select[name="areaLevel"]').val(data.areaLevel);
        $("#areaName").val(data.areaName);
        $("#id").val(data.id);
        $("#latitude").val(data.latitude);
        $("#longitude").val(data.longitude);
        $("#parentCode").val(data.parentCode);
        $("#parentName").val(data.parentName);
        form.render();
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

function saveSuccess(data){
    var parentCode = $("#parentCode").val();
    //将新增地区异步添加到zTree树
    var pNode = treeObj.getNodeByParam("id",parentCode);
    var newNode = {};
    newNode.id = data.areaCode;
    newNode.pId = data.parentCode;
    newNode.name = data.areaName;
    newNode.param = {"areaid":data.id};
    newNode = treeObj.addNodes(pNode, newNode);
    Common.openConfirm("添加成功，是否重置表单？",function () {
        $("#chongzhi").click();
    });
}

function saveHandler(data){
    if(data !=null && data != '' && data.areaCode != null && data.areaCode != ''){
        Common.info("地区编码已经存在");
        return;
    }else{
        Common.ajax('areaManage/area',$("#form1").serialize(),true,'POST',saveSuccess);
    }
}

function updateSuccess(data){
    var selectNode = treeObj.getSelectedNodes()[0];
    //如果修改的节点是父节点（存在子节点）则影响相对较大，这里重新初始化ztree树
    if(selectNode.isParent && (selectNode.pId != data.parentCode || selectNode.id != data.areaCode)){
        //更新地区编码,影响ztree的整体结构。所以这里重新初始化ztree树
        treeObj.destroy();
        loadAreaTree();
        $("#chongzhi").click();
    }else{
        if(selectNode.pId == data.parentCode){//节点位置不变
            selectNode.id = data.areaCode
            selectNode.name = data.areaName
            treeObj.updateNode(selectNode);
        }else{//节点位置改变
            var parentCode = $("#parentCode").val();
            //先移除
            treeObj.removeNode(selectNode);
            //再新增
            var pNode = treeObj.getNodeByParam("id",parentCode);
            var newNode = {};
            newNode.id = data.areaCode;
            newNode.pId = data.parentCode;
            newNode.name = data.areaName;
            newNode.param = {"areaid":data.id};
            newNode = treeObj.addNodes(pNode, newNode);
        }
    }
    Common.success("更新成功");
}

function updateHandler(data){
    var selectNode = treeObj.getSelectedNodes()[0];
    if(data !=null && data != '' && data.areaCode != null && data.areaCode != ''){
        Common.info("地区编码已经存在");
        return;
    }else{
        //由于areaCode作为和其他表的关联字段，如果修改areaCode则需要提醒用户是否同步修改关联表
        //首先判断该地区编码是否被其他表引用
        var isQuote = checkRelation(selectNode.id).isQuote;
        if(isQuote){//如果被引用提示用户将同时更新被引用的地方
            Common.openConfirm("地区编码存在外键引用，需要同步更新，是否继续？",function(){
                Common.ajax('areaManage/area/'+selectNode.id,$("#form1").serialize(),true,'PUT',updateSuccess);
            });
        }else{//没有被引用，则可以直接更新
            Common.ajax('areaManage/area',$("#form1").serialize(),true,'PUT',updateSuccess);
        }
    }
}

function deleteSuccess(data){
    var selectNode = treeObj.getSelectedNodes()[0];
    treeObj.removeNode(selectNode);
    $("#chongzhi").click();
    Common.success("删除成功！");
}

layui.use(['form'], function(){
    var form = layui.form;
    form.on('submit(saveOrUpdate)', function(data){
        if(data.field.id==null||data.field.id==''){ //保存
            //判断当前添加的地区是否已存在
            Common.ajax("areaManage/areaOnCode/"+data.field.areaCode,null,true,'GET',saveHandler);
        }else{
            var selectNode = treeObj.getSelectedNodes()[0];
            if(selectNode.id != data.field.areaCode){//areaCode被修改
                //判断当前添加的地区是否已存在
                Common.ajax("areaManage/areaOnCode/"+data.field.areaCode,null,true,'GET',updateHandler);
            }else{
                Common.ajax('areaManage/area',$("#form1").serialize(),true,'PUT',updateSuccess);
            }

        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
    form.on('submit(deleteOne)', function(data){
        var checkObj = checkRelation(data.field.areaCode);
        if(checkObj.isQuote){//存在引用，不允许删除
            Common.info(checkObj.msg);
        }else{
            Common.ajax('areaManage/area/'+data.field.id,null,true,'DELETE',deleteSuccess);
        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
});

/**
 * 检查areaCode和其他表的关联关系
 * @param areaCode
 */
function checkRelation(areaCode){
    var resultobj = {};
    //是否和其他表存在关联关系
    resultobj.isQuote = true;
    $.ajax({
        async:false,
        type : 'GET',
        url : Common.ctxPath+'areaManage/areaRelation/'+areaCode,
        dataType : 'json',
        success : function(rsp) {
            if(rsp.returnCode == '0'){
                resultobj.isQuote = true;
                resultobj.msg = rsp.data.msg;
            }else{
                resultobj.isQuote = false;
            }
        },
        error : function(rsp) {
            Common.error("验证地区编码关联关系失败！！！");
        }
    });
    return resultobj;
}