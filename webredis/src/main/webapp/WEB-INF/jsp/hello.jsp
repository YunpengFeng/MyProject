<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/8/4 0004
  Time: 下午 3:51
  To change this template use File | Settings | File Templates.
--%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<html>
<head>
    <title>helloeword</title>
    <script type="text/javascript" src="../JavaScript/Jquery/jquery.min.js"></script>
</head>
<body>
我是从index.jsp跳转的。
<b>helloeword</b>
<br>
姓名：${user.userName}
性别：${user.sex}
年龄：${user.age}
编号：${user.id}

<hr>
<button onclick="synRequest()">點我出現新的用戶</button>
<div id ="test"></div>
</body>
<script>
   function synRequest(){
        $.ajax({
            type:'POST',
            url:'../UserCRUD/synRequest',
            dataType:'json',
            success:function(data){
                var html ="";
                for(var i in data){
                    html += '<div style="color:red">姓名：'+data[i].userName+';年齡：'+data[i].age+';性别：'+data[i].sex+"<br></div>";
                }
                $("#test").html(html);
            }

        })
    }

</script>
</html>
