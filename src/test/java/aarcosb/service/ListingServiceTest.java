package aarcosb.service;

import aarcosb.model.entity.Listing;
import aarcosb.model.repository.ListingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import java.lang.reflect.Field;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Pageable;
import static org.mockito.Mockito.*;
import org.springframework.data.domain.PageImpl;
import java.util.Collections;

public class ListingServiceTest {
    @Mock private ListingRepository listingRepository;
    private ListingService listingService;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        listingService = new ListingService();
        Field listingRepoField = ListingService.class.getDeclaredField("listingRepository");
        listingRepoField.setAccessible(true);
        listingRepoField.set(listingService, listingRepository);
    }

    @Test
    void searchAndFilter_callsRepository() {
        when(listingRepository.searchAndFilter(any(), any(), any(), any(), any(), any()))
            .thenReturn(new PageImpl<>(Collections.emptyList()));
        listingService.searchAndFilter(null, null, null, null, null, mock(Pageable.class));
        verify(listingRepository).searchAndFilter(any(), any(), any(), any(), any(), any());
    }

    @Test
    void getListingById_callsRepository() {
        listingService.getListingById(1L);
        verify(listingRepository).findById(1L);
    }

    @Test
    void updateListing_callsRepository() {
        Listing listing = new Listing();
        listing.setId(1L);
        when(listingRepository.save(any())).thenAnswer(i -> i.getArgument(0));
        listingService.updateListing(listing);
        verify(listingRepository).save(listing);
    }

    @Test
    void countTotalFavorites_callsRepository() {
        listingService.countTotalFavorites(1L);
        verify(listingRepository).countTotalFavorites(1L);
    }
}
