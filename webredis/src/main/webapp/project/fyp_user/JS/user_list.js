
$( function(){
    inituserlist();
})

function inituserlist() {
    $.ajax({
        type:'POST',
        url:'../../UserCRUD/showUsers',
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

function login() {
    $.ajax({
        type:'POST',
        url:'../../UserCRUD/login',
        data:{
            userid:$("#userid").val(),
            pwd:$("#pwd").val()
        },
        success:function (data) {
            if(data.message == 'success'){
                window.location.href='websocket.html';
            }else{
                alert("账号或者密码错误");
                $("#userid").val("");
                $("#pwd").val("");
            }
        }
    })
}