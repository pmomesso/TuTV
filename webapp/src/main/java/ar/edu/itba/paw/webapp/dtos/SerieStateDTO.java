package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.webapp.dtos.constraints.SerieStateDTOConstraint;

import javax.validation.constraints.NotNull;

@SerieStateDTOConstraint
public class SerieStateDTO {
    @NotNull
    private Integer loggedInUserRating;

    //private Boolean loggedInUserFollows;

    public SerieStateDTO() {
        //Empty constructor for JAX-RS
    }

    public SerieStateDTO(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }

    /*public Boolean getLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public void setLoggedInUserFollows(Boolean loggedInUserFollows) {
        this.loggedInUserFollows = loggedInUserFollows;
    }*/

    public Integer getLoggedInUserRating() {
        return loggedInUserRating;
    }

    public void setLoggedInUserRating(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }
}
