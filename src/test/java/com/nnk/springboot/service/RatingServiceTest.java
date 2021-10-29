package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;

    @Test
    public void return_all_ratings(){
        when(ratingRepository.findAll()).thenReturn(List.of(new Rating()));
        List<Rating> ratings = ratingService.getAllRating();

        assertThat(ratings).isNotEmpty();
    }

    @Test
    public void return_empty_rating_list(){
        when(ratingRepository.findAll()).thenReturn(List.of());
        List<Rating> ratings = ratingService.getAllRating();

        assertThat(ratings).isEmpty();
    }

    @Test
    public void add_rating_ok(){
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating);
        Rating savedRating = ratingService.addRating(rating);

        assertThat(savedRating).isNotNull();
    }

    @Test
    public void update_rating_ok(){
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setId(1);
        Rating rating2 = new Rating("Updated Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        when(ratingRepository.findRatingById(anyInt())).thenReturn(rating);
        when(ratingRepository.save(any(Rating.class))).thenReturn(rating2);
        Rating updatedRating = ratingService.updateRating(rating);

        assertThat(updatedRating.getMoodysRating()).isEqualTo("Updated Moodys Rating");
    }

    @Test
    public void update_rating_failure(){
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setId(1);
        when(ratingRepository.findRatingById(anyInt())).thenReturn(null);
        Rating updatedRating = ratingService.updateRating(rating);

        assertThat(updatedRating).isNull();
    }

    @Test
    public void delete_rating_ok(){
        Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);
        rating.setId(1);
        when(ratingRepository.findRatingById(anyInt())).thenReturn(rating);
        doNothing().when(ratingRepository).delete(any(Rating.class));

        assertThat(ratingService.deleteRating(1)).isTrue();
    }

    @Test
    public void delete_rating_failure(){
        when(ratingRepository.findRatingById(anyInt())).thenReturn(null);

        assertThat(ratingService.deleteRating(1)).isFalse();
    }

    @Test
    public void get_rating_ok(){
        when(ratingRepository.findRatingById(anyInt())).thenReturn(new Rating());

        assertThat(ratingService.getRating(1)).isNotNull();
    }

    @Test
    public void get_rating_failure(){
        when(ratingRepository.findRatingById(anyInt())).thenReturn(null);

        assertThat(ratingService.getRating(1)).isNull();
    }
}
