package br.com.tmdev.awsintegrations.runner;

import br.com.tmdev.awsintegrations.components.SQSPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SQSPublisherRunner implements CommandLineRunner {

    private static final Logger logger = LoggerFactory.getLogger(SQSPublisherRunner.class);

    private final SQSPublisher sqsPublisher;

    public SQSPublisherRunner(SQSPublisher sqsPublisher) {
        this.sqsPublisher = sqsPublisher;
    }

    @Override
    public void run(String... args) throws Exception {
        logger.info("Starting SQS Publisher Runner...");
        sqsPublisher.sendMessage("test-queue", "Hello, SQS!");
        logger.info("Message sent to SQS queue 'test-queue'");
    }
}