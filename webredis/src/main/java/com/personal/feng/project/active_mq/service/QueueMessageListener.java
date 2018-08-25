package com.personal.feng.project.active_mq.service;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;
import javax.jms.StreamMessage;
import javax.jms.TextMessage;

import com.personal.feng.utils.mail.Email;
import org.apache.activemq.advisory.DestinationEvent;
import org.apache.activemq.command.ActiveMQDestination;
import org.apache.activemq.command.ActiveMQMessage;
import org.apache.activemq.command.DestinationInfo;
/*
 * 队列消息监听器
 *
 * */

public class QueueMessageListener implements MessageListener {

    /*邮件发送服务*/
    @Resource(name = "EmailService")
    private Email EmailService;

    //当收到消息后，自动调用该方法
    @Override
    public void onMessage(Message message) {
        try {
            ActiveMQDestination queues = (ActiveMQDestination) message.getJMSDestination();

            /**
             * 邮件队列
             * 监听消息队列Emailqueue中的消息
             */
            if (queues.getPhysicalName().equalsIgnoreCase("Emailqueue")) {
                System.out.println("监听队列:邮件" + queues.getPhysicalName() + "消费了消息:");
                // 如果是文本消息
                if (message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    EmailService.sendMail("1565370422@qq.com", "apache activemq", "明天吃什么啊，刘阿姨");
                    System.out.println("...............已经发送成功！.....................");

                }

                // 如果是Map消息
                if (message instanceof MapMessage) {
                    MapMessage mm = (MapMessage) message;
                    try {
                        System.out.println("from get MapMessage：\t" + mm.getString("msgId"));
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
            /**
             * 监听消息队列queue2中的消息
             */
            if (queues.getPhysicalName().equalsIgnoreCase("queue2")) {
                System.out.println("监听队列:" + queues.getPhysicalName() + "消费了消息:");
                // 如果是文本消息
                if (message instanceof TextMessage) {
                    TextMessage tm = (TextMessage) message;
                    try {
                        System.out.println("from get textMessage：\t" + tm.getText());
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }

                // 如果是Map消息
                if (message instanceof MapMessage) {
                    MapMessage mm = (MapMessage) message;
                    try {
                        System.out.println("from get MapMessage：\t" + mm.getString("msgId"));
                    } catch (JMSException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }

        } catch (JMSException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        // 如果是Object消息
        if (message instanceof ObjectMessage) {
            ObjectMessage om = (ObjectMessage) message;
            System.out.println("from get ObjectMessage：\t");
        }

        // 如果是bytes消息
        if (message instanceof BytesMessage) {
            System.out.println("from get BytesMessage：\t");
            byte[] b = new byte[1024];
            int len = -1;
            BytesMessage bm = (BytesMessage) message;
            try {
                while ((len = bm.readBytes(b)) != -1) {
                    System.out.println(new String(b, 0, len));
                }
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

        // 如果是Stream消息
        if (message instanceof StreamMessage) {
            System.out.println("from get BytesMessage：\t");
            StreamMessage sm = (StreamMessage) message;
            try {
                System.out.println(sm.readString());
                System.out.println(sm.readInt());
            } catch (JMSException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        }
    }

}
