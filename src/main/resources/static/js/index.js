var _header = $("meta[name='_csrf_header']").attr("content");
var _token = $("meta[name='_csrf']").attr("content");

/**
 * 加载一级菜单下的子菜单
 * @param 以及菜单id
 * @param url 一级菜单对应的url（如果存在）
 * @param nav 打开位置navTab（系统内打开）、_blank(新窗口打开) ,默认（navTab）
 */
function showSecondMenu(moduleId, url, nav) {
    if ($.isEmpty(url)) {
        url = '';
    }
    if ($.isEmpty(nav)) {
        nav = 'navTab';
    }
    if (url.substr(0, 4) != "http") {//http开头为外部链接
        url = Common.ctxPath + url;
    }
    if (url != Common.ctxPath) {//如果菜单url和项目地址不相等，说明该以及菜单是功能点，需要打开
        $("#sidebar").hide();
        $("#sidebar").addClass('menu-hide');
        if (nav == '_blank') {//新窗口打开
            window.open(url);
        } else {
            $("#rightframe").attr("src", url);
        }
    } else {//加载二级菜单
        $("#sidebar").show();
        $("#sidebar").removeClass('menu-hide');
        loadSecondMenu(moduleId);
    }
}

/**
 * 加载子菜单 默认样式
 * @param moduleId
 */
function loadSecondMenu(moduleId) {
    $.ajax({
        async: false,
        type: "GET",
        url: Common.ctxPath + 'childMenu/' + moduleId,
        data: {"moudleId": moduleId},
        dataType: "json",
        beforeSend: function (xhr) {
            if (!$.isEmpty(_header) && !$.isEmpty(_token)) {
                xhr.setRequestHeader(_header, _token);
            }
        },
        success: function (rsp) {
            setSecondMenu(rsp.data);
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            // 通过XMLHttpRequest取得响应头，sessionstatus，
            if (sessionstatus == "sessionTimeOut") {
                window.location.replace("/");
            } else {
                alert("请求异常");
            }
        }
    });
}

function setSecondMenu(data) {
    var html = "";
    var secondMenu = data.allSecondMenu;
    for (var i = 0; i < secondMenu.length; i++) {
        var moduleId = secondMenu[i].id;
        //菜单打开位置
        var moduleTarget = secondMenu[i].moduleTarget;
        //菜单样式
        var moduleIcon = secondMenu[i].moduleIcon;
        var secondclass = 'icon-reorder';
        if (!$.isEmpty(moduleIcon)) {
            secondclass = moduleIcon;
        }
        //得到二级菜单对应的三级菜单
        var thirdMenu = data["secondMenu_" + moduleId];

        var secondmenuurl = secondMenu[i].moduleUrl;

        //判断如果不是以http开头,说明是系统内菜单，加上项目路径
        if (!(secondmenuurl.indexOf("http") != 0 || secondmenuurl.indexOf("https") != 0)) {
            secondmenuurl = Common.ctxPath + secondmenuurl;
        }
        //如果有三级菜单，拼接三级菜单
        if (!$.isEmpty(thirdMenu) && thirdMenu.length > 0) {
            secondmenuurl = '';
        }
        if (i == 0) {//默认打开并选中第一个菜单
            html += '<li class="active open"><a onclick="setIframeSrc(\'' + secondmenuurl + '\',2,this,\'' + moduleTarget + '\')" href="javascript:void(0);" class="dropdown-toggle"><i class="' + secondclass + '"></i><span class="menu-text"> ' + secondMenu[i].moduleName + ' </span><b class="arrow icon-angle-down"></b></a>';
        } else {
            html += '<li><a onclick="setIframeSrc(\'' + secondmenuurl + '\',2,this,\'' + moduleTarget + '\')" href="javascript:void(0);" class="dropdown-toggle"><i class="' + secondclass + '"></i><span class="menu-text"> ' + secondMenu[i].moduleName + ' </span><b class="arrow icon-angle-down"></b></a>';
        }
        if (!$.isEmpty(thirdMenu) && thirdMenu.length > 0) {
            html += '<ul class="submenu">';
            for (var j = 0; j < thirdMenu.length; j++) {
                var urlPa = thirdMenu[j].moduleUrl;
                if (!(urlPa.indexOf("http") != 0 || urlPa.indexOf("https") != 0)) {
                    urlPa = Common.ctxPath + urlPa;
                }
                html += '<li onclick="setIframeSrc(\'' + urlPa + '\',3,this,\'' + moduleTarget + '\')"><a href="javascript:void(0);"><i class="icon-double-angle-right"></i>' + thirdMenu[j].moduleName + '</a></li>';
            }
            html += '</ul>';
        }
        html += '</li>';
    }
    $("#secondmenu").html(html);
    if ($("#secondmenu li").first().find("ul").length > 0) {//有三级菜单
        $("#secondmenu li").first().find("ul li:first").click();
    } else {
        $("#secondmenu li").first().find("a:first").click();
    }
}

function setIframeSrc(url, menulevel, obj, moduleTarget) {
    if (url != '') {
        if (moduleTarget == '_blank') {//新窗口打开
            window.open(url);
        } else {
            $("#rightframe").attr("src", url);
        }
    }
    if (menulevel == 2) {
        $(obj).parent().siblings().removeClass('active open');
        $(obj).parent().addClass('active');
        $($(obj).next().get(0)).slideDown(200).parent().toggleClass("open");
        $(".submenu").each(function () {
            if (this != $(obj).next().get(0)) {
                $(this).slideUp(200);
                $(this).children().removeClass("active");
            }
        });
        if (!$(obj).parent().hasClass("open")) {
            $($(obj).next().get(0)).slideUp(200);
        }
        if ($(obj).parent().find("ul").length > 0) {//有三级菜单
            $(obj).parent().find("ul li:first").click();
        }
    }
    if (menulevel == 3) {
        if (!$(obj).parent().parent().hasClass('active')) {
            $(obj).parent().prev().click();
        }
        $(obj).siblings().removeClass('active');
        $(obj).addClass("active");
    }
}

function showMsgBox() {
    layer.open({
        id: "msgbox",
        type: 2,
        title: '消息中心',
        shadeClose: true,
        shade: false,
        maxmin: true, //开启最大化最小化按钮
        area: ['893px', '600px'],
        content: Common.ctxPath + 'articleManage/msgbox'
    });
    $("#msgcount").text('');
}

function msgBoxCount() {
    Common.ajax("articleManage/msgboxcount", null, true, "GET", function (data) {
        if (data.count > 0) {
            var count = data.count > 99 ? '99+' : data.count + '';
            $("#msgcount").text(count);
        }

    })
}

$(function () {
    msgBoxCount();
    layui.use('upload', function () {
        var upload = layui.upload;
        //上传头像
        var headImgUploadInst = upload.render({
            elem: '#headImg'
            , url: Common.ctxPath + 'common/uploadSingle'
            , accept: 'images' //普通文件
            , acceptMime: 'image/*'
            , field: 'file'
            , data: {"prefixPath": 'headImg'}
            , before: function () {
                Common.load();
            }
            , done: function (res) {
                Common.closeload();
                if (res.returnCode == '0') {//成功
                    $("#headImg").attr("src", Common.ctxPath + res.data.fileUrl);
                    var headImg = res.data.fileUrl;
                    var params = {};
                    params.headImg = headImg;
                    //更新数据库
                    Common.ajax('userManage/userHeadImg', params, true, 'PUT', null);
                } else {
                    Common.info(res.returnMessage);
                }
            }
            , error: function () {
                Common.closeload();
                Common.info("请求异常");
            }
        });
    });
})