package ar.edu.itba.paw.webapp.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NotificationStateDTO {

    @NotNull
    private Boolean viewedByUser;

    public NotificationStateDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }
}
