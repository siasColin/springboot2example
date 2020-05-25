var articleTable;
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
function articleUpdateSuccess(){
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
        articleTable = table.render({
            id: 'articleTable'
            ,elem: '#articleList'
            ,height: "full-135"
            ,limit:10
            ,method:'GET'
            ,url: Common.ctxPath+'articleManage/articleListData/0' //数据接口
            ,parseData :parseDataFun
            ,title: '文章列表'
            ,page: true //开启分页
            ,toolbar: '#info_toolbar' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
            ,defaultToolbar: ['filter', 'print']
            ,totalRow: false //开启合计行
            ,cols: [[ //表头
                {type: 'checkbox', fixed: 'left'}
                ,{field: 'infoTitle', title: '标题'}
                ,{field: 'typeId', title: '分类',templet: function(d){
                        return d.articleType.typeName;
                }}
                ,{field: 'infoOpen', title: '是否公开',templet: function(d){
                    if(d.infoOpen == 1){
                        return '公开';
                    }else if(d.infoOpen == 0){
                        return '仅自己';
                    }else{
                        return '未知';
                    }
                }}
                ,{field: 'infoIscomment', title: '开启评论',templet: function(d){
                        if(d.infoOpen == 1){
                            return '开启';
                        }else if(d.infoOpen == 0){
                            return '关闭';
                        }else{
                            return '未知';
                        }
                    }}
                ,{field: 'infoAmount', title: '阅读量'}
                ,{fixed: 'right',  align:'center', toolbar: '#bararticleList',width:180}
            ]]
        });


        //监听头工具栏事件
        table.on('toolbar(articleTable)', function(obj){
            var checkStatus = table.checkStatus(obj.config.id)
                ,data = checkStatus.data; //获取选中的数据
            switch(obj.event){
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
                        Common.openConfirm("确定删除吗?",function () {
                            Common.ajax('articleManage/article/'+ids,null,true,'DELETE',search);
                        });
                    }
                    break;
            };
        });

        //监听行工具事件
        table.on('tool(articleTable)', function(obj){
            var data = obj.data //获得当前行数据
                ,layEvent = obj.event; //获得 lay-event 对应的值
            if(layEvent === 'details'){
                window.open(Common.ctxPath+'articleManage/articleView/'+data.id);
            } else if(layEvent === 'del'){
                Common.openConfirm("确定删除吗?",function () {
                    Common.ajax('articleManage/article/'+data.id,null,true,'DELETE',search);
                })
            }
        });
        $("#chongzhi").click(function () {
            layui.use(['form'], function(){
                var form = layui.form;
                $("#typeName").val("");
                form.render();
            });
        });
    });
});
function search(){
    var whereParams = {};
    var infoTitle = $('#infoTitle').val();
    if(!$.isEmpty(infoTitle)){
        whereParams.infoTitle=infoTitle;
    }
    articleTable.reload({
        page: {
            curr: 1 //重新从第 1 页开始
        }
        ,where: whereParams
    });
}