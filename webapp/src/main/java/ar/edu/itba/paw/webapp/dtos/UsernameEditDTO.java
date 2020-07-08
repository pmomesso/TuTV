package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UsernameEditDTO {

    @NotNull
    @Size(min = 1, max = 32)
    private String userName;

    public UsernameEditDTO() {
        //Empty constructor for JAX-RS
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
