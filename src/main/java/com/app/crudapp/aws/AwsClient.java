package com.app.crudapp.aws;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.beans.factory.annotation.Value;

public class AwsClient {
    @Value("${aws.access.key}")
    private String awsAccessKey;
    @Value("${aws.secret.key}")
    private String awsSecretKey;

    public AmazonS3 getS3Connection() {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsSecretKey);

        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }
}
