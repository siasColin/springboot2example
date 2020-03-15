var _header = $("meta[name='_csrf_header']").attr("content");
var _token =$("meta[name='_csrf']").attr("content");
var treeObj;
var setting = {
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
    callback: {
        // onClick: areaNodeOnClick
    }
};

/**
 * 弹出层中的地区树点击时触发，主要用于给页面元素赋值
 */
function iframeAreaNodeClick(treeNode){
    document.getElementById('areaCode').value=treeNode.id;
    document.getElementById('areaName').value=treeNode.name;
}

/**
 * 弹出层中的机构树点击时触发，主要用于给页面元素赋值
 * @param treeNode
 */
function iframeOrgNodeClick(treeNode){
    document.getElementById('parentCode').value=treeNode.id;
    document.getElementById('parentName').value=treeNode.name;
}

//初始化页面
$(function(){
    loadOrgTree();

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
                content: Common.ctxPath+'areaManage/areatree/none'
            });
        });
    });

    $("#parentName").on("click",function(){
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
});


/**
 * 加载地区树
 */
function loadOrgTree(){
    var param = {};
    $.ajax({
        async:true,
        type: "GET",
        url: Common.ctxPath+'orgManage/orgListTree',
        data:param,
        dataType: "json",
        beforeSend : function(xhr) {
            xhr.setRequestHeader(_header, _token);
        },
        success: function(rsp){
            if(rsp.returnCode == '0'){
                treeObj = $.fn.zTree.init($("#org_tree"), setting, rsp.data);
                expandRoot();
            }else{
                Common.error(rsp.returnMessage);
            }
        },
        error: function (XMLHttpRequest, textStatus, errorThrown) {
            var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus");
            // 通过XMLHttpRequest取得响应头，sessionstatus，
            if(sessionstatus == "sessionTimeOut"){
                window.location.replace("/");
            }else {
                Common.error("请求异常")
            }
        }
    });
}


function expandRoot(){
    var nodes = treeObj.getNodes();
    if (nodes.length>0) {
        for(var i=0;i<nodes.length;i++){
            treeObj.expandNode(nodes[i], true, false, false);//默认展开第一级节点
        }
    }
}

layui.use(['form','upload','layer'], function(){
    var form = layui.form
    ,layer = layui.layer
    ,upload = layui.upload;



    //上传
    var uploadInst = upload.render({
        elem: '#orgLogoPlugin'
        ,url: Common.ctxPath+'common/uploadSingle'
        ,accept: 'images' //普通文件
        ,acceptMime: 'image/*'
        ,field:'file'
        ,data: {"prefixPath": 'orgLogo'}
        ,before: function () {
            Common.load();
        }
        ,done: function(res){
            Common.closeload();
            if(res.returnCode == '0'){//成功
                $("#orglogoImg").attr("src",Common.ctxPath+res.data.fileUrl);
                $("#orgLogo").val(res.data.fileUrl);
                Common.success("上传成功");
            }else{
                Common.info(res.returnMessage);
            }
        }
        ,error: function(){
            Common.closeload();
            Common.info("请求异常");
        }
    });
});