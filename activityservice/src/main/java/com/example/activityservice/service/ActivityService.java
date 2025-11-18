package com.example.activityservice.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.example.activityservice.dto.ActivityRequest;
import com.example.activityservice.dto.ActivityResponse;
import com.example.activityservice.model.Activity;
import com.example.activityservice.repository.ActivityRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ActivityService 
{
     private final ActivityRepository activityRepository;

     private final UserValidationService userValidationService;

     private final KafkaTemplate<String,Activity> kafkaTemplate;

     @Value("${kafka.topic.name}")
     private String topicName;

    public ActivityResponse trackActivity(ActivityRequest request) 
    {

       boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if(!isValidUser)
        {
            throw new RuntimeException("Invalid User :" +request.getUserId());
        }

        Activity activity = Activity.builder()
            .userId(request.getUserId())
            .type(request.getType())
            .duration(request.getDuration())
            .caloriesBurned(request.getCaloriesBurned())
            .starTime(request.getStarTime())
            .additionalMetrics(request.getAdditionalMetrics())
            .build();

            Activity savedActivity = activityRepository.save(activity);

            try {
            kafkaTemplate.send(topicName,savedActivity.getUserId(),savedActivity);
                
            } catch (Exception e) {
                
                e.printStackTrace();
            }
        
            
            return  mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) 
    {
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStarTime(activity.getStarTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        return response;
    }


}
