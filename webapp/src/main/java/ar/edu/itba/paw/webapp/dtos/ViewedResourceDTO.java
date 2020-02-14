package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class ViewedResourceDTO {

    @NotNull
    private Boolean viewedByUser;

    public ViewedResourceDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

}
