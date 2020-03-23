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
/*    check:{
        //chkboxType: { "Y": "ps", "N": "ps" },
        chkStyle: "radio",//单选
        enable: true //每个节点上是否显示 CheckBox
    },*/
    callback: {
        onClick: zTreeOnclick
    }
};

$(function(){
    loadOrgTree();
});

function initOrgTree(data){
    treeObj = $.fn.zTree.init($("#org_tree"), setting, data);
    expandRoot();
}
/**
 * 加载地区树
 */
function loadOrgTree(){
    var param = {};
    Common.ajax('orgManage/orgListTree',param,true,'GET',initOrgTree);
}

function expandRoot(){
    var nodes = treeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

function zTreeOnclick(event, treeId, treeNode) {
    if (treeNode.id != null) {
        parent.iframeOrgNodeClick(treeNode);
    }
    //关闭此页面
    var index = parent.layer.getFrameIndex(window.name);
    parent.layer.close(index);
}