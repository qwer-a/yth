package com.fh.util;

import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class MQConnectionUtils {

    //创建新的连接
    public static Connection newConnection() throws IOException, TimeoutException {
        //创建连接工厂
        ConnectionFactory factory= new ConnectionFactory();
        //链接地址
        factory.setHost("192.168.174.132");
        //用户名称
        factory.setUsername("admin");
        //用户密码
        factory.setPassword("admin");
        //amqp端口号
        factory.setPort(5672);
        //连接virtualhost
        factory.setVirtualHost("/");
        Connection connection = factory.newConnection();
        return connection;
    }
}
