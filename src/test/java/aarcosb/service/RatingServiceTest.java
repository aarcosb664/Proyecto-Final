package aarcosb.service;

import aarcosb.model.entity.Rating;
import aarcosb.model.repository.RatingRepository;
import aarcosb.model.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.lang.reflect.Field;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RatingServiceTest {
    @Mock private RatingRepository ratingRepository;
    @Mock private ListingRepository listingRepository;
    private RatingService ratingService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        ratingService = new RatingService();
        Field ratingRepoField = RatingService.class.getDeclaredField("ratingRepository");
        ratingRepoField.setAccessible(true);
        ratingRepoField.set(ratingService, ratingRepository);
        Field listingRepoField = RatingService.class.getDeclaredField("listingRepository");
        listingRepoField.setAccessible(true);
        listingRepoField.set(ratingService, listingRepository);
    }

    @Test
    void getAverageRating_returnsAverageRating() {
        Long listingId = 1L;
        double averageRating = 4.5;
        when(ratingRepository.findAverageRatingByListingId(listingId)).thenReturn(averageRating);
        double result = ratingService.getAverageRating(listingId);
        assertEquals(averageRating, result);
    }

    @Test
    void getUserRating_returnsUserRating() {
        Long listingId = 1L;
        Long userId = 1L;
        Rating rating = new Rating();
        rating.setListingId(listingId);
        rating.setUserId(userId);
        rating.setRatingValue(4.5);
        when(ratingRepository.findByListingIdAndUserId(listingId, userId)).thenReturn(rating);
        double result = ratingService.getUserRating(listingId, userId);
        assertEquals(rating.getRatingValue(), result);
    }

    @Test
    void rateListing_newRating_returnsSavedRating() {
        when(ratingRepository.findByListingIdAndUserId(1L, 1L)).thenReturn(null);
        when(ratingRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        when(ratingRepository.findAverageRatingByListingId(1L)).thenReturn(4.0);
        Rating result = ratingService.rateListing(1L, 1L, 4.0);
        assertNotNull(result);
        assertEquals(4.0, result.getRatingValue());
    }

    @Test
    void rateListing_updateRating_returnsUpdatedRating() {
        Rating existing = new Rating();
        existing.setRatingValue(3.0);
        when(ratingRepository.findByListingIdAndUserId(1L, 1L)).thenReturn(existing);
        when(ratingRepository.save(existing)).thenReturn(existing);
        when(ratingRepository.findAverageRatingByListingId(1L)).thenReturn(4.0);
        Rating result = ratingService.rateListing(1L, 1L, 4.0);
        assertNotNull(result);
        assertEquals(4.0, result.getRatingValue());
    }

    @Test
    void rateListing_sameValue_deletesRatingAndReturnsNull() {
        Rating existing = new Rating();
        existing.setRatingValue(4.5);
        when(ratingRepository.findByListingIdAndUserId(1L, 1L)).thenReturn(existing);
        when(ratingRepository.findAverageRatingByListingId(1L)).thenReturn(4.5);
        Rating result = ratingService.rateListing(1L, 1L, 4.5);
        verify(ratingRepository).delete(existing);
        assertNull(result);
    }
}