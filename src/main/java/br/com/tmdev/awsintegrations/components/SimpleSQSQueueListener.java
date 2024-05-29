package br.com.tmdev.awsintegrations.components;

import io.awspring.cloud.sqs.annotation.SqsListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class SimpleSQSQueueListener {

    private static final Logger logger = LoggerFactory.getLogger(SimpleSQSQueueListener.class);

    @SqsListener("test-queue")
    public void listen(String message) {
        logger.info("Message received from SQS: {}", message);
    }
}