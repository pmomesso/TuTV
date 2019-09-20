package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

public class LoginForm {
    @NotNull
    @Size(min = 6, max = 32)
    @Pattern(regexp = "[a-zA-Z0-9]+")
    private String username;

    @NotNull
    @Size(min = 8, max = 32)
    private String password;

    @NotNull
    private boolean rememberme;

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

    public boolean getRememberme() { return rememberme; }

    public void setRememberme(boolean rememberme) { this.rememberme = rememberme; }
}
