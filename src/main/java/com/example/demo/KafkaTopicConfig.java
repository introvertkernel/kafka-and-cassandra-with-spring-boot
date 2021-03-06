package com.example.demo;

import org.apache.kafka.clients.admin.AdminClientConfig;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.KafkaAdmin;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaTopicConfig {

    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootStrapServer;

    @Value(value = "${kafka.topic.name}")
    private String topicName;

    /*Required only for non-spring boot apps*/
    /*@Bean
    public KafkaAdmin kafkaAdmin(){
        Map<String,Object> map = new HashMap<>();
        map.put(AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG,bootStrapServer);
        return new KafkaAdmin(map);
    }*/

    @Bean
    public NewTopic newTopic(){
//        return new NewTopic(topicName,1,(short) 1);
        return TopicBuilder.name(topicName).build();
    }
}
