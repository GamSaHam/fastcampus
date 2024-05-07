package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.config.AWSConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.AwsCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sns.SnsClient;

@Slf4j
@Service
@RequiredArgsConstructor
public class SnsService {
    private final AWSConfig awsConfig;
    public AwsCredentialsProvider getAwsCredentials(String awsAccessKey, String awsSecretKey) {
        AwsBasicCredentials awsBasicCredentials = AwsBasicCredentials.create(awsAccessKey, awsSecretKey);
        return () -> awsBasicCredentials;
    }
    public SnsClient getSnsClient() {
        return SnsClient.builder()
                .credentialsProvider(getAwsCredentials(awsConfig.getAwsAccessKey(), awsConfig.getAwsSecretKey()))
                .region(Region.of(awsConfig.getAwsRegion()))
                .build();
    }

}
