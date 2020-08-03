package com.fh.rabbitmq.work;

import com.fh.util.MQConnectionUtils;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class Consumer {

    //队列名称
    private static final String QUEUE_NAME = "test_queue";

    public static void main(String[] args) throws IOException, TimeoutException {
        System.out.println("消费者启动..........");
        //创建新的连接
        Connection connection = MQConnectionUtils.newConnection();
        //创建Channel
        Channel channel = connection.createChannel();
        // 消费者关联队列
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        DefaultConsumer defaultConsumerr = new DefaultConsumer(channel) {
            //监听获取消息
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String msg =new String(body,"UTF-8");
                System.out.println("消费者获取生产者消息："+msg);
            }
        };
        //牵手模式设置  默认自动应答模式  true:自动应答模式
        channel.basicConsume(QUEUE_NAME, true, defaultConsumerr);

              //关闭通道和连接
             // channel.close();
            // connection.close();
    }
}
