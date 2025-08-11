package com.menes.banking.profile_service.config;

import com.menes.banking.profile_service.config.props.ProfileEventProps;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@RequiredArgsConstructor
@Configuration
public class RabbitConfig {

    private final ProfileEventProps profileEventProps;

    @Bean
    public TopicExchange profileExchange() {
        return new TopicExchange(profileEventProps.getExchange(), true, false);
    }

    @Bean
    public Queue profileQueue() {
        return QueueBuilder.durable(profileEventProps.getQueue())
                .withArgument("x-dead-letter-exchange", "dlx.ex")
                .withArgument("x-dead-letter-routing-key", "dlx.profile.event")
                .build();
    }

    @Bean
    public Binding bindProfileQueue() {
        return BindingBuilder.bind(profileQueue())
                .to(profileExchange())
                .with(profileEventProps.getRoutingKey());
    }

    // DLX + DLQ (optional)
    @Bean
    public TopicExchange dlx() {
        return new TopicExchange("dlx.ex");
    }

    @Bean
    public Queue profileDLQ() {
        return QueueBuilder.durable("profile.event.dlq").build();
    }

    @Bean
    public Binding bindProfileDLQ() {
        return BindingBuilder.bind(profileDLQ()).to(dlx()).with("dlx.profile.event");
    }

    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
