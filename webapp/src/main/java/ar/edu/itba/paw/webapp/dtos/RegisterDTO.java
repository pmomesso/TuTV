package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegisterDTO {

    @NotNull
    private String mail;
    @NotNull
    private String username;

    @NotNull
    @Size(min = 8)
    private String password;

    public RegisterDTO() {
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
