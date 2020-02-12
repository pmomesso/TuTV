package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.SeriesReview;
import ar.edu.itba.paw.model.User;

import java.util.*;

public class SeriesReviewsDTO {

    private List<SeriesReviewDTO> seriesReviews = Collections.emptyList();

    public SeriesReviewsDTO(Series series, Optional<User> loggedUser) {
        Set<SeriesReview> reviews = series.getSeriesReviewList();
        seriesReviews = new ArrayList<>(reviews.size());
        reviews.stream().forEach(seriesReview -> {
            SeriesReviewDTO seriesReviewDTO = new SeriesReviewDTO(seriesReview, loggedUser);
            seriesReviews.add(seriesReviewDTO);
        });
    }

    public SeriesReviewsDTO() {
        //Empty constructor for JAX-RS
    }

    public List<SeriesReviewDTO> getSeriesReviews() {
        return seriesReviews;
    }

    public void setSeriesReviews(List<SeriesReviewDTO> seriesReviews) {
        this.seriesReviews = seriesReviews;
    }

}
