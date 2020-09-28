package com.demo.gateway2x.config;

import com.demo.gateway2x.decorator.PayloadServerWebExchangeDecorator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.web.server.WebFilter;

@Configuration
public class GatewayConfig {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE) //过滤器顺序
    public WebFilter webFilter() {
        return (exchange, chain) -> chain.filter(new PayloadServerWebExchangeDecorator(exchange));
    }

}
