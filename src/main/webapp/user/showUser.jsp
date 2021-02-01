<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>

<script type="text/javascript">
    $(function(){
        //初始化表格
        initUserTable();

        $("#exportUser").click(function (){
            $.get(
                "${path}/user/export",
                function (result){
                    $("#exportUserSpan").text(result.message);
                },"json"
            )
        });
    });

    //配置表格的相关参数
    function initUserTable(){
        $("#userTable").jqGrid({
            styleUI:"Bootstrap",
            url:"${path}/user/ajaxQueryAll",
            datatype:"json",
            colNames:["ID","手机号","微信号","头像","用户名","签名","状态","注册时间",],
            // editoptions: {"multipart/form-data":true},
            collEdit:true,
            colModel:[
                {name:"id"},
                {name:"phone",editable:true},
                {name:"vxNum",editable:true},
                {name:"picImg", index:"picImg",edittype:"file",editable:true,editoptions:{enctype:"multipart/form-data"},
                    formatter: function (cellvalue, options, rowObject){
                        return "<img src='${path}/upload/"+rowObject.picImg+"' width='300px' height='100px'/>";
                    }},
                {name:"nickName",editable:true},
                {name:"brief",editable:true},
                {name:"status",formatter:function (cellvalue, options, rowObject){
                        if(rowObject.status == 1){
                            return "<button class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>冻结</button>";
                        }else{
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>解除冻结</button>";
                        }
                    }},
                {name:"createDate",formatter:"date"}
            ],
            autowidth:true,
            pager:"#userPage",
            rowNum:3,
            rowList:[1,3,5,7,9],
            viewrecords : true,  //是否展示总条数
            height:"auto",
            editurl:"${path}/user/operation"
        })
        $("#userTable").jqGrid('navGrid', '#userPage',
            {edit : true,add : true,del : true,addtext:"添加",deltext:'删除',edittext:"修改"},
            {
                closeAfterEdit:true, //关闭对话框
                afterSubmit:function(data){  //提交之后触发
                    if(data.responseText!=''){
                        //console.log(data.responseText);   添加数据的id
                        //文件上传   异步
                        $.ajaxFileUpload({
                            fileElementId: "picImg",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                            url: "${path}/user/upload", //后台方法的路径
                            type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                            //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                            data:{"id":data.responseText},
                            success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                                //刷新表单
                                $("#userTable").trigger("reloadGrid");
                            }
                        });
                        return "hello";
                    }
                }
            },  //修改之后的额外操作
            {
                closeAfterAdd:true, //关闭对话框
                afterSubmit:function(data){  //提交之后触发
                    //console.log(data.responseText);   添加数据的id
                    //文件上传   异步
                    $.ajaxFileUpload({
                        fileElementId: "picImg",    //需要上传的文件域的ID，即<input type="file" id="headImg" name="headImg" >的ID。
                        url: "${path}/user/upload", //后台方法的路径
                        type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                        //dataType: 'json',   //服务器返回的数据类型。可以为xml,script,json,html。如果不填写，jQuery会自动判断。
                        data:{"id":data.responseText},
                        success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                            //刷新表单
                            $("#userTable").trigger("reloadGrid");
                        }
                    });
                    return "hello";
                }
            },  //添加之后的额外操作
        );
    }

    function updateStatus(id,status){
        if(status == 1){
            $.post("${path}/user/updateStatus",{"id":id,"status":"0"},function (data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            });
        }else{
            $.post("${path}/user/updateStatus",{"id":id,"status":"1"},function (data){
                //刷新表单
                $("#userTable").trigger("reloadGrid");
            });
        }
    }

</script>

<%--创建一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>用户信息</h2>
    </div>

    <!-- 标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" >用户管理</a></li>
    </ul><br>

    <%--按钮组--%>
    <div>
        &emsp;&emsp;<button class="btn btn-success" id="exportUser">导出用户信息</button><span id="exportUserSpan"></span>
        &emsp;&emsp;<button class="btn btn-info">导出用户信息</button>
        &emsp;&emsp;<button class="btn btn-danger">导出用户信息</button>
    </div><br>

    <%--创建表格--%>
    <table id="userTable" />

    <%--创建分页工具栏--%>
    <div id="userPage" />

</div>