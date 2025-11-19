package com.fitness.AIRecommendationService.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.fitness.AIRecommendationService.model.Activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityMessageListener {

    @KafkaListener(topics = "${kafka.topic.name}", groupId = "activity-processor-group-v2")
    public void processActivity(Activity activity)
    {
        log.info("Received activity for processing:{}",activity.getUserId());
    }

}
