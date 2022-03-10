package com.app.crudapp.aws;

import com.amazonaws.services.s3.model.Bucket;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import static com.app.crudapp.aws.AwsClient.getS3Connection;

@Slf4j
public class BucketService {
    @Value("${bucket.name}")
    private static String bucketName;

    public Bucket createBucket(String bucketName) {
        if (getS3Connection().doesBucketExistV2(bucketName)) {
            log.info("Bucket with name: {} is exist", bucketName);
        }
        return getS3Connection().createBucket(bucketName);
    }
}
