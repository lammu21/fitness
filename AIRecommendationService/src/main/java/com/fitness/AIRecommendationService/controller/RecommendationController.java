package com.fitness.AIRecommendationService.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

import com.fitness.AIRecommendationService.model.Recommendation;
import com.fitness.AIRecommendationService.service.*;;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/recommendations")
public class RecommendationController
{
     private final RecommendationService recommendationService;

    @GetMapping("/user/{userid}")
    public ResponseEntity<List<Recommendation>> getUserRecommendations(@PathVariable String userid)
    {
        return ResponseEntity.ok(recommendationService.getUserRecommendations(userid));

    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable String activityId)
    {
        return ResponseEntity.ok(recommendationService.getActivityRecommendations(activityId));
    }


}
