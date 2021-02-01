<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#logTable").jqGrid({
            url : "${path}/log/queryByPage",
            datatype : "json",
            rowNum : 3,
            rowList : [ 1,3, 10, 20, 30 ],
            pager : '#logPage',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '管理员名称','操作时间', '操作方法', '状态' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'adminName',index : 'invdate',width : 90,editable:true},
                {name:'optionTime',width : 90,editable: true},
                {name : 'methodName',index : 'name',width : 100},
                {name : 'status',index : 'note',width : 150,sortable : false}
            ],
        });

        $("#logTable").jqGrid('navGrid', '#logPage', {add : true,edit : true,del : true},{closeAfterAdd:true},
            {closeAfterEdit:true}
        );
    }

</script>

<%--创建一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>日志信息</h2>
    </div>

    <!-- 标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" >日志管理</a></li>
    </ul>

    <%--创建表格--%>
    <table id="logTable" />

    <%--创建分页工具栏--%>
    <div id="logPage" />

</div>