package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class FollowSeriesDTO {

    @NotNull
    private Long seriesId;

    public FollowSeriesDTO() {}

    public FollowSeriesDTO(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
