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
        onClick: menuNodeOnClick
    }
};

/**
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeMenuNodeClick(treeNode){
    document.getElementById('pid').value=treeNode.id;
    document.getElementById('parentName').value=treeNode.name;
}

//初始化页面
$(function(){
    loadMenuTree();
    $("#parentName").on("click",function(){
        layui.use('layer', function(){
            var layer = layui.layer;
            layer.open({
                type: 2,
                skin: 'layui-layer-lan', //
                title:'菜单选择',
                scrollbar: false, //  滚动条 禁止
                // closeBtn: 0,
                area: ['265px', '350px'], //宽高
                content: Common.ctxPath+'menuManage/menutree/none'
            });
        });
    });
    $("#chongzhi").click(function () {
        layui.use(['form'], function(){
            var form = layui.form;
            $("#id").val("");
            $("#pid").val("");
            $("#moduleName").val("");
            $("#moduleCode").val("");
            $("#moduleIcon").val("");
            $('select[name="moduleType"]').val(1);
            $('select[name="moduleTarget"]').val("navTab");
            $("input[name='moduleStatus'][value='1']").prop("checked",true);
            $("#sortNum").val("");
            $("#parentName").val("");
            form.render();
        });
    });
});

/**
 * 加载菜单树
 */
function loadMenuTree(){
    var param = {};
    Common.ajax('menuManage/menuListTree',param,true,'GET',initMenuTree)
}

/**
 * 初始化菜单树
 * @param data
 */
function initMenuTree(data){
    treeObj = $.fn.zTree.init($("#mene_tree"), setting, data);
    expandRoot();
}

function menuNodeOnClick(event, treeId, treeNode) {
    $("#chongzhi").click();
    Common.ajax('menuManage/menu/'+treeNode.id,null,true,'GET',initForm);
};

/**
 * 点击菜单树节点，给form表单元素赋值
 * @param data
 */
function initForm(data){
    layui.use(['form'], function(){
        var form = layui.form;
        $("#id").val(data.id);
        $("#pid").val(data.pid);
        $("#moduleName").val(data.moduleName);
        $("#moduleCode").val(data.moduleCode);
        $("#moduleIcon").val(data.moduleIcon);
        $("#moduleUrl").val(data.moduleUrl);
        $('select[name="moduleType"]').val(data.moduleType);
        $('select[name="moduleTarget"]').val(data.moduleTarget);
        $("input[name='moduleStatus'][value='"+data.moduleStatus+"']").prop("checked",true);
        $("#sortNum").val(data.sortNum);
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

layui.use(['form'], function(){
    var form = layui.form;
    form.on('submit(saveOrUpdate)', function(data){
        if(data.field.id==null||data.field.id==''){ //保存
            Common.ajax('menuManage/menu',$("#form1").serialize(),true,'POST',saveMenu);
        }else{//更新
            Common.ajax('menuManage/menu',$("#form1").serialize(),true,'PUT',updateMenu);
        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });
    form.on('submit(deleteOne)', function(data){
        var selectNode = treeObj.getSelectedNodes()[0];
        var cnodes = treeObj.getNodesByParam("pId", selectNode.id, selectNode);
        if(cnodes != null && cnodes != undefined && cnodes.length > 0){
            Common.info("存在子节点，不允许删除！");
        }else{
            Common.ajax('menuManage/menu/'+data.field.id,null,true,'DELETE',deleteMenu);
        }
        //阻止表单跳转。如果需要表单跳转，去掉这段即可。
        return false;
    });

    /**
     * 保存菜单成功后，将菜单信息添加到ztree中
     * @param data
     */
    function saveMenu(data){
        var pid = $("#pid").val();
        //将新增地区异步添加到zTree树
        var pNode = treeObj.getNodeByParam("id",pid);
        var newNode = {};
        newNode.id = data.id;
        newNode.pId = data.pid;
        newNode.name = data.moduleName;
        newNode = treeObj.addNodes(pNode, newNode);
        Common.openConfirm("添加成功，是否重置表单？",function () {
            $("#chongzhi").click();
        })
    }
    /**
     * 更新菜单信息
     * @param selectNode
     * @param data
     */
    function updateMenu(data){
        var selectNode = treeObj.getSelectedNodes()[0];
        var pid = $("#pid").val();
        if(selectNode.pId == data.pid){//节点位置不变
            selectNode.name = data.moduleName
            treeObj.updateNode(selectNode);
        }else{//节点位置改变
            if(selectNode.isParent){
                //重新初始化ztree树
                treeObj.destroy();
                loadMenuTree();
            }else{
                //先移除
                treeObj.removeNode(selectNode);
                //再新增
                var pNode = treeObj.getNodeByParam("id",pid);
                var newNode = {};
                newNode.id = data.id;
                newNode.pId = data.pid;
                newNode.name = data.moduleName;
                newNode = treeObj.addNodes(pNode, newNode);
            }
        }
        Common.success("更新成功！");
    }

    function deleteMenu(data){
        var selectNode = treeObj.getSelectedNodes()[0];
        treeObj.removeNode(selectNode);
        Common.success("删除成功！");
    }

});