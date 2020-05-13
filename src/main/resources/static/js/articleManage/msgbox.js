function openArticle(id){
    window.open(Common.ctxPath+'articleManage/articleView/'+id);
}

layui.use(['flow','layer'], function() {
    var flow = layui.flow
        ,layer = layui.layer;
    //请求消息
    var renderMsg = function(page, callback){
        $.get(Common.ctxPath+'articleManage/msgboxdata', {
            page: page || 1
        }, function(res){
            if(res.returnCode != '0'){
                return layer.msg(res.returnMessage);
            }
            callback && callback(res);
        });
    };
    flow.load({
        elem: '#msgbox_view' //流加载容器
        ,end: '<li class="layim-msgbox-tips">暂无更多新消息</li>'
        , done: function (page, next) { //执行下一页的回调
            renderMsg(page, function(res){
                var html = [];
                if(res != null && res.data.length > 0){
                    for (var i = 0;i<res.data.length;i++) {
                        var comment = res.data[i];
                        html.push('<li><a href="javascript:void(0)">');
                        html.push('<img src="'+Common.ctxPath+comment.fromUser.headImg+'" class="layui-circle layim-msgbox-avatar">');
                        html.push('</a>');
                        html.push('<p class="layim-msgbox-user"><a href="javascript:void(0)">'+comment.fromUser.userName+'</a><em style="padding-left: 5px">回复</em><span onclick="openArticle(\''+comment.infoId+'\')" style="cursor: pointer;">'+comment.articleInfo.infoTitle+'</span></p>');
                        html.push('<p class="layim-msgbox-content">'+comment.commentContent+'</p><span style="color: #999;display: block;text-align: right;">'+comment.commentTime+'</span>');
                        html.push('</li>');
                    }
                }
                next(html.join(''), page < res.total);
            });
        }
    });
});