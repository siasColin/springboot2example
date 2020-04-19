/**
 * lsz
 * 通用性自定义LayUI表单验证
 * 必须引用layui.js,jquery-2.1.4.min.js
 * <%=basePath%>resources/js/utils/layuiFromVerify.js
 *
 */
layui.use(['form' ], function(){
        var form = layui.form
            ,layer = layui.layer
    form.verify({
        //必填项为空校验
        required: function(value,item){
            if(value == null || value.trim().length<=0){
                return '必填项不能为空！';
            }
        }
         /**
         * 字段唯一值校验 参数：id判断是新增还是修改,code校验字段, tableName需校验的数据库表名,val校验值
         * 1.form必须命名为：form1;
         * 2.form添加check="数据库表名"属性
         * 3.需由input id="id" type=hidden隐藏域存储id值
         * 4.校验字段 id=“数据库对应字段名”
         */
        ,repeatCheck: function(value,item){
            if(value.length<=0){
                return '字段不能为空！';
            }
            var id = $("#id").val();
            var val = value.toString().trim();
            var code= $(item).attr("tableColumn");
            var tableName=$("#form1").attr("check");
            if(tableName != null){
                var flag = true;
                $.ajax({
                    type : 'GET',
                    async:false,
                    url : Common.ctxPath+'common/fromVerifyCode',
                    data :{"id":id,"val":val,"tableName":tableName,"code":code},
                    dataType : 'json',
                    success : function(data) {
                        if(data.returnCode != '0'){
                            flag =false;
                        }
                    },
                    error : function(data) {
                    }
                });

                if(!flag){
                    return "已经存在,请重新填写!";
                }
            }
        }

        ,codeIsNot: function(value){
            var rootPath =getRootPath();
            var id = $("#id").val();
            var val = value;
            var tableName=$("#form1").attr("check");
            if(tableName != null){
                var flag = true;
                $.ajax({
                    type : 'POST',
                    async:false,
                    url : rootPath+'/common/codeIsNot',
                    data :{"id":id,"val":val},
                    dataType : 'json',
                    success : function(data) {
                        if(data.code != 0){
                            flag =false;
                        }
                    },
                    error : function(data) {
                    }
                });
                if(!flag){
                    return "您填写的行政区划编码有误，请及时校验！！";
                }
            }
        }

        ,lon: function(value, item){
            var lon = value;
            if(lon<0 || lon >180 || lon==''){
                return "经度填写超出范围！！";
            }
        }


        ,lat: function(value, item){
            var lat = value;
            if(lat<0 || lat >90 || lat==''){
                return "纬度填写超出范围！！";
            }
        }

        ,deleteNot: function(value, item){
            var rootPath =getRootPath();
            var id = $("#id").val();
            var code= item.id;
            var val = value;
            var tableName=$("#form1").attr("check");
            if(tableName != null){
                var flag = true;
                $.ajax({
                    type : 'POST',
                    async:false,
                    url : rootPath+'/common/deleteNot',
                    data :{"id":id,"val":val, "code":code},
                    dataType : 'json',
                    success : function(data) {
                        if(data.code != 0){
                            flag =false;
                        }
                    },
                    error : function(data) {
                    }
                });
                if(!flag){
                    return "字段值已经存在,请重新填写!";
                }
            }
        }


        //坐标验证
        ,coordinate: function(value){

            //校验坐标,整数位最多4位,小数位最多6位的数字
            var regu = /^(\-|\+)?\d{1,4}(\.\d{1,6})?$/;
            if (value.length>0) {
                if(regu.test(value)){
                }else{
                    return '格式不正确,整数最多4位,小数最多6位！';
                }
            }
        }
        //浮点小数验证
        ,decimal: function(value){
            var regu = /^(\+)?\d{1,8}(\.\d{1,2})?$/;
            if (value.length>0) {
                if (!regu.test(value)) {
                    return '格式不正确,请填写非负整数最多8位，小数最多2位！';
                }
            }
        }
        //浮点小数验证
        ,decimals: function(value){
            var regu = /^(\+)?\d{1,4}(\.\d{1,6})?$/;
            if (value.length>0) {
                if (!regu.test(value)) {
                    return '格式不正确,填写的整数最多4位，小数最多6位！';
                }
            }
        }
      //浮点小数验证
        ,decimaff: function(value){
            var regu = /^(\+)?\d{1,10}(\.\d{1,4})?$/;
            if (value.length>0) {
                if (!regu.test(value)) {
                    return '格式不正确,请填写非负整数最多10位，小数最多4位！';
                }
            }
        }
        //非负整数验证 长度10位
        ,number: function(value){

            var regu = /^(\+)?\d+(\d{1,10})?$/;
            var max = Math.pow(2,31)-1;
            if(value.length>0){
                if (!regu.test(value)){
                    return '格式不正确,请填写非负最多10位整数！';
                }
                if(value > max){
                    return '超出数字格式最大范围,请重新填写！';
                }
            }
        }
        ,dasYear: function(value){
            if(value.length>0) {
                var regu = /^(\+)?\d{1,4}(\.\d{0})?$/;
                if (!regu.test(value)) {
                    return '格式不正确,请填写4位数字年份！';
                }
            }
        }
        //手机号验证,区别再带验证 加了l字符
        ,dasPhone: function(value){
                var regu = /^(1[1-9][0-9]|14[5|7]|15[0|1|2|3|5|6|7|8|9]|18[0|1|2|3|5|6|7|8|9])\d{8}$/;
            if(value.length>0){
                if (!regu.test(value)) {
                    return '手机号码格式不正确,请重新填写!';
                }
            }
        }
        // 电话号码正则：
        ,isMob: function(value){
            var regu = /^(\d{3,4}\)|\d{3,4}-|\s)?\d{7,15}$/;
            if(value.length>0){
                if (!regu.test(value)) {
                    return '电话号码格式不正确,请重新填写!';
                }
            }
        }

        //email验证
        ,dasEmail: function(value){
            var regu =/^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/;
            if(value.length>0){
                if (!regu.test(value)) {
                    return 'email格式不正确,请重新填写!';
                }
            }
        }
        //长度校验,在需要校验长度的组件上添加自定义属性  daslength=长度数字
        ,dasLength: function(value,item){
            var length_ = $(item).attr("daslength");
            if(value.trim().length>length_){
                return "字段长度超过最大"+length_+"限制,请重新填写!";
            }
        }
        ,//阀值校验 value,当前阀值； selectId：关联下拉id
        threshold: function(value,item){
            //浮点小数
            var regu = /^(\+)?\d{1,8}(\.\d{1,2})?$/;
                var selectId = $(item).attr("selectId");
                if($("#"+selectId).val() ==0 && value !="None"){
                    $(item).val("None");
                }
                if($("#"+selectId).val() ==1 && (value =="" || value =="None")){
                    return "请填写具体阈值!";
                }
                if($("#"+selectId).val() ==1 && value.length>0){
                    if (!regu.test(value)) {
                        return '格式不正确,请填写非负整数最多8位，小数最多2位！';
                    }
                }
           /* if (value.length>0) {
                if (!regu.test(value)) {
                    return '格式不正确,请填写非负整数最多8位，小数最多2位！';
                }
            }*/

        }
        //Cron表达式格式校验
        ,cronCheck: function(value){
            if(!$.isEmpty(value)) {
                var cronCheckParams = {};
                cronCheckParams.cronVal = value;
                var checkFlag = true;
                $.ajax({
                    async: false,
                    url: Common.ctxPath + 'common/cronCheck',
                    type: 'GET',
                    data: cronCheckParams,
                    dataType: "json",
                    success: function (rsp) {
                        if (rsp.returnCode != '0') {////0 验证通过，1 验证失败
                            checkFlag = false;
                        }
                    }
                });
                if (!checkFlag) {
                    return "时间表达式有误，请仔细检查！";
                }
            }
        }
        ,isJson: function(value){
            if(!$.isEmpty(value)){
                var jsonParams = {};
                jsonParams.jsonVal = value;
                var checkFlag = true;
                $.ajax({
                    async: false,
                    url: Common.ctxPath + 'common/jsonCheck',
                    type: 'GET',
                    data: jsonParams,
                    dataType: "json",
                    success: function (rsp) {
                        if (rsp.returnCode != '0') {////0 验证通过，1 验证失败
                            checkFlag = false;
                        }
                    }
                });
                if (!checkFlag) {
                    return "json格式有误，请仔细检查！";
                }
            }
        }

    });

    form.on("select(rainFallFactor)",function(data){
        if(data.value == 0){
            $("#rainFallThreshold").val("None");
            $("#rainFallThreshold").attr("readonly","readonly");
        }
        if(data.value == 1){
            $("#rainFallThreshold").val("");
            $("#rainFallThreshold").removeAttr("readonly");
        }
    });
    form.on("select(waterLineFactor)",function(data){
        if(data.value == 0){
            $("#waterLineThreshold").val("None");
            $("#waterLineThreshold").attr("readonly","readonly");
        }
        if(data.value == 1){
            $("#waterLineThreshold").val("");
            $("#waterLineThreshold").removeAttr("readonly");
        }
    });
    form.on("select(soilFactor)",function(data){
        if(data.value == 0){
            $("#soilThreshold").val("None");
            $("#soilThreshold").attr("readonly","readonly");
        }
        if(data.value == 1){
            $("#soilThreshold").val("");
            $("#soilThreshold").removeAttr("readonly");
        }
    });

});


function getRootPath(){
    var pathName = window.location.pathname.substring(1);
    var webName = pathName == '' ? '' : pathName.substring(0, pathName.indexOf('/'));
    if (webName == "") {
        return window.location.protocol + '//' + window.location.host;
    }
    else {
        return window.location.protocol + '//' + window.location.host + '/' + webName;
    }
}