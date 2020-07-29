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
                        return '<span style="color: green">已启用</span>';
                    }else if(d.running == 0){
                        return '<span style="color: red">已禁用</span>';
                    }
                }}
                ,{field: 'exp1', title: '上次执行开始时间'}
                ,{field: 'exp2', title: '上次执行结束时间'}
                ,{field: 'exp3', title: '服务编码'}
                ,{fixed: 'right',  align:'center',title: '操作', toolbar: '#barQuartzlist',width:230}
            ]]
        });


        //监听头工具栏事件
        table.on('toolbar(quartzTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
                case 'add':
                    layer.open({
                        title:"新增任务",
                        type: 2,
                        area: ['60%','80%'],
                        btn: ['保存', '取消'],
                        content: Common.ctxPath+'quartzManage/quartz',
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
                            if(data[i].exp3 === data[i].exp4){ // 只能删除当前服务的任务
                                if(i == data.length - 1){
                                    ids+=data[i].id
                                }else{
                                    ids+=data[i].id+',';
                                }
                            }
                        }
                        var params = {};
                        params.ids = ids;
                        Common.openConfirm("确定删除吗?",function () {
                            Common.ajax('quartzManage/quartz',params,true,'DELETE',search);
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
                    Common.ajax('quartzManage/quartz',params,true,'DELETE',search);
                })
            } else if(layEvent === 'edit'){
                layer.open({
                    title:"修改任务",
                    type: 2,
                    area: ['60%','80%'],
                    btn: ['修改', '取消'],
                    content: Common.ctxPath+'quartzManage/quartz/'+data.id,
                    yes: function(index,layero){
                        // 获取iframe层的body
                        var body = layer.getChildFrame('body', index);
                        // 找到隐藏的提交按钮模拟点击提交
                        body.find('#permissionSubmit').click();

                    }
                });
            }else if(layEvent === 'changeRunning'){//启用或禁用任务
                Common.ajax('quartzManage/quartz/'+data.id,null,true,'PUT',changeRunningResult);
            }
        });
        table.on('edit(quartzTable)', function(obj){
            // console.log(obj.value);
            // console.log(obj.field);
            // console.log(obj.data);
            if(obj.data.exp3 === obj.data.exp4){
                var params = {};
                params.cron = obj.value;
                Common.ajax('quartzManage/quartzCron/'+obj.data.id,params,true,'PUT',updateCron);
            }else{
                Common.info('非当前服务的任务，编辑无效！')
                return false
            }
        });
        $("#chongzhi").click(function () {
            $("input").val("");
        });
    });
});
function search(){
    var quartzname = $('#quartzname');
    var running = $('#running');
    var exp3 = $('#exp3');
    roleTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
        ,where: {
            quartzname: quartzname.val(),
            exp3: exp3.val(),
            running: running.val()
        }
    });
}

function changeRunningResult(data){
    if(data == 1){
        Common.success("启用成功");
    }else if(data == 0){
        Common.success("禁用成功");
    }
    search();
}

function updateCron(){
    Common.success("修改成功");
    search();
}