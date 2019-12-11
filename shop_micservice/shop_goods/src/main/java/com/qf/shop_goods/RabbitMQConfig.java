package com.qf.shop_goods;

import org.springframework.amqp.core.FanoutExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author 张是非
 * @Date 2019/12/10
 */
@Configuration
public class RabbitMQConfig {

    //@Bean -->把该方法的返回值加入到springboot容器里面去
    @Bean
    public FanoutExchange getExchange(){

        return new FanoutExchange("goods_exchange");
    }
}
