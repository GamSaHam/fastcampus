package com.fastcampus.boardserver.service;

import com.fastcampus.boardserver.dto.constant.SlackConstant;
import com.slack.api.Slack;
import com.slack.api.methods.MethodsClient;
import com.slack.api.methods.SlackApiException;

import com.slack.api.methods.request.chat.ChatPostMessageRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Slf4j
@Service
public class SlackService {

    @Value("${slack.token}")
    private String slackToken;

    public void sendSlackMessage(String message, String channel) {

        String channelAddress = "";

        if(channel.equals("error")) {
            channelAddress = SlackConstant.MONITOR_CHANNEL;
        } else if(channel.equals("warnning")) {
            channelAddress = SlackConstant.WARNING_CHANNEL;
        }

        try {
            MethodsClient methods = Slack.getInstance().methods(slackToken);

            ChatPostMessageRequest request = ChatPostMessageRequest.builder()
                    .channel(channelAddress)
                    .text(message)
                    .build();

            methods.chatPostMessage(request);

            log.info("Slack" + channel + " 에 메시지 보냄");
        } catch (SlackApiException | IOException e) {
            log.error(e.getMessage());
        }
    }
}
