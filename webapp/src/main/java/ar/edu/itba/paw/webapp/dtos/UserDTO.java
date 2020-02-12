package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;


public class UserDTO {

    private Long id;
    private String userName;
    private String mail;
    private Integer pendingNotifications;
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
        this.pendingNotifications = user.getNotifications().size();
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

    public Integer getPendingNotifications() {
        return pendingNotifications;
    }

    public void setNotifications(Integer pendingNotifications) {
        this.pendingNotifications = pendingNotifications;
    }
}
