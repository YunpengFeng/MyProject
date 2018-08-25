$(function () {
    inituserlist();
})

function inituserlist() {
    $.ajax({
        type: 'POST',
        url: '../../UserCRUD/getalluser',
        dataType: 'json',
        success: function (data) {
            var html = "";
            if (data.message == 'success') {
                var list = data.data;
                for (var i in list) {
                    html += '<div style=""><a href ="javascript:void(0)" onclick="userinfo(' + list[i].id + ')">姓名：' + list[i].userName + ';年齡：' + list[i].age + ';性别：' + list[i].sex + "</a><br></div>";
                }
                $("#test").html(html);
            }
        }

    })
}

function login() {
    $.ajax({
        type: 'POST',
        url: '../../UserCRUD/login',
        data: {
            userid: $("#userid").val(),
            pwd: $("#pwd").val()
        },
        success: function (data) {
            if (data.message == 'success') {
                window.location.href = 'websocket.html';
            } else {
                alert("账号或者密码错误");
                $("#userid").val("");
                $("#pwd").val("");
            }
        }
    })
}


/*用户详细信息*/
function userinfo(index) {
    $.ajax({
        type: 'POST',
        url: '../../UserCRUD/userinfo/' + index,
        data: {
            testParam: "111",
        },
        success: function (data) {
            var text = 'restful风格获取的数据......姓名：' + data.data.userName + ';年齡：' + data.data.age + ';性别：' + data.data.sex + '</a><br>'
            $("#userinfo").html(text);

        }
    })
}