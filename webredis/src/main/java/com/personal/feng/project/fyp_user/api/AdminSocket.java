package com.personal.feng.project.fyp_user.api;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;


@ServerEndpoint(value = "/adminwebsocket")
public class AdminSocket {
    //静态变量，用来记录当前在它来给客户端发送数据
    private  static  Session session;

    /*用戶的id，后期可以改成cookie*/
    private String userid = "";

    /**
     * 连接建立成功调用的方法
     *
     * @param session 可选的参数。session为与某个客户端的连接会话，需要通过它来给客户端发送数据
     */
    @OnOpen
    public void onOpen(Session session) {
        this.session = session;
       System.out.println("...............有新连接加入！当前在线人数为" + "人...................");
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        System.out.println("有一连接关闭！当前在线人数为" );
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message 客户端发送过来的消息
     * @param session 可选的参数
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        try {
            this.sendMessage("Szsdz");
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
    public static void sendMessage(String message) throws IOException {
        session.getBasicRemote().sendText(message);//阻塞式
        //this.session.getAsyncRemote().sendText(message); //非阻塞式
    }



}
