package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Series;
import ar.edu.itba.paw.model.SeriesReview;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SeriesReviewsDTO {

    private List<SeriesReviewDTO> seriesReviews = Collections.emptyList();

    public SeriesReviewsDTO(Series series) {
        Set<SeriesReview> reviews = series.getSeriesReviewList();
        seriesReviews = new ArrayList<>(reviews.size());
        reviews.stream().forEach(seriesReview -> seriesReviews.add(new SeriesReviewDTO(seriesReview)));
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
