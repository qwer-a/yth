package com.fh.rabbitmq.work;

import com.fh.util.MQConnectionUtils;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Producer {

    //队列名称
    private static final String UEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        //创建新的连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        //创建队列
        channel.queueDeclare(UEUE_NAME, false, false, false, null);
        //创建message
        String msg = "toov5_message";
        System.out.println("生产者投递消息"+msg);
        //生产者发送消息
        channel.basicPublish("",UEUE_NAME, null, msg.getBytes());
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}
