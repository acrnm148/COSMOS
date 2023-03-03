package com.cosmos.back.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Configuration
public class RedisConfig {

    @Value("${spring.redis.host}")
    private String host;

    @Value("${spring.redis.port}")
    private int port;

    @Bean
    public RedisConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory(host, port);
    }

    /**
     * 어플리케이션에서 사용할 redisTemplate 설정
     */
    @Bean
    public RedisTemplate<?,?> redisTemplate() {
        RedisTemplate<?,?> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(redisConnectionFactory());
        return redisTemplate;
    }

    /**
     * 단일 Topic 사용을 위한 Bean 설정 =>
     * 일원화로 불필요한 자원 낭비 막고, RedisPublisher가 필요 없어짐

    @Bean
    public ChannelTopic channelTopic() {
        return new ChannelTopic("chatroom");
    }
     */
    /**
     * redis pub/sub 메시지를 처리하는 listener 설정

    @Bean
    public RedisMessageListenerContainer redisMessageListener(RedisConnectionFactory connectionFactory,
                                                              MessageListenerAdapter listenerAdapter,
                                                              ChannelTopic channelTopic) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.addMessageListener(listenerAdapter, channelTopic);
        return container;
    }
     */

    /**
     * 실제 메시지를 처리하는 subscriber 설정 추가
     * RedisSubscriber : Redis 발행 서비스
    @Bean
    public MessageListenerAdapter listenerAdapter(RedisSubscriber subscriber) { //RedisSubscriber만들어줘야함
        return new MessageListenerAdapter(subscriber, "sendMessage");
    }
    */
}
