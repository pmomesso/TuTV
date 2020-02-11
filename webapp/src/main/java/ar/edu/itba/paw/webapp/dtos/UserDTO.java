package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

public class UserDTO {

    private Long id;
    private String userName;
    private Boolean isAdmin = Boolean.FALSE;
    private Boolean isBanned = Boolean.FALSE;

    public UserDTO() {
        //Empty constructor for JAX-RS
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.isAdmin = user.getIsAdmin();
        this.isBanned = user.getIsBanned();
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

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setBanned(Boolean banned) {
        isBanned = banned;
    }
}
