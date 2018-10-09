package uz.oltinolma.producer.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitMessagingTemplate;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableRabbit
public class RabbitConfiguration {

    @Value("${rabbitmq.host}")
    String hostname;

    @Value("${rabbitmq.username}")
    String username;

    @Value("${rabbitmq.password}")
    String password;

    @Bean
    public ConnectionFactory connectionFactory() {
        CachingConnectionFactory connectionFactory =
                new CachingConnectionFactory(hostname);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);
        connectionFactory.setPort(5672);
        return connectionFactory;
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }


    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate rabbitTemplate() {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean(name = "htemplate")
    public RabbitMessagingTemplate rmtemplate() {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        RabbitMessagingTemplate rabbitMessagingTemplate = new RabbitMessagingTemplate(rabbitTemplate);
        rabbitMessagingTemplate.setAmqpMessageConverter(jsonMessageConverter());
        return rabbitMessagingTemplate;
    }

    @Bean
    public Queue sendQueue() {
        return new Queue("kinoman.send");
    }

    @Bean
    public Queue requestQueue() {
        return new Queue("kinoman.request");
    }

    @Bean
    TopicExchange topicExchange() {
        return new TopicExchange("kinomanTopicExchange");
    }

    @Bean
    Binding topicBind(Queue sendQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(sendQueue).to(topicExchange).with("send.#");
    }

    @Bean
    Binding topicBind2(Queue requestQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(requestQueue).to(topicExchange).with("request.#");
    }


}
