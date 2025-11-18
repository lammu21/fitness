package com.example.activityservice.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.activityservice.model.Activity;

@Repository
public interface ActivityRepository extends MongoRepository<Activity, String> 
{

    
}
