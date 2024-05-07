package com.fastcampus.boardserver.controller;

import com.fastcampus.boardserver.config.AWSConfig;
import com.fastcampus.boardserver.service.SnsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.*;

import java.util.Map;

@Slf4j
@RestController
@RequiredArgsConstructor
public class SnsController {
    private final AWSConfig awsConfig;
    private final SnsService snsService;

    @PostMapping("/create-topic")
    public ResponseEntity<String> createTopic(@RequestParam final String topicName) {
        final CreateTopicRequest createTopicRequest = CreateTopicRequest.builder()
                .name(topicName)
                .build();

        SnsClient snsClient = snsService.getSnsClient();
        final CreateTopicResponse createTopicResponse = snsClient.createTopic(createTopicRequest);

        if(!createTopicResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(createTopicResponse);
        }

        log.info("topic name = " + createTopicResponse.topicArn());
        log.info("topic list = " + snsClient.listTopics());
        snsClient.close();

        return new ResponseEntity<>("TOPIC CREATING SUCCESS", HttpStatus.OK);
    }


    @PostMapping("/subscribe")
    public ResponseEntity<String> subscribe(@RequestParam final String endpoint, @RequestParam final String topicArn) {
        final SubscribeRequest subscribeRequest = SubscribeRequest.builder()
                .protocol("https")
                .topicArn(topicArn)
                .endpoint(endpoint)
                .build();

        SnsClient snsClient = snsService.getSnsClient();
        final SubscribeResponse subscribeResponse = snsClient.subscribe(subscribeRequest);

        if(!subscribeResponse.sdkHttpResponse().isSuccessful()) {
            throw getResponseStatusException(subscribeResponse);
        }

        log.info("topicArn to subscribe = " + subscribeResponse.subscriptionArn());
        log.info("subscription list = " + snsClient.listSubscriptions());

        snsClient.close();

        return new ResponseEntity<>("TOPIC SUBSCRIBE SUCCESS", HttpStatus.OK);
    }

    @PostMapping("/publish")
    public String publish(@RequestParam String topicArn, @RequestBody Map<String, Object> message) {
        SnsClient snsClient = snsService.getSnsClient();

        final PublishRequest publishRequest = PublishRequest.builder()
                .topicArn(topicArn)
                .subject("HTTP ENDPOINT TEST MESSAGE")
                .message(message.toString())
                .build();

        PublishResponse publishResponse = snsClient.publish(publishRequest);
        log.info("message status:" + publishResponse.sdkHttpResponse().statusCode());
        snsClient.close();

        return "send MSG ID = " + publishResponse.messageId();
    }

    private ResponseStatusException getResponseStatusException(SnsResponse response) {
        return new ResponseStatusException(
                HttpStatus.INTERNAL_SERVER_ERROR
                , response.sdkHttpResponse().statusText().get()
        );
    }


    // https://webhook.site 에 들어가서 subscribe에 endpoint 항목을 지정한후에 send를 보내서
    // 크롬에서 혼항목에 subscribe url 항목을 들어가고
    // publish 항목에 arn:aws:sns:ap-northeast-2:781076985467:notifly3 이런 형태에 데이터를 저장 한 후에
    // 데이터를 보내면 된다.


    // slack
//    @GetMapping("/slack/error")
//    public void error() {
//        log.info("슬랙 error 채널 테스트");
//        slackService.sendSlackMessage("슬랙 에러 테스트", "error");
//    }




}
