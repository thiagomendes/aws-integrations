package br.com.tmdev.awsintegrations.components;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.stereotype.Component;

@Component
public class SQSPublisher {

    private final SqsTemplate sqsTemplate;

    public SQSPublisher(SqsTemplate sqsTemplate) {
        this.sqsTemplate = sqsTemplate;
    }

    public void sendMessage(String queueName, String message) {
        sqsTemplate.send(queueName, message);
    }
}