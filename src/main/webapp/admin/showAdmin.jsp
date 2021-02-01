<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#adminTable").jqGrid({
            url : "${path}/admin/queryByPage",
            datatype : "json",
            rowNum : 3,
            rowList : [ 1,3, 10, 20, 30 ],
            pager : '#adminPage',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '用户名','密码', '状态', '盐' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'username',index : 'invdate',width : 90,editable:true},
                {name:'password',width : 90,editable: true},
                {name : 'status',index : 'name',width : 100,formatter:function (cellvalue, options, rowObject){
                        if(rowObject.status == 1){
                            return "<button class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>冻结</button>";
                        }else{
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")'>解除冻结</button>";
                        }
                    }},
                {name : 'salt',index : 'note',width : 150,sortable : false}
            ],
            editurl:"${path}/admin/option"
        });

        $("#adminTable").jqGrid('navGrid', '#adminPage', {add : true,edit : true,del : true},{closeAfterAdd:true},
            {closeAfterEdit:true}
        );
    }

    function updateStatus(id,status){

        if(status==1){
            $.post("${path}/admin/updateStatus",{"id":id,"status":"0","oper":"edit"},function(data){
                //刷新表单
                $("#adminTable").trigger("reloadGrid");
            });
        }else{
            $.post("${path}/admin/updateStatus",{"id":id,"status":"1","oper":"edit"},function(data){
                //刷新表单
                $("#adminTable").trigger("reloadGrid");
            });
        }

    }

</script>

<%--创建一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>管理员信息</h2>
    </div>

    <!-- 标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" >管理员管理</a></li>
    </ul>

    <%--创建表格--%>
    <table id="adminTable" />

    <%--创建分页工具栏--%>
    <div id="adminPage" />

</div>