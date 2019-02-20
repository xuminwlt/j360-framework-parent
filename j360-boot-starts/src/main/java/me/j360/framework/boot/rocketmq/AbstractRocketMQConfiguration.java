package me.j360.framework.boot.rocketmq;

import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.rocketmq.starter.configuration.RocketMQProperties;
import org.rocketmq.starter.core.producer.RocketMQProducerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

/**
 * @author: min_xu
 * yml config @see rocketmq-starter
 */
public abstract class AbstractRocketMQConfiguration {

    @Autowired
    private RocketMQProperties rocketMQProperties;

    @Bean
    public RocketMQProducerTemplate rocketMQProducerTemplate() throws MQClientException {
        RocketMQProducerTemplate producer = new RocketMQProducerTemplate();
        producer.setProducerGroup(rocketMQProperties.getProducer().getGroup());
        producer.setTimeOut(rocketMQProperties.getProducer().getSendMsgTimeout());
        producer.setOrderlyMessage(true);
        producer.setMessageClass(String.class);
        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        return producer;
    }

    @Bean
    public TransactionMQProducer transactionMQProducer() throws MQClientException {
        TransactionMQProducer producer = new TransactionMQProducer();
        RocketMQProperties.Producer producerConfig = rocketMQProperties.getProducer();
        String groupName = producerConfig.getGroup();
        Assert.hasText(groupName, "[spring.rocketmq.producer.group] must not be null");

        producer.setNamesrvAddr(rocketMQProperties.getNameServer());
        producer.setSendMsgTimeout(producerConfig.getSendMsgTimeout());
        producer.setRetryTimesWhenSendFailed(producerConfig.getRetryTimesWhenSendFailed());
        producer.setRetryTimesWhenSendAsyncFailed(producerConfig.getRetryTimesWhenSendAsyncFailed());
        producer.setMaxMessageSize(producerConfig.getMaxMessageSize());
        producer.setCompressMsgBodyOverHowmuch(producerConfig.getCompressMsgBodyOverHowmuch());
        producer.setRetryAnotherBrokerWhenNotStoreOK(producerConfig.isRetryAnotherBrokerWhenNotStoreOk());
        producer.setTransactionListener(newTxListener());
        producer.setProducerGroup(rocketMQProperties.getProducer().getGroup());
        producer.start();
        return producer;
    }

    protected abstract TransactionListener newTxListener();

}
