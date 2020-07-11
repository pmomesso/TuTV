package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class SeriesReviewCommentStateDTO {

    @NotNull
    private Boolean loggedInUserLikes;

    public SeriesReviewCommentStateDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getLoggedInUserLikes() {
        return loggedInUserLikes;
    }

    public void setLoggedInUserLikes(Boolean loggedInUserLikes) {
        this.loggedInUserLikes = loggedInUserLikes;
    }

}
