package ar.edu.itba.paw.webapp.controller;

import ar.edu.itba.paw.webapp.dtos.constraints.UserEditDTOConstraint;

import javax.validation.constraints.Size;

@UserEditDTOConstraint
public class UserEditDTO {

    private Boolean isBanned;
    @Size(min = 1, max = 32)
    private String userName;

    public UserEditDTO() {
        //Empty constructor for JAX-RS
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
