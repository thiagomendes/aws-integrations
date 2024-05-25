package br.com.tmdev.awsintegrations.runner;

import br.com.tmdev.awsintegrations.components.S3Integration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

@Component
public class S3IntegrationRunner implements CommandLineRunner {

    private S3Integration s3Integration;

    private static final Logger logger = LoggerFactory.getLogger(S3IntegrationRunner.class);

    @Autowired
    public S3IntegrationRunner(S3Integration s3Integration) {
        this.s3Integration = s3Integration;
    }

    @Override
    public void run(String... args) throws Exception {
        String bucketName = "my-test-bucket";
        logger.info("Creating bucket: {}", bucketName);
        s3Integration.createBucket(bucketName);

        Path tempFile = Files.createTempFile("upload-", ".txt");
        Files.writeString(tempFile, "This is a test file");

        String uniqueFileName = UUID.randomUUID().toString() + ".txt";
        logger.info("Uploading file: {}", uniqueFileName);
        s3Integration.upload(bucketName, uniqueFileName, tempFile.toFile());

        logger.info("Listing files in bucket: {}", bucketName);
        List<String> files = s3Integration.listFiles(bucketName);
        logger.info("Files in {}: {}", bucketName, files);

        logger.info("Clearing bucket: {}", bucketName);
        s3Integration.clearBucket(bucketName);

        logger.info("Deleting bucket: {}", bucketName);
        s3Integration.deleteBucket(bucketName);
        logger.info("Bucket {} deleted", bucketName);
    }
}