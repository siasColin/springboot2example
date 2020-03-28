/*一些基础的js方法，基础的业务js方法在lib.js里*/
//日期格式化
Date.prototype.format=function(format){var d=this,o={"M+":d.getMonth()+1,"d+":d.getDate(),"H+":d.getHours(),"m+":d.getMinutes(),"s+":d.getSeconds(),w:["日","一","二","三","四","五","六"][d.getDay()]};if(/(y+)/.test(format)){format=format.replace(RegExp.$1,(d.getFullYear()+"").substr(4-RegExp.$1.length))}for(var k in o){if(new RegExp("("+k+")").test(format)){format=format.replace(RegExp.$1,RegExp.$1.length==1?o[k]:("00"+o[k]).substr((""+o[k]).length))}}return format};
var _header = $("meta[name='_csrf_header']").attr("content");
var _token =$("meta[name='_csrf']").attr("content");
var Common = {
    //当前项目名称
    ctxPath: "/",
    version:"",
    //会话超时状态码
    sessionInvalidCode:"888",
    sessionInvalidURL:"common/sessionInvalid",
    log: function (info) {
        console.log(info);
    },
    msg: function (info, iconIndex) {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.msg(info, {
                icon: iconIndex
            });
        });
    },
    alert: function (info, iconIndex) {
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.alert(info, {
                icon: iconIndex,
                skin: 'layui-layer-molv'
            });
        });
    },
    load: function(){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.load();
        });
    },
    closeload: function(){
        layui.use('layer', function() {
            var layer = layui.layer;
            layer.closeAll('loading');
        });
    },
    info: function (info) {
        Common.msg(info, 0);
    },
    success: function (info) {
        Common.msg(info, 1);
    },
    error: function (info) {
    		Common.openConfirm(info)
    },
    ajax: function (url, paras,sy,tp,next) {
    		$.ajax({
                async:sy,
    			url:Common.ctxPath+url,
    			type:tp,
    			data:paras,
                dataType: "json",
                beforeSend : function(xhr) {
                    xhr.setRequestHeader(_header, _token);
                },
    			success:function(rsp){
    				if(rsp.returnCode == '0'){
                        //成功
                        if(next!=null){
                            next(rsp.data);
                        }else{
                            Common.success(rsp.returnMessage);
                        }
    				}else if(rsp.returnCode == Common.sessionInvalidCode){//会话超时
                        Common.openConfirm(rsp.returnMessage,function () {
                            window.location.replace(Common.ctxPath+Common.sessionInvalidURL);
                        });
                    }else if(rsp.returnCode == '006'){//当前登录用户关联信息已更新，提示用户重新登录
                        layui.use(['layer'], function(){
                            var layer = layui.layer
                            var logoutTipIndex = layer.confirm(rsp.returnMessage, {
                                btn: ['确定'], //按钮
                                shade:0.5
                            }, function(){
                                layer.close(logoutTipIndex);
                                $("#logout",parent.document).submit();
                            });
                        });
                    }else{
                        Common.error(rsp.returnMessage);
    				}
    			},
    			error:function(rsp){
    				Common.error(rsp.returnMessage);
    			}
    		})

    },
    getOneFromTable:function(layuiTable,tableId){
    		var checkStatus = layuiTable.checkStatus(tableId)
        ,data = checkStatus.data;
        if(data.length==0){
        		Common.info("请选中一条记录");
        }else if(data.length>1){
        		Common.info("只能选中一条记录")
        }else{
        		return  data[0];
        }
    },
    getMoreDataFromTable:function(layuiTable,tableId){
		var checkStatus = layuiTable.checkStatus(tableId)
	    ,data = checkStatus.data;
	    if(data.length==0){
	    		Common.info("请选中记录");
	    }else{
	    		return  data;
	    }
    },
    openDlg:function(url,title){
    		var index = layer.open({
            type: 2,
            content: Common.ctxPath+url,
            title: title,
            maxmin: false
        });
		layer.full(index);
    },
    topOpenDlg:function(url,title){
                   		var index = top.layer.open({
                           type: 2,
                           content: Common.ctxPath+url,
                           title: title,
                           area:['100%', '100%'],
                           maxmin: false
                       });
               		layer.full(index);
                   },
    openConfirm:function(content,callback,callBackNo){
        layui.use('layer', function() {
            var index = layer.confirm(content, {
                btn: ['确认', '取消'] //按钮
            }, function () {
                if (callback != null) {
                    callback();
                }
                layer.close(index);
            }, function () {
                if (callBackNo != null) {
                    callBackNo()
                }
                layer.close(index);
            });
        });

    },
    openPrompt:function(title,defaultValue,callback){
    		layer.prompt({title: title, formType: 0,value:defaultValue}, function(value, index,elem){
    		  layer.close(index);
    		  callback(value);
    		});
    },
    getDate:function(date,pattern){
    		if(date==null||date==''){
    			return "";
    		}else{
    			if(pattern){
    				return new Date(date).format(pattern);
    			}else{
    				return date.substring(0,10);
    			}

    		}
    },
    getUrlParam:function (name) {
        var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)"); //构造一个含有目标参数的正则表达式对象
        var r = window.location.search.substr(1).match(reg);  //匹配目标参数
        if (r != null) return unescape(r[2]);
        return null; //返回参数值
    },
    getCheckedNodesWithOutParent : function (zTree){
        var selectedNodes = "";
        var nodes = zTree.getCheckedNodes(true);
        for (var i=0, l=nodes.length; i<l; i++) {
            if(nodes[i].isParent==false&&nodes[i].halfCheck==false){//过滤掉父节点和半选节点
                selectedNodes += nodes[i].id + ",";
            }
        }
        if(selectedNodes.length > 0){
            selectedNodes = selectedNodes.substring(0,selectedNodes.length-1);
        }
        return selectedNodes;
    },
    getCheckedNodes : function (zTree){
        var selectedNodes = "";
        var nodes = zTree.getCheckedNodes(true);
        for (var i=0, l=nodes.length; i<l; i++) {
            selectedNodes += nodes[i].id + ",";
        }
        if(selectedNodes.length > 0){
            selectedNodes = selectedNodes.substring(0,selectedNodes.length-1);
        }
        return selectedNodes;
    }

};



// JQuery方法定制
(function($){
  //例如$.isEmpty("")
  $.extend({
  	//非空判断
  	isEmpty: function(value) {
  		if (value === null || value == undefined || value === '') { 
  			return true;
  		}
  		return false;
    },
    //获取对象指
    result: function(object, path, defaultValue) {
    	var value = "";
  		if(!$.isEmpty(object) && $.isObject(object) && !$.isEmpty(path)){
  			var paths = path.split('.');
  			var length = paths.length;
  			$.each(paths,function(i,v){
  				object = object[v];
  				if(length-1 == i){
						value = object;
					}
  				if(!$.isObject(object)){
  					return false;
  				}
  			})
  			
  		}
  		
  		if($.isEmpty(value) && !$.isEmpty(defaultValue)){
  			value = defaultValue;
  		}
  		return value;
    },
    //判断是否obj对象
    isObject : function(value) {
      var type = typeof value;
      return value != null && (type == 'object' || type == 'function');
    },
    //是否以某个字符开头
    startsWith : function(value,target){
    	return value.indexOf(target) == 0;
    },
    //设置sessionStorage
    setSessionStorage:function(key, data){
    	sessionStorage.setItem(key, data);
    },
    //获取sessionStorage
    getSessionStorage:function(key){
    	return sessionStorage.getItem(key) == null ? "" : sessionStorage.getItem(key);
    },
    //删除sessionStorage
    removeSessionStorage:function(key){
    	sessionStorage.removeItem(key);
    },
    //清除sessionStorage
    clearSessionStorage:function(){
    	sessionStorage.clear();
    },
    uuid : function(){
  		return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
		    var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
		    return v.toString(16);
  		});
    }
  });

}(jQuery));
