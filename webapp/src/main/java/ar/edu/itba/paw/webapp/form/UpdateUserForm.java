package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class UpdateUserForm {

    @Size(min = 6, max = 32)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
