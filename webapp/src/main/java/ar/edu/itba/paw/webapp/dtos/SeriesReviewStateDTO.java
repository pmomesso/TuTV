package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class SeriesReviewStateDTO {

    @NotNull
    private Boolean loggedInUserLikes;

    public SeriesReviewStateDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getLoggedInUserLikes() {
        return loggedInUserLikes;
    }

    public void setLoggedInUserLikes(Boolean loggedInUserLikes) {
        this.loggedInUserLikes = loggedInUserLikes;
    }

}
