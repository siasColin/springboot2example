var roleTreeObj;
var roleAndUsersTable;
//定义处理数据结构转化的适配器
var parseDataFun = function (res){
    return {
        "code": res.returnCode
        , "message": res.returnMessage
        , "data": res.data
        , "count": res.total
    };
};
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


$(function(){
    loadRoleTree();
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

function saveRoleAndUser(){
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
    $('#roleAndUserlist').next('.layui-table-view').remove();
    if(treeNode.checked){
        layui.use([ 'layer', 'table','form'], function(){
            var layer = layui.layer //弹层
                ,table = layui.table //表格
                ,form = layui.form //表格

            //执行一个 table 实例
            roleAndUsersTable = table.render({
                id: 'roleAndUserTable'
                ,elem: '#roleAndUserlist'
                ,height: "full-135"
                ,limit:10
                ,method:'GET'
                ,url: Common.ctxPath+'userManage/roleAndUserList/'+treeNode.id //数据接口
                ,parseData :parseDataFun
                ,title: '用户信息表'
                ,page: true //开启分页
                ,toolbar: '#info_toolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
                ,defaultToolbar: ['filter', 'print']
                ,totalRow: false //开启合计行
                ,cols: [[ //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field: 'userName', title: '用户名称'}
                    ,{field: 'loginName', title: '登录名'}
                    ,{field: 'userGender', title: '性别',templet: function(d){
                            if(d.userGender == 1){
                                return "女";
                            }else if(d.userGender == 0){
                                return "男";
                            }
                        }}
                    ,{field: 'orgName', title: '所属机构'}
                    ,{field: 'userStatus', title: '用户状态',templet: function(d){
                            if(d.userStatus == 0){
                                return "正常";
                            }else if(d.userStatus == 2){
                                return "禁用";
                            }else if(d.userStatus == 3){
                                return "过期";
                            }else if(d.userStatus == 4){
                                return "锁定";
                            }
                        }}
                    ,{fixed: 'right',  align:'center', toolbar: '#barRoleAndUserlist',width:130}
                ]]
            });


            //监听头工具栏事件
            table.on('toolbar(roleAndUserTable)', function(obj){
                var checkStatus = table.checkStatus(obj.config.id)
                    ,data = checkStatus.data; //获取选中的数据
                switch(obj.event){
                    case 'add':
                        layer.open({
                            title:"用户信息",
                            type: 2,
                            area: ['75%','90%'],
                            btn: ['确定', '取消'],
                            content: Common.ctxPath+'userManage/userListByRoleIdAndNotBind',
                            yes: function(index,layero){
                                var iframeWin = window[layero.find('iframe')[0]['name']];
                                var userIds = iframeWin.getChecks();
                                if(!$.isEmpty(userIds)){
                                    var checkRoleId = getCheckRoleId();
                                    var params = {};
                                    params.users = userIds;
                                    Common.ajax("roleManage/roleAndUser/"+checkRoleId,params,true,"POST",saveRoleAndUsersSuccess);
                                }
                            }
                        });
                        break;
                    case 'delete':
                        if(data.length === 0){
                            Common.info("请至少选择一行");
                        } else {
                            var ids = "";
                            for(var i=0;i<data.length;i++){
                                if(i == data.length - 1){
                                    ids+=data[i].id
                                }else{
                                    ids+=data[i].id+',';
                                }
                            }
                            var params = {};
                            params.users = ids;
                            Common.openConfirm("确定解除绑定吗?",function () {
                                var checkRoleId = getCheckRoleId();
                                Common.ajax('roleManage/roleAndUser/'+checkRoleId,params,true,'DELETE',deleteRoleAndUsersSuccess);
                            });
                        }
                        break;
                };
            });

            //监听行工具事件
            table.on('tool(roleAndUserTable)', function(obj){
                var data = obj.data //获得当前行数据
                    ,layEvent = obj.event; //获得 lay-event 对应的值
                if(layEvent === 'del'){
                    var params = {};
                    params.users = data.id;
                    Common.openConfirm("确定解除绑定吗?",function (){
                        var checkRoleId = getCheckRoleId();
                        Common.ajax('roleManage/roleAndUser/'+checkRoleId,params,true,'DELETE',deleteRoleAndUsersSuccess);
                    })
                }
            });
        });
    }
}

function updateMenuTree(data){
    for (i=0;i<data.length ;i++ ){
        var node = menuTreeObj.getNodeByParam("id", data[i], null);
        menuTreeObj.checkNode(node, true, false);
    }
}

function getCheckRoleId(){
    var nodes = roleTreeObj.getCheckedNodes(true);
    if(nodes.length > 0){
        return nodes[0].id;
    }else{
        null;
    }
}

function saveRoleAndUsersSuccess(data){
    layui.use('layer', function() {
        var layer = layui.layer;
        layer.closeAll('iframe'); //关闭弹框
    });
    roleAndUsersTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}
function deleteRoleAndUsersSuccess(data){
    roleAndUsersTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
    });
}