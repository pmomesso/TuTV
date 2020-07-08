package ar.edu.itba.paw.webapp.dtos;

public class RatingDTO {

    private Double seriesRating;
    private Integer userRating;

    public RatingDTO() {}

    public RatingDTO(Double seriesRating, Integer userRating) {
        this.seriesRating = seriesRating;
        this.userRating = userRating;
    }

    public Integer getUserRating() {
        return userRating;
    }

    public void setUserRating(Integer userRating) {
        this.userRating = userRating;
    }

    public Double getSeriesRating() {
        return seriesRating;
    }

    public void setSeriesRating(Double seriesRating) {
        this.seriesRating = seriesRating;
    }
}
