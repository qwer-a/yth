package com.fh.rabbitmq.direct;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 单播-配置
 */
@Configuration
public class DirectRabbitConfig {

    /**
     * 创建队列-队列1
     *
     * @return
     */
    @Bean
    public Queue directQueue1() {
        // name=队列的名称
        // durable 是否持久化
        // exclusive 是否独占队列
        // autoDelete 是否自动删除
        // durable:是否持久化,默认是false,持久化队列：会被存储在磁盘上，当消息代理重启时仍然存在，暂存队列：当前连接有效
        // exclusive:默认也是false，只能被当前创建的连接使用，而且当连接关闭后队列即被删除。此参考优先级高于durable
        // autoDelete:是否自动删除，当没有生产者或者消费者使用此队列，该队列会自动删除。
        //   return new Queue("TestDirectQueue",true,true,false);

        //一般设置一下队列的持久化就好,其余两个就是默认false

        return new Queue("directQueue1", true);
    }

    /**
     * 创建队列-队列2
     *
     * @return
     */
    @Bean
    public Queue directQueue2() {
        return new Queue("directQueue2", true);
    }

    /**
     * 创建单播路由
     *
     * @return
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange("direct");
    }

    /**
     * 队列绑定1
     *
     * @return
     */
    @Bean
    public Binding bindingDirectQueue1() {
        // Message的routingKey与其一致 单播建议与队列名称一致
        return BindingBuilder.bind(directQueue1()).to(directExchange()).with("directQueue1");
    }

    /**
     * 队列绑定2
     *
     * @return
     */
    @Bean
    public Binding bindingDirectQueue2() {
        // Message的routingKey与其一致
        return BindingBuilder.bind(directQueue2()).to(directExchange()).with("directQueue2");
    }
}
