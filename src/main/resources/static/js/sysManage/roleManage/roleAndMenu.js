var roleTreeObj;
var menuTreeObj;
var roleSetting = {
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
    check:{
        chkStyle: "radio"//单选
        ,enable: true //true / false 分别表示 显示 / 不显示 复选框或单选框
        ,radioType: "all"
    },
    callback: {
        onClick: roleTreeOnclick
        ,onCheck: roleTreeOnCheck
    }
};

var menuSetting = {
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
    check:{
        chkStyle: "checkbox",//单选
        enable: true //true / false 分别表示 显示 / 不显示 复选框或单选框
    }
};

$(function(){
    loadRoleTree();
    loadMenuTree();
});

/**
 * 加载角色（带地区层级）树
 */
function loadRoleTree(){
    var param = {};
    Common.ajax('roleManage/roleWithAreaListTree/4',param,true,'GET',initRoleTree);
}

function initRoleTree(data){
    roleTreeObj = $.fn.zTree.init($("#role_tree"), roleSetting, data);
    expandRoleRoot();
}

function expandRoleRoot(){
    var nodes = roleTreeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            roleTreeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

function roleTreeOnclick(event, treeId, treeNode) {
    roleTreeObj.checkNode(treeNode, true, false,true);
}


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
    menuTreeObj = $.fn.zTree.init($("#mene_tree"), menuSetting, data);
    expandMenuRoot();
}

function expandMenuRoot(){
    var nodes = menuTreeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            menuTreeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

function saveRoleAndMenu(){
    var checkedRole = Common.getCheckedNodesWithOutParent(roleTreeObj);
    if($.isEmpty(checkedRole)){
        Common.info("请选择角色！")
    }else{
        var checkedMenus = Common.getCheckedNodes(menuTreeObj);
        var params = {};
        params.checkedRole = checkedRole;
        params.checkedMenus = checkedMenus;
        Common.ajax('roleManage/roleAndMenu',params,true,'POST',null);
    }
}

function roleTreeOnCheck(event, treeId, treeNode){
    menuTreeObj.checkAllNodes(false);
    if(treeNode.checked){
        var roleId = treeNode.id;
        Common.ajax('roleManage/roleAndMenu/'+roleId,null,true,'GET',updateMenuTree);
    }
}

function updateMenuTree(data){
    for (i=0;i<data.length ;i++ ){
        var node = menuTreeObj.getNodeByParam("id", data[i], null);
        menuTreeObj.checkNode(node, true, false);
    }
}