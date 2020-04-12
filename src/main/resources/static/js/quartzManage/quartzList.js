var quartzTable;
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
 * 保存或者更新成功后回调
 */
function quartzSaveOrUpdateSuccess(){
    layui.use('layer', function() {
        var layer = layui.layer;
        layer.closeAll('iframe'); //关闭弹框
    });
    search();
}
$(function(){
    layui.use([ 'layer', 'table','form'], function(){
        var layer = layui.layer //弹层
            ,table = layui.table //表格
            ,form = layui.form //表格

        //执行一个 table 实例
        roleTable = table.render({
            id: 'quartzTable'
            ,elem: '#quartzist'
            ,height: "full-135"
            ,limit:10
            ,method:'GET'
            ,url: Common.ctxPath+'quartzManage/quartzList' //数据接口
            ,parseData :parseDataFun
            ,title: '定时任务列表'
            ,page: true //开启分页
            ,toolbar: '#info_toolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            ,defaultToolbar: ['filter', 'print']
            ,totalRow: false //开启合计行
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'quartzname', title: '任务名称'}
                ,{field: 'cron', title: '执行时间',edit: 'text'}
                ,{field: 'clazzname', title: '任务类名'}
                ,{field: 'running', title: '任务状态',templet: function(d){
                    if(d.running == 1){
                        return "启用";
                    }else if(d.running == 0){
                        return "禁用";
                    }
                }}
                ,{fixed: 'right',  align:'center', toolbar: '#barRolelist',width:130}
            ]]
        });


        //监听头工具栏事件
        table.on('toolbar(quartzTable)', function(obj){
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
        table.on('tool(quartzTable)', function(obj){
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
    var quartzname = $('#quartzname');
    roleTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
        ,where: {
            quartzname: quartzname.val()
        }
    });
}