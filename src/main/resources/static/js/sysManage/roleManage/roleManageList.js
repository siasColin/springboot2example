var roleTable;
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
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeAreaNodeClick(treeNode){
    document.getElementById('areaCode').value=treeNode.id;
    document.getElementById('areaName').value=treeNode.name;
}

/**
 * 保存或者更新成功后回调
 */
function roleSaveOrUpdateSuccess(){
    layui.use('layer', function() {
        var layer = layui.layer;
        layer.closeAll('iframe'); //关闭弹框
    });
    search();
}
$(function(){
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
    layui.use([ 'layer', 'table','form'], function(){
        var layer = layui.layer //弹层
            ,table = layui.table //表格
            ,form = layui.form //表格

        //执行一个 table 实例
        roleTable = table.render({
            id: 'roleTable'
            ,elem: '#rolelist'
            ,height: "full-135"
            ,limit:10
            ,method:'GET'
            ,url: Common.ctxPath+'roleManage/roleList' //数据接口
            ,parseData :parseDataFun
            ,title: '角色信息表'
            ,page: true //开启分页
            ,toolbar: '#info_toolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            ,defaultToolbar: ['filter', 'print']
            ,totalRow: false //开启合计行
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'roleName', title: '角色名称'}
                ,{field: 'roleCode', title: '角色编码'}
                ,{field: 'roleAttr', title: '角色属性',templet: function(d){
                    if(d.roleAttr == 1){
                        return "私有";
                    }else if(d.roleAttr == 0){
                        return "共享";
                    }
                }}
                ,{field: 'roleStatus', title: '状态',templet: function(d){
                    if(d.roleStatus == 1){
                        return "启用";
                    }else if(d.roleStatus == 0){
                        return "禁用";
                    }
                }}
                ,{field: 'areaName', title: '所属地区'}
                ,{field: 'orgName', title: '所属机构'}
                ,{fixed: 'right',  align:'center', toolbar: '#barRolelist',width:130}
            ]]
        });


        //监听头工具栏事件
        table.on('toolbar(roletable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    layer.open({
                        title:"角色信息",
                        type: 2,
                        area: ['75%','90%'],
                        btn: ['保存', '取消'],
                        content: Common.ctxPath+'roleManage/role',
                        yes: function(index,layero){
                            // 获取iframe层的body
                            var body = layer.getChildFrame('body', index);
                            // 找到隐藏的提交按钮模拟点击提交
                            body.find('#permissionSubmit').click();
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
                        params.ids = ids;
                        Common.openConfirm("确定删除吗?",function () {
                            Common.ajax('roleManage/role',params,true,'DELETE',search);
                        });
                    }
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(roletable)', function(obj){
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'detail'){
            } else if(layEvent === 'del'){
                var params = {};
                params.ids = data.id;
                Common.openConfirm("确定删除吗?",function () {
                    Common.ajax('roleManage/role',params,true,'DELETE',search);
                })
            } else if(layEvent === 'edit'){
                layer.open({
                    title:"角色信息",
                    type: 2,
                    area: ['75%','90%'],
                    btn: ['修改', '取消'],
                    content: Common.ctxPath+'roleManage/role/'+data.id,
                    yes: function(index,layero){
                        // 获取iframe层的body
                        var body = layer.getChildFrame('body', index);
                        // 找到隐藏的提交按钮模拟点击提交
                        body.find('#permissionSubmit').click();

                    }
                });
            }
        });
        $("#chongzhi").click(function () {
            $("input").val("");
        });
    });
});
function search(){
    var roleNameObj = $('#roleName');
    var areaCodeObj = $('#areaCode');
    roleTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
        ,where: {
            roleName: roleNameObj.val(),
            areaCode: areaCodeObj.val()

        }
    });
}