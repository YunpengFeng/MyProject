
/*要注意加載順序，等頁面渲染完再去獲取值*/
var userinfo =""
var websocket = null;

/*使用声明式写法*/
(function(){
    "use strict";
    var init = {
        userinit: function () {
            $.ajax({
                type: 'POST',
                url: '../../UserCRUD/getUserinfo',
                success: function (data) {
                    if (data.id != 'error') {
                        userinfo = data;
                        $("#username").text(userinfo.userName + "欢迎进入")
                        init.websocketinit(userinfo.id);
                    }
                }
            })
        },

        websocketinit: function (userid) {
            alert(userid)
        //判断当前浏览器是否支持WebSocket
                if ('WebSocket' in window) {
                    websocket = new WebSocket("ws://localhost:8099/RedisDemo/websocket/" + userid);
                }
                else {
                    alert('Not support websocket')
                }

        //连接发生错误的回调方法
                websocket.onerror = function () {
                    setMessageInnerHTML("error");
                };

        //连接成功建立的回调方法
                websocket.onopen = function (event) {
                    setMessageInnerHTML("open");
                }

              //接收到消息的回调方法
                websocket.onmessage = function () {
                    setMessageInnerHTML(event.data);
                    alert("你收到一条消息" + event.data)
                }

            //连接关闭的回调方法
                websocket.onclose = function () {
                    setMessageInnerHTML("close");
                }

             //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
                window.onbeforeunload = function () {
                    websocket.close();
                }

                  //将消息显示在网页上
                function setMessageInnerHTML(innerHTML) {
                    document.getElementById('message').innerHTML += innerHTML + '<br/>';
                }

              //关闭连接
                function closeWebSocket() {
                    websocket.close();
                }

                //发送消息
                function send() {
                    var message = document.getElementById('text').value;
                    websocket.send(message);
                }


        }
    }

    $(function(){
       init.userinit();
    })

})("../../")


