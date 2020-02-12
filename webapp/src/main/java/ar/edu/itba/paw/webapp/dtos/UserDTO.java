package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

import java.util.ArrayList;
import java.util.List;


public class UserDTO {

    private Long id;
    private String userName;
    private String mail;
    private Long pendingNotifications;
    private Boolean isAdmin = Boolean.FALSE;
    private Boolean isBanned = Boolean.FALSE;
    private List<NotificationDTO> notifications = null;

    public UserDTO() {
        //Empty constructor for JAX-RS
    }

    public UserDTO(User user) {
        this.id = user.getId();
        this.userName = user.getUserName();
        this.mail = user.getMailAddress();
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

    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }

    public void setNotifications(User user) {
        setPendingNotifications(user.getNotificationsToView());
        notifications = new ArrayList<>();
        user.getNotifications().stream().forEach(notification -> notifications.add(new NotificationDTO(notification)));
    }


    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Long getPendingNotifications() {
        return pendingNotifications;
    }

    public void setPendingNotifications(Long pendingNotifications) {
        this.pendingNotifications = pendingNotifications;
    }
}
