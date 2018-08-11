package com.personal.feng.project.fyp_user.api;


import com.personal.feng.utils.ResultJO;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;

@ServerEndpoint(value = "/websocket/{userid}")
public class FirstWebSocket {


    //静态变量，用来记录当前在线连接数。应该把它设计成线程安全的。
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。若要实现服务端与单一客户端通信的话，可以使用Map来存放，其中Key可以为用户标识
    // private static CopyOnWriteArraySet<FirstWebSocket> webSocketSet = new CopyOnWriteArraySet<FirstWebSocket>();
    private static ConcurrentHashMap<String, FirstWebSocket> webSocketSet = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    /*用戶的id，后期可以改成cookie*/
    private String userid = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(@PathParam(value = "userid") String param, Session session) {
        this.session = session;
        userid = param;
        webSocketSet.put(userid, this);     //加入set中
        addOnlineCount();           //在线数加1
        System.out.println("当前连接加入的人的id为" + userid);
        System.out.println("...............有新连接加入！当前在线人数为" + getOnlineCount() + "人...................");

    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(userid);  //从set中删除
        subOnlineCount();           //在线数减1
        System.out.println("有一连接关闭！当前在线人数为" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("来自客户端的消息:" + message);
        //群发消息,广播
        if (1 < 2) {
            this.sendAll(message, userid);
        } else {
            //给指定的人发消息
            this.sendToUser(message);
        }
    }

    public void sendAll(String message, String userid) {
        Date date = new Date();
        SimpleDateFormat simple = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /*通过key的方式遍历map*/
        for (String key : webSocketSet.keySet()) {
            try {
                String _message = "用戶" + userid + "于" + simple.format(date) + "发送了消息为：\"<b>" + message + "\"</b>";
                webSocketSet.get(key).sendMessage(_message);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }

    public void sendToUser(String message) {

        Date date = new Date();
        /*通过key的方式遍历map*/
        try {
            webSocketSet.get(userid).sendMessage("用戶" + userid + "于" + date.toString() + "发送了消息为：\"" + message + "\"");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 发生错误时调用
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        System.out.println("发生错误");
        error.printStackTrace();
    }

    /**
     * 这个方法与上面几个方法不一样。没有用注解，是根据自己需要添加的方法。
     *
     * @param message
     * @throws IOException
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
        //this.session.getAsyncRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        FirstWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        FirstWebSocket.onlineCount--;
    }


}




