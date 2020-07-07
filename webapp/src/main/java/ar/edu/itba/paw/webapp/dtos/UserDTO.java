package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

import javax.validation.constraints.NotNull;
import java.util.Base64;

public class UserDTO {

    private Long id;
    private String userName;
    private String mail;
    private String avatar;
    private Boolean isAdmin = Boolean.FALSE;
    private Boolean isBanned = Boolean.FALSE;

    public UserDTO() {
        //Empty constructor for JAX-RS
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.mail = user.getMailAddress();
        this.isAdmin = user.getIsAdmin();
        this.isBanned = user.getIsBanned();
        if(user.getUserAvatar() != null) {
            this.avatar = Base64.getEncoder().encodeToString(user.getUserAvatar());
        } else {
            this.avatar = "";
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Boolean getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public Boolean getBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

}
