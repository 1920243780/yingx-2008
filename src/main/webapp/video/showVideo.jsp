<%@page pageEncoding="UTF-8" isELIgnored="false" contentType="text/html; UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}" />

<script>
    $(function(){
        //初始化表单属性
        $("#vTable").jqGrid({
            url : "${path}/video/queryByPage",  //分页查询   page  rows  total recoreds  page  rows
            editurl:"${path}/video/option",
            datatype : "json",
            rowNum : 3,  //每页展示是条数
            rowList : [ 3,5,10, 20, 30 ],
            pager : '#vPager',
            styleUI:"Bootstrap",
            height:"auto",
            autowidth:true,
            viewrecords:true,  //是否展示数据总条数
            colNames : [ 'ID', '标题', '描述', '视频','封面', '上传时间','状态','播放次数','点赞次数','用户Id', '类别id','分组id'],
            colModel : [
                {name : 'id',width : 55},
                {name : 'title',editable:true,width : 90},
                {name : 'description',editable:true,width : 100},
                {name : 'videoPath',editable:true,width : 400,align : "center",edittype:"file",
                    formatter:function(cellvalue, options, rowObject){
                        return "<video id='media' src='"+rowObject.videoPath+"' controls width='400px' heigt='800px' poster='"+rowObject.coverPath+"'/>";
                    }
                },
                {name : 'coverPath',hidden:true,width : 80,align : "right"},
                {name : 'createTime',width : 80,align : "right"},
                {name : 'status',width : 80,align : "center",
                    formatter:function(cellvalue, options, rowObject){
                        //判断状态
                        if(cellvalue==1){
                            //正常状态    冻结  绿色  id  status
                            return "<button class='btn btn-success' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")' >下线</button>";
                        }else{
                            //冻结状态   解除冻结  红色
                            return "<button class='btn btn-danger' onclick='updateStatus(\""+rowObject.id+"\",\""+rowObject.status+"\")' >上线</button>";
                        }
                    }
                },
                {name : 'playCount',width : 80,align : "center"},
                {name : 'likeCount',width : 80,align : "center"},
                {name : 'categoryId',width : 80,align : "center"},
                {name : 'userId',width : 80,align : "right"},

                {name : 'groupId',width : 150,sortable : false}
            ]
        });

        //处理曾删改查操作   工具栏
        $("#vTable").jqGrid('navGrid', '#vPager',
            {edit : true,add : true,del : true,edittext:"修改",addtext:"添加",deltext:"删除"},
            {
                closeAfterEdit:true,
                beforeShowForm :function(obj){
                    obj.find("#videoPath").attr("disabled",true);//禁用input
                }
            }, //执行修改之后的额外操作
            {
                closeAfterAdd:true, //关闭添加的对话框
                afterSubmit:function(data){
                    console.log(data);
                    $.ajaxFileUpload({
                            fileElementId: "videoPath",    //需要上传的文件域的ID，即<input type="file">的ID。
                            url: "${path}/video/upload", //后台方法的路径
                            type: 'post',   //当要提交自定义参数时，这个参数要设置成post
                            data:{id:data.responseText},  //responseText: "74141b4c-f337-4ab2-ada2-c1b07364adee"
                            success: function() {   //提交成功后自动执行的处理函数，参数data就是服务器返回的数据。
                                //刷新页面
                                $("#vTable").trigger("reloadGrid");
                            }
                        });
                        //必须要有返回值
                        return "aaa";
                }
            }/*, //执行添加之后的额外操作
            {
                afterSubmit:function(data){
                    //向警告框中追加错误信息
                    $("#showMsg").html(data.responseJSON.message);
                    //展示警告框
                    $("#deleteMsg").show();

                    //自动关闭
                    setTimeout(function(){
                        $("#deleteMsg").hide();
                    },3000);
                    return "hello";
                }
            }*/ //执行删除之后的额外操作
        );
    });

    //修改状态
    function updateStatus(id,status){

        if(status==1){
            $.post("${path}/video/updateStatus",{"id":id,"status":"0","oper":"edit"},function(data){
                //刷新表单
                $("#vTable").trigger("reloadGrid");
            });
        }else{
            $.post("${path}/video/updateStatus",{"id":id,"status":"1","oper":"edit"},function(data){
                //刷新表单
                $("#vTable").trigger("reloadGrid");
            });
        }
    }

</script>

<%--初始化一个面板--%>
<div class="panel panel-info">

    <%--面板头--%>
    <div class="panel panel-heading" align="center">
        <h2>视频管理</h2>
    </div>

    <%--选项卡--%>
    <div class="nav nav-tabs">
        <li class="active"><a href="">视频信息</a></li>
    </div>

    <%--警告提示框--%>
    <div id="deleteMsg" class="alert alert-danger" style="height: 50px;width: 250px;display: none" align="center">
        <span id="showMsg"/>
    </div>

    <%--初始化表单--%>
    <table id="vTable" />

    <%--工具栏--%>
    <div id="vPager" />

</div>