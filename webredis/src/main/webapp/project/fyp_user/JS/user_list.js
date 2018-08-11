
$( function(){
    inituserlist();
})

function inituserlist() {
    $.ajax({
        type:'POST',
        url:'../../UserCRUD/getalluser',
        dataType:'json',
        success:function(data){
            var html ="";
            if(data.message == 'success') {
                var list = data.data;
                for (var i in list) {
                    html += '<div style="color:red">姓名：' + list[i].userName + ';年齡：' + list[i].age + ';性别：' + list[i].sex + "<br></div>";
                }
                $("#test").html(html);
            }
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