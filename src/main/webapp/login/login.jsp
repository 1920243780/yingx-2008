<%@page pageEncoding="utf-8" language="java"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="path" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <title>登录</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${path}/login/assets/lib/css/bootstrap.3.3.7.min.css">
    <script src="${path}/login/assets/lib/js/jquery.2.2.1.min.js"></script>
    <script src="${path}/login/assets/lib/js/bootstrap.3.3.7.min.js"></script>
    <script src="${path}/login/assets/js/jquery.validate.js"></script>
    <script type="text/javascript">
        $(function (){
            $("form").validate({
                rules:{
                    username:{required:true,minlength:4},
                    password:{required:true,minlength:4}
                },
                errorPlacement:function(error,element){
                    element.next().append(error);
                }
            });

            $("#changeImg").click(function (){
                $(this).prop("src","${path}/admin/createImg?"+Math.random())
            });
        });
    </script>
    <style>
        html,body{
            height: 100%;
            margin: 0;
            padding: 0;
            font-family: "宋体";
        }
        body{
            background: url("assets/img/bg.jpg") 0 / cover fixed;
            position: relative;
            background-color:#888;
        }
        form{
            padding:20px 40px;
        }
        #main{
            background: rgba(255,255,255,0.96);
            box-shadow: 20px 20px 50px rgba(0,0,0,0.86);
            position: absolute;
            top:50%;
            left:50%;
            height:500px;
            width: 440px;
            margin-top: -230px;
            margin-left: -220px;
            border-radius: 8px;
        }
        #logo{
            padding-top: 20px;
            display:block;
            margin: 0 auto;
            width: 140px;
            border-radius: 100%;
        }
        .form-group{
            padding-bottom: 16px;
        }
    </style>
</head>
<body>
    <div id="main">
        <img src="assets/img/logo.png" alt="" id="logo">
        <form action="${path}/admin/login" class="form-horizontal" method="post">
            <div class="form-group">
                <label for="username" class="control-label col-sm-3">用户名：</label>
                <div class="col-sm-8">
                    <input type="text" name="username" id="username" class="form-control"/><span style="color:red;font-size: 10px"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="password" class="control-label col-sm-3">密&nbsp码：</label>
                <div class="col-sm-8">
                    <input type="password" name="password" id="password" class="form-control"/><span style="color:red;font-size: 10px"></span>
                </div>
            </div>
            <div class="form-group">
                <label for="vcode" class="control-label col-sm-3">验证码：</label>
                <div class="col-sm-5">
                    <input type="text" name="code" id="vcode" class="form-control"/>
                </div>
                    <img src="${path}/admin/createImg" id="changeImg" alt="" style="width: 25%">
            </div>
            <div style="text-align: center">
                <button type="submit" class="btn btn-primary btn-lg" style="width:60%">登 录</button>
            </div>
        </form>
    </div>
    <script>
    </script>
</body>
</html>