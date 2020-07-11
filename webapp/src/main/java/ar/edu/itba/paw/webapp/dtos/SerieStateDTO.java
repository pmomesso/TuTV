package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class SerieStateDTO {

    @NotNull
    private Integer loggedInUserRating;

    public SerieStateDTO() {
        //Empty constructor for JAX-RS
    }

    public SerieStateDTO(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }

    public Integer getLoggedInUserRating() {
        return loggedInUserRating;
    }

    public void setLoggedInUserRating(Integer loggedInUserRating) {
        this.loggedInUserRating = loggedInUserRating;
    }
}
