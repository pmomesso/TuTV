package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class BanUserDTO {

    @NotNull
    private Boolean banned;

    public BanUserDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getBanned() {
        return banned;
    }

    public void setBanned(Boolean banned) {
        this.banned = banned;
    }
}
