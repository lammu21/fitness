package com.fitness.AIRecommendationService.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fitness.AIRecommendationService.model.Recommendation;
import com.fitness.AIRecommendationService.repository.RecommendationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RecommendationService 
{
    private final RecommendationRepository recommendationRepository;

   

    public List<Recommendation> getUserRecommendations(String userId) 
    {
         return recommendationRepository.findByUserId(userId);
        
    }



    public Recommendation getActivityRecommendations(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
               .orElseThrow(() ->new RuntimeException("Recommendation not found for activityId: " + activityId));

      }
    

}
