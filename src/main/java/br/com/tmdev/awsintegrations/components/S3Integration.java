package br.com.tmdev.awsintegrations.components;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.File;
import java.util.List;

@Component
public class S3Integration {

    private S3Client s3Client;

    @Autowired
    public S3Integration(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    public void upload(String bucketName, String fileName, File file) {
        s3Client.putObject(r -> r.bucket(bucketName).key(fileName), file.toPath());
    }

    public List<String> listFiles(String bucketName) {
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();
        ListObjectsV2Response listObjResponse = s3Client.listObjectsV2(listObjectsReqManual);
        List<S3Object> objects = listObjResponse.contents();
        return objects.stream()
                .map(S3Object::key)
                .toList();
    }

    public void createBucket(String bucketName) {
        CreateBucketRequest createBucketRequest = CreateBucketRequest
                .builder()
                .bucket(bucketName)
                .build();

        s3Client.createBucket(createBucketRequest);
    }

    public void deleteBucket(String bucketName) {
        DeleteBucketRequest deleteBucketRequest = DeleteBucketRequest
                .builder()
                .bucket(bucketName)
                .build();

        s3Client.deleteBucket(deleteBucketRequest);
    }

    public void clearBucket(String bucketName) {
        ListObjectsV2Request listObjectsReqManual = ListObjectsV2Request.builder()
                .bucket(bucketName)
                .build();

        ListObjectsV2Response listObjResponse = s3Client.listObjectsV2(listObjectsReqManual);

        for (S3Object s3Object : listObjResponse.contents()) {
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket(bucketName)
                    .key(s3Object.key())
                    .build();

            s3Client.deleteObject(deleteObjectRequest);
        }
    }
}
