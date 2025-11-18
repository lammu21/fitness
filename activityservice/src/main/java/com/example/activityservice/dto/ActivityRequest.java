package com.example.activityservice.dto;

import java.time.LocalDateTime;
import java.util.Map;

import com.example.activityservice.model.ActivityType;

import lombok.Data;

@Data
public class ActivityRequest 
{

    private String userId;
    private ActivityType type;
    private Integer duration;
    private Integer caloriesBurned;
    private LocalDateTime starTime;
    private Map<String,Object> additionalMetrics;



}
