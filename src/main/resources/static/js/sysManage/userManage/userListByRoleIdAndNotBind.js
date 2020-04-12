var usertable;
//定义处理数据结构转化的适配器
var parseDataFun = function (res){
    return {
        "code": res.returnCode
        , "message": res.returnMessage
        , "data": res.data
        , "count": res.total
    };
};

/**
 * 弹出层中的机构树点击时触发，主要用于给页面元素赋值
 * @param treeNode
 */
function iframeOrgNodeClick(treeNode){
    document.getElementById('orgCode').value=treeNode.id;
    document.getElementById('orgName').value=treeNode.name;
}

$(function(){
    $("#orgName").on("click",function(){
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
    var roleId = parent.getCheckRoleId();
    layui.use([ 'layer', 'table','form'], function() {
        var layer = layui.layer //弹层
            , table = layui.table //表格
            , form = layui.form //表格

        //执行一个 table 实例
        usertable = table.render({
            id: 'usertable'
            , elem: '#userlist'
            , height: "full-135"
            , limit: 10
            , method: 'GET'
            , url: Common.ctxPath + 'userManage/userList?roleIdNotBind='+roleId //数据接口
            , parseData: parseDataFun
            , title: '用户信息表'
            , page: true //开启分页
            , totalRow: false //开启合计行
            , cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                , {field: 'userName', title: '用户名称'}
                , {field: 'loginName', title: '登录名'}
                , {
                    field: 'userGender', title: '性别', templet: function (d) {
                        if (d.userGender == 1) {
                            return "女";
                        } else if (d.userGender == 0) {
                            return "男";
                        }
                    }
                }
                , {field: 'orgName', title: '所属机构'}
                , {
                    field: 'userStatus', title: '用户状态', templet: function (d) {
                        if (d.userStatus == 0) {
                            return "正常";
                        } else if (d.userStatus == 2) {
                            return "禁用";
                        } else if (d.userStatus == 3) {
                            return "过期";
                        } else if (d.userStatus == 4) {
                            return "锁定";
                        }
                    }
                }
            ]]
        });
        $("#chongzhi").click(function () {
            layui.use(['form'], function(){
                var form = layui.form;
                $("#userName").val("");
                $("#orgCode").val("");
                $("#orgName").val("");
                form.render();
            });
        });
    });
});
function search(){
    var userName = $('#userName');
    var orgCode = $('#orgCode');
    usertable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
        ,where: {
            userName: userName.val(),
            orgCode: orgCode.val()

        }
    });
}

function getChecks(){
    var ids = "";
    layui.use(['table'], function() {
        var table = layui.table; //表格
        var checkStatus = table.checkStatus('usertable')
            ,data = checkStatus.data; //获取选中的数据
        if(data.length === 0){
            Common.info("请至少选择一行");
        }else{
            for(var i=0;i<data.length;i++){
                if(i == data.length - 1){
                    ids+=data[i].id
                }else{
                    ids+=data[i].id+',';
                }
            }
        }
    });
    return ids;
}