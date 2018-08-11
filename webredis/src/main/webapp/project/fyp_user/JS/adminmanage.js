/*
document.ready和onload的区别:
一、JavaScript文档加载完成事件页面加载完成有两种事件一是ready，表示文档结构已经加载完成（不包含图片等非文字媒体文件）
二、是onload，指示页面包含图片等文件在内的所有元素都加载完成。
*/
$(function () {
    /*要注意加載順序，等页面渲染完再去获取值*/
    var userinfo = "";

    $.ajax({
        async: false, /*此处不能为异步，要先加载出用户信息*/
        type: 'POST',
        url: '../../UserCRUD/getUserinfo',
        success: function (data) {
            if (data.id != 'error') {
                userinfo = data;
                $("#tt").html(userinfo.userName + "欢迎进入" + "用戶編號" + userinfo.id)
            }
        }
    })



    var websocket = null;
    //判断当前浏览器是否支持WebSocket
    if ('WebSocket' in window) {
        websocket = new WebSocket("ws://localhost:8099/RedisDemo/websocket/" + userinfo.id);
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
        setMessageInnerHTML("..................................................................连接成功......................................................................");
    }

    //接收到消息的回调方法
    websocket.onmessage = function () {
        setMessageInnerHTML(event.data);
    }

    //连接关闭的回调方法
    websocket.onclose = function () {
        setMessageInnerHTML("..................................................................连接关闭......................................................................");
    }

    //监听窗口关闭事件，当窗口关闭时，主动去关闭websocket连接，防止连接还没断开就关闭窗口，server端会抛异常。
    window.onbeforeunload = function () {
        websocket.close();
    }

    //将消息显示在网页上
    setMessageInnerHTML = function (innerHTML) {
        document.getElementById('number').innerHTML += innerHTML + '<br/><br/>';
    }

    //关闭连接
    closeWebSocket = function () {
        websocket.close();
    }
/*
    //发送消息
    send = function () {
        var message = document.getElementById('text').value;
        websocket.send(message);
    }*/
});


