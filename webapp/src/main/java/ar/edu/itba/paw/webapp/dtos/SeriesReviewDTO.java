package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReview;

import java.util.Collections;
import java.util.List;

public class SeriesReviewDTO {

    private List<SeriesReviewCommentDTO> seriesReviewDTOList = Collections.emptyList();

    public SeriesReviewDTO() {
        //Empty constructor for JAX-RS
    }

    public List<SeriesReviewCommentDTO> getSeriesReviewDTOList() {
        return seriesReviewDTOList;
    }

    public void setSeriesReviewDTOList(List<SeriesReviewCommentDTO> seriesReviewDTOList) {
        this.seriesReviewDTOList = seriesReviewDTOList;
    }
}
