var _header = $("meta[name='_csrf_header']").attr("content");
var _token = $("meta[name='_csrf']").attr("content");
$(function () {
    $('.date-dz-z').on('click', function () {
        var obj = $(this);
        var zNum = obj.find('.z-num').html();
        if (obj.is('.date-dz-z-click')) {
            zNum--;
            obj.removeClass('date-dz-z-click red');
            obj.find('.z-num').html(zNum);
            obj.find('.date-dz-z-click-red').removeClass('red');
        } else {
            zNum++;
            obj.addClass('date-dz-z-click');
            obj.find('.z-num').html(zNum);
            obj.find('.date-dz-z-click-red').addClass('red');
        }
    });
})

function reply1(obj, keyid, touserid) {
    //回复 淡影淡出（第一个）
    $(obj).parents(".time_").find(".tosub_metext").attr("keyid", keyid);
    $(obj).parents(".time_").find(".tosub_metext").attr("uid", touserid);
    if ($(obj).nextAll(".tosub_back").is(":hidden")) {
        $(obj).nextAll(".tosub_back").fadeIn(200);
        $(obj).next().next().next().show();
    }
    $(obj).parents(".time_").find(".tosub_metext").val('');
    $(obj).parents(".time_").find(".tosub_metext").attr({"placeholder": ""});
    $(obj).parents(".time_").find(".tosub_metext").focus();
}

/**
 * 点击收起时间
 * @param obj
 */
function retract(obj) {
    $(obj).nextAll(".tosub_back").fadeOut(200);
    $(obj).hide();
}

function reply2(obj, keyid, touserid) {
    //点击 回复按钮    （第二个）
    $(obj).parents(".tosub_back").find(".tosub_metext").attr("keyid", keyid);
    $(obj).parents(".tosub_back").find(".tosub_metext").attr("uid", touserid);
    $(obj).parents(".tosub_back").find(".tosub_metext").focus();
    var sub_user = $(obj).parents(".tosub_text").find(".sub_user").html();
    var sub_userid = $(obj).parents(".tosub_text").find(".sub_userid").html();
    $(obj).parents(".tosub_back").find(".tosub_metext").attr({"placeholder": "回复 " + sub_user});

}

function publish_content() {
    var sysUserId = $("#sysUserId").val();
    if ($.isEmpty(sysUserId)) {
        alert("请先登录！");
        return false;
    }
    var content = $("#container").val();
    if (content == null || "" == content) {
        alert("请输入内容!");
        return false;
    }
    var userId = $("#articleUserId").val();
    var articleInfoId = $("#articleId").val();
    $.ajax({
        data: {"infoId": articleInfoId, "commentContent": content, "toUserId": userId, "parentId": "-1"},
        url: Common.ctxPath + "articleManage/comment",
        type: "POST",
        cache: false,
        dataType: "json",
        beforeSend: function (xhr) {
            if (!$.isEmpty(_header) && !$.isEmpty(_token)) {
                xhr.setRequestHeader(_header, _token);
            }
        },
        error: function () {
            alert("加载数据出错...");
            return false;
        },
        success: function (rsp) {
            if (rsp.returnCode == '0') {
                $("#container").val('');
                dataP(rsp.data);
            } else {
                alert('操作失败!');
            }
        }
    });

}

function dataP(data) {
    var userId = $("#sysUserId").val();
    var articleUserId = $("#articleUserId").val();
    var divHtml = "";
    for (var i = 0; i < data.length; i++) {
        divHtml += '<div class="sub_back sub_back">' +
            '<div class="left_user"><img id="avatar" src="' + Common.ctxPath + data[i].fromUser.headImg + '" style="border-radius: 50%;width: 50px;height: 50px;margin-top: 5px;cursor: pointer;"></div>' +
            '<div class="right_usertext">' +
            '<div class="txt_ input_border"><span class="comment-size-name">' + data[i].fromUser.userName + '：</span><span class="my-pl-con">' + data[i].commentContent + '</span></div>' +
            '<div class="time_">';
        if (userId == data[i].fromUserId || userId == data[i].toUserId) {
            divHtml += '<span style="cursor: pointer;" onclick="del(\'' + data[i].id + '\')">删除</span>';
            divHtml += '<span class="date-dz-line">|</span>';
        }
        divHtml += '<span class="span_03 callback_ " style="cursor: pointer;" onclick="reply1(this,\'' + data[i].id + '\',\'' + data[i].fromUserId + '\');">回复</span>' +
            '<span class="span_04 " id="span"' + data[i].id + ' >(' + (data[i].childList.length) + ')</span>' +
            '<span class="span_02">' + data[i].commentTime + '</span>' +
            '<span class="date-dz-line" style="cursor: pointer;display:none" onclick="retract(this);">收起</span>' +
            '<div class="tosub_back ">' +
            '<div class="tosub_me" style="position: relative">';
        var reply = "";
        for (var j = 0; j < data[i].childList.length; j++) {
            reply += '<div class="tosub_text">' +
                '<p class="tosub_txt"><span class="sub_user">' + data[i].childList[j].fromUser.userName + '：</span>回复<span class="sub_user" >@' + data[i].childList[j].toUser.userName + ':</span> ' + data[i].childList[j].commentContent + '</p>' +
                ' <div class="sub_time"><span class="span_02" >' + data[i].childList[j].commentTime + '</span><span class="sub_callback" onclick="reply2(this,\'' + data[i].childList[j].id + '\',\'' + data[i].childList[j].fromUserId + '\');">回复</span>';
            if (userId == data[i].childList[j].fromUserId || userId == articleUserId) {
                reply += '<span class="delec" onclick="delChild(\'' + data[i].childList[j].id + '\',\'' + data[i].id + '\',this,\'' + data[i].fromUserId + '\');">|删除</span>';
            }
            reply += '</div></div>';
        }
        divHtml = divHtml + reply +
            '<textarea class="tosub_metext" keyid="' + data[i].id + '" uid="' + data[i].fromUserId + '" name="" id="" cols="30" rows="10" placeholder=""></textarea>' +
            ' <div style="cursor: pointer;" class="sub_btn sub_publish" onclick="publish(this,\'' + data[i].fromUserId + '\',\'' + data[i].id + '\');">回复</div>' +
            '    </div>' +
            ' </div>' +
            '  </div>' +
            '   </div>' +
            '   </div>';
    }
    $("#divHtml").html(divHtml);
}

function publish(obj, rootuid, rootkid) {
    var sysUserId = $("#sysUserId").val();
    if ($.isEmpty(sysUserId)) {
        alert("请先登录！");
        return false;
    }
    //点击 发表按钮    （第二个）
    var kid = $(obj).parents(".tosub_back").find(".tosub_metext").attr("keyid");
    var uid = $(obj).parents(".tosub_back").find(".tosub_metext").attr("uid");
    var articleInfoId = $("#articleId").val();
    var sub_publish_ = $(obj).siblings(".tosub_metext").val();
    if (sub_publish_ == null || sub_publish_ == "") {
        alert("请输入内容!");
        return false;
    }
    $.ajax({
        data: {
            "infoId": articleInfoId,
            "parentId": kid,
            "toUserId": uid,
            "commentContent": sub_publish_,
            "parentId": rootkid
        },
        url: Common.ctxPath + "articleManage/comment",
        type: "POST",
        cache: false,
        dataType: "json",
        beforeSend: function (xhr) {
            if (!$.isEmpty(_header) && !$.isEmpty(_token)) {
                xhr.setRequestHeader(_header, _token);
            }
        },
        error: function () {
            alert('加载数据出错...');
            return false;
        },

        success: function (rsp) {
            if (rsp.returnCode == '0') {
                childDataP(obj, rsp.data, kid, rootuid, rootkid);
            } else {
                alert("操作失败!");
            }
        }
    });

    $(obj).parents(".tosub_back").find(".tosub_metext").val('');
    $(obj).parents(".tosub_back").find(".tosub_metext").attr({"placeholder": ""});

}

function childDataP(obj, data, pid, uid, rootkid) {
    var userId = $("#sysUserId").val();
    var articleUserId = $("#articleUserId").val();
    $("#span" + rootkid).text("(" + data.length + ")");
    var reply = "";
    for (var j = 0; j < data.length; j++) {
        reply += '<div class="tosub_text">' +
            '<p class="tosub_txt"><span class="sub_user">' + data[j].fromUser.userName + '：</span>回复<span class="sub_user" >@' + data[j].toUser.userName + ':</span> ' + data[j].commentContent + '</p>' +
            ' <div class="sub_time"><span class="span_02" >' + data[j].commentTime + '</span><span class="sub_callback" onclick="reply2(this,\'' + data[j].id + '\',\'' + data[j].fromUserId + '\');">回复</span>';
        if (userId == data[j].fromUserId || userId == articleUserId) {
            reply += '<span class="delec" onclick="delChild(\'' + data[j].id + '\',\'' + rootkid + '\',this,\'' + uid + '\');">|删除</span>';
        }

        reply += '</div> </div>';
    }
    reply = reply +
        //'<p style="cursor: pointer;" onclick="init(this,'+rootkid+','+uid+');">我也说一句</p>'+
        '<textarea class="tosub_metext" keyid="' + rootkid + '" uid="' + uid + '" name="" id="" cols="30" rows="10" placeholder=""></textarea>' +
        ' <div style="cursor: pointer;" class="sub_btn sub_publish" onclick="publish(this,\'' + uid + '\',\'' + rootkid + '\');">回复</div>' +
        '    </div>' +
        ' </div>' +
        '  </div>' +
        '   </div>' +
        '   </div>';
    $(obj).parents(".tosub_me").html(reply);
}

function delChild(keyid, pid, obj, uid) {

    $.ajax({
        url: Common.ctxPath + "articleManage/comment/" + keyid + "/" + pid,
        type: "DELETE",
        cache: false,
        dataType: "json",
        beforeSend: function (xhr) {
            if (!$.isEmpty(_header) && !$.isEmpty(_token)) {
                xhr.setRequestHeader(_header, _token);
            }
        },
        error: function () {
            alert('加载数据出错...');
            return false;
        },
        success: function (rsp) {
            if (rsp.returnCode == '0') {
                childDataP(obj, rsp.data, pid, uid, pid);
                alert('删除成功!');

            } else {
                alert('操作失败!');
            }
        }
    });

}

function del(keyid) {
    $.ajax({
        url: Common.ctxPath + "articleManage/comment/" + keyid + "/-1",
        type: "DELETE",
        cache: false,
        dataType: "json",
        beforeSend: function (xhr) {
            if (!$.isEmpty(_header) && !$.isEmpty(_token)) {
                xhr.setRequestHeader(_header, _token);
            }
        },
        error: function () {
            alert('加载数据出错...');
            return false;
        },

        success: function (rsp) {
            if (rsp.returnCode == '0') {
                dataP(rsp.data);
                alert('删除成功!');

            } else {
                alert('操作失败!');
            }
        }
    });
}

layui.use(['flow', 'layer'], function () {
    var flow = layui.flow
        , layer = layui.layer;
    flow.load({
        elem: '#divHtml' //流加载容器
        , end: '<li class="layim-msgbox-tips">暂无更多新消息</li>'
        , done: function (page, next) { //执行下一页的回调
            renderComment(page, function (res) {
                var html = [];
                if (res != null && res.data.length > 0) {
                    for (var i = 0; i < res.data.length; i++) {
                        var comment = res.data[i];
                        html.push('<li><a href="javascript:void(0)">');
                        html.push('<img src="' + Common.ctxPath + comment.fromUser.headImg + '" class="layui-circle layim-msgbox-avatar">');
                        html.push('</a>');
                        html.push('<p class="layim-msgbox-user"><a href="javascript:void(0)">' + comment.fromUser.userName + '</a><em style="padding-left: 5px">回复</em><span onclick="openArticle(\'' + comment.infoId + '\')" style="cursor: pointer;">' + comment.articleInfo.infoTitle + '</span></p>');
                        html.push('<p class="layim-msgbox-content">' + comment.commentContent + '</p><span style="color: #999;display: block;text-align: right;">' + comment.commentTime + '</span>');
                        html.push('</li>');
                    }
                }
                next(html.join(''), page < res.total);
            });
        }
    });
});