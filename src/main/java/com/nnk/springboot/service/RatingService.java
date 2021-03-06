package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class RatingService {

    private final RatingRepository ratingRepository;

    public RatingService(RatingRepository ratingRepository){
        this.ratingRepository = ratingRepository;
    }

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

    public boolean deleteRating(int id){
        Rating rating = ratingRepository.findRatingById(id);
        if (rating != null){
            ratingRepository.delete(rating);
            log.info("Rating deleted");
            return true;
        }
        log.error("Deletion failure");
        return false;
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
