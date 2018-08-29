package com.personal.feng.project.active_mq.service;


import java.io.Serializable;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.jms.BytesMessage;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.StreamMessage;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

/*
 *消息生产者 p2p模式
 *
 * */
@Service
public class ProducerService {

    @Resource(name = "jmsTemplate")
    private JmsTemplate jmsTemplate;

    /**
     * 向指定Destination(目标)发送text消息
     *
     * @param destination
     * @param message
     */
    public void sendTxtMessage(Destination destination, final String message) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(message);
            }
        });
        System.out.println("springJMS send text message...");
    }

    /**
     * 向指定Destination发送map消息
     *
     * @param destination
     * @param message
     */
    public void sendMapMessage(Destination destination, final String message) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                MapMessage mapMessage = session.createMapMessage();
                mapMessage.setString("msgId", message);
                return mapMessage;
            }
        });
        System.out.println("springJMS send map message...");
    }

    /**
     * 向指定Destination发送序列化的对象
     *
     * @param destination
     * @param object      object 必须序列化
     */
    public void sendObjectMessage(Destination destination, final Serializable object) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(object);
            }
        });
        System.out.println("springJMS send object message...");
    }

    /**
     * 向指定Destination发送字节消息
     *
     * @param destination
     * @param bytes
     */
    public void sendBytesMessage(Destination destination, final byte[] bytes) {
        if (null == destination) {
            destination = jmsTemplate.getDefaultDestination();
        }
        jmsTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                BytesMessage bytesMessage = session.createBytesMessage();
                bytesMessage.writeBytes(bytes);
                return bytesMessage;

            }
        });
        System.out.println("springJMS send bytes message...");
    }

    /**
     * 向默认队列发送Stream消息
     */
    public void sendStreamMessage(Destination destination) {
        jmsTemplate.send(new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                StreamMessage message = session.createStreamMessage();
                message.writeString("stream string");
                message.writeInt(11111);
                return message;
            }
        });
        System.out.println("springJMS send Strem message...");
    }

}
