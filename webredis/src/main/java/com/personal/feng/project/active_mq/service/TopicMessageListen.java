package com.personal.feng.project.active_mq.service;

import org.apache.activemq.command.ActiveMQDestination;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;

public class TopicMessageListen implements MessageListener {



    @Override
    public void onMessage(Message message) {
        try {
            ActiveMQDestination topics = (ActiveMQDestination) message.getJMSDestination();



        } catch (JMSException e) {
            e.printStackTrace();
        }

    }
}
