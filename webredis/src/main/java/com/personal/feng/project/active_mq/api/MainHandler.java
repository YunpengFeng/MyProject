package com.personal.feng.project.active_mq.api;

import java.text.SimpleDateFormat;
import java.util.*;

import javax.annotation.Resource;
import javax.jms.Destination;


import org.apache.activemq.command.ActiveMQDestination;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.personal.feng.project.active_mq.service.ProducerService;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * @author feng
 * activemq查看队列信息的控制台url地址：http://192.168.7.111:8161/admin/queues.jsp
 */
@Controller
public class MainHandler {


    //队列名
    @Resource(name = "queueDestination")
    private Destination queueDestination;


    //队列消息生产者
    @Resource(name = "producerService")
    private ProducerService producerService;


    @RequestMapping(value = "/main", method = RequestMethod.GET)
    public String producer() {

        return "main";
    }

    /**
     * 往队列queue1中发送消息
     *
     * @param message
     * @return
     */
    @RequestMapping(value = "/sendone", method = RequestMethod.GET)
    public @ResponseBody String producer(@RequestParam("message") String message) {

        /**
         * 将destination强制转换为ActiveMQDestination，在ActiveMQDestination对象中，
         *    通过getCompositeDestinations()方法获取destination队列数组：queue://queue1  queue://queue2
         *
         */
        ActiveMQDestination activeMQDestination = (ActiveMQDestination) queueDestination;
        /**
         * 往队列queue1中发送文本消息
         */
        System.out.println("往队列" + activeMQDestination.getCompositeDestinations()[0].getPhysicalName() + "中发送文本消息");
        producerService.sendTxtMessage(activeMQDestination.getCompositeDestinations()[0], message);
        /**
         * 往队列queue1中发送MapMessage消息
         */
        System.out.println("往队列" + activeMQDestination.getCompositeDestinations()[0].getPhysicalName() + "中发送MapMessage消息");
        producerService.sendMapMessage(activeMQDestination.getCompositeDestinations()[0], message);

        //String bb="fdsalfkasdfkljasd;flkajsfd";
        //byte[] b =  bb.getBytes();

        // producer.sendBytesMessage(demoQueueDestination, b);

        //producer.sendMapMessage(mqQueueDestination, message);

        return "main";
    }

    /**
     * 往消息队列queue2中发送消息
     *
     * @param message
     * @return
     */

    @RequestMapping(value = "/sendtwo", method = RequestMethod.GET)
    public @ResponseBody
    String producertwo(@RequestParam("message") String message) {


        /**
         * 将destination强制转换为ActiveMQDestination，在ActiveMQDestination对象中，
         *    通过getCompositeDestinations()方法获取destination队列数组：queue://queue1  queue://queue2
         *
         */
        ActiveMQDestination activeMQDestination = (ActiveMQDestination) queueDestination;
        /**
         * 队列queue2中发送文本消息
         */
        System.out.println("往队列" + activeMQDestination.getCompositeDestinations()[1].getPhysicalName() + "中发送文本消息");
        producerService.sendTxtMessage(activeMQDestination.getCompositeDestinations()[1], message);
        /**
         * 队列queue2中发送mapMessage消息
         */
        System.out.println("往队列" + activeMQDestination.getCompositeDestinations()[1].getPhysicalName() + "中发送文本消息");
        producerService.sendMapMessage(activeMQDestination.getCompositeDestinations()[1], message);

        String bb = "fdsalfkasdfkljasd;flkajsfd";
        byte[] b = bb.getBytes();

        // producer.sendBytesMessage(demoQueueDestination, b);

        //producer.sendMapMessage(mqQueueDestination, message);

        return "main";
    }


}