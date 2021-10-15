package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingService {

    @Autowired
    private RatingRepository ratingRepository;

    public List<Rating> getAllRating(){
        List<Rating> ratings = ratingRepository.findAll();
        if (!ratings.isEmpty()){
            log.info("Rating(s) found");
            return ratings;
        }
        log.error("No Rating found.");
        return List.of();
    }

    public Rating addRating(Rating rating){
        try{
            Rating savedRating = ratingRepository.save(rating);
            log.info("Rating successfully saved.");
            return savedRating;
        } catch (Exception e){
            log.error(e.getMessage());
            return null;
        }
    }

    public Rating updateRating(Rating rating){
        Rating ratingToUpdate = ratingRepository.findRatingById(rating.getId());
        if (ratingToUpdate != null){
            log.info("Update in progress ...");
            ratingToUpdate.setMoodysRating(rating.getMoodysRating());
            ratingToUpdate.setSandPRating(rating.getSandPRating());
            ratingToUpdate.setFitchRating(ratingToUpdate.getFitchRating());
            ratingToUpdate.setOrderNumber(rating.getOrderNumber());
            return ratingRepository.save(ratingToUpdate);
        }
        log.error("Unable to update the rating.");
        return null;
    }

    public void deleteRating(int id){
        Rating rating = ratingRepository.findRatingById(id);
        if (rating != null){
            ratingRepository.delete(rating);
        } else {
            log.error("Deletion failure");
        }

        log.info("Rating deleted");
    }

    public Rating getRating(int id) {
        Rating rating = ratingRepository.findRatingById(id);
        if (rating != null){
            log.info("Rating found");
            return rating;
        }
        log.error("No Rating found.");
        return null;
    }
}
