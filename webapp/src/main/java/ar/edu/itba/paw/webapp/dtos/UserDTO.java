package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

public class UserDTO {

    private Long id;
    private String userName;
    private boolean isAdmin = false;
    private boolean isBanned = false;

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

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean isBanned() {
        return isBanned;
    }

    public void setBanned(boolean banned) {
        isBanned = banned;
    }
}
