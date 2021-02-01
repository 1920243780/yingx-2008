<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>


<script>
    $(function(){
        pageInit();
    });

    function pageInit(){
        $("#cateTable").jqGrid({
            url : "${path}/cate/queryOneByPage",
            datatype : "json",
            rowNum : 8,
            rowList : [ 8, 10, 20, 30 ],
            pager : '#catePage',
            viewrecords : true,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '类别名', '级别', '父类别ID' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'invdate',width : 90,editable:true},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',index : 'note',width : 150,sortable : false}
            ],
            editurl:"${path}/cate/option",
            subGrid : true,  //是否开启子表格
            /*
            subgrid_id：是在创建表数据时创建的div标签的ID
            row_id：    是该行的ID
            * */
            subGridRowExpanded : function(subgrid_id, row_id) {  //设置展开子表格的参数
                addSubGird(subgrid_id, row_id);
            }
        });

        //父表格分页工具栏
        $("#cateTable").jqGrid('navGrid', '#catePage', {add : true,edit : true,del : true},{closeAfterEdit:true},
            {closeAfterAdd:true});
    }


    //设置子表格的属性
    function addSubGird(subgridId, rowId){

        //生命变量并赋值  子表格表格的id
        var subgridTableId = subgridId+"Table";
        //子表格分页工具栏id
        var pagerId = subgridId+"Page";

        //在子表格的div中创建一个table和一个div
        $("#"+subgridId).html("<table id="+subgridTableId+" /><div id="+pagerId+" />");

        //设置习表哥的属性
        $("#" + subgridTableId).jqGrid({
            url : "${path}/cate/queryTwoByPage?parentId="+ rowId,//rowId根据以及类别查询二级类别时使用
            datatype : "json",
            rowNum : 20,
            pager : "#"+pagerId,
            styleUI:"Bootstrap",
            autowidth:true,
            height:"auto",
            colNames : [ 'Id', '类别名', '级别', '父类别ID' ],
            colModel : [
                {name : 'id',index : 'id',  width : 55},
                {name : 'cateName',index : 'invdate',width : 90,editable:true},
                {name : 'levels',index : 'name',width : 100},
                {name : 'parentId',index : 'note',width : 150,sortable : false}
            ],
            editurl: "${path}/cate/option?parentId="+rowId
        });
        //子表格分页工具栏
        $("#" + subgridTableId).jqGrid('navGrid',"#" + pagerId, {edit : true,add : true,del : true},{closeAfterEdit:true},
            {closeAfterAdd:true});

    }

</script>

<%--创建一个面板--%>
<div class="panel panel-success">

    <%--面板头--%>
    <div class="panel panel-heading">
        <h2>类别信息</h2>
    </div>

    <!-- 标签页 -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" >类别管理</a></li>
    </ul>

    <%--创建表格--%>
    <table id="cateTable" />

    <%--创建分页工具栏--%>
    <div id="catePage" />

</div>