package com.hz.configurer;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by huangzhe on 2017/12/11.
 */
//@Configuration
public class RabbitConfig {
    @Bean
    public Queue tbiQueue() {
        return new Queue("elk_queue",true);
    }
}
