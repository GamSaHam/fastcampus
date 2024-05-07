package com.fastcampus.boardserver.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Getter
@Configuration
public class AWSConfig {
    @Value("${sns.topic.arn}")
    private String snsTopicArn;
    @Value("${aws.accessKey}")
    private String awsAccessKey;
    @Value("${aws.secretKey}")
    private String awsSecretKey;
    @Value("${aws.region}")
    private String awsRegion;


}
