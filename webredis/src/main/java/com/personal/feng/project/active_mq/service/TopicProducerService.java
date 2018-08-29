package com.personal.feng.project.active_mq.service;

import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import java.io.Serializable;

/*
 *消息生产者 发布-订阅模式
 *
 * */
@Service
public class TopicProducerService {

    @Resource(name = "jmsTopicTemplate")
    private JmsTemplate jmsTopicTemplate;

    /**
     * 向指定Destination发送序列化的对象
     *
     * @param destination
     * @param object      object 必须序列化
     */
    public void sendObjectMessage(Destination destination, final Serializable object) {
        if (null == destination) {
            destination = jmsTopicTemplate.getDefaultDestination();
        }
        jmsTopicTemplate.send(destination, new MessageCreator() {
            public Message createMessage(Session session) throws JMSException {
                return session.createObjectMessage(object);
            }
        });
        System.out.println("springJMS send object message...");
    }


}
