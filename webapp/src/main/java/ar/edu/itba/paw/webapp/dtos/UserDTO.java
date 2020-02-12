package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Notification;
import ar.edu.itba.paw.model.User;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


public class UserDTO {

    private Long id;
    private String userName;
    private String mail;
    private Integer pendingNotifications;
    private Boolean isAdmin = Boolean.FALSE;
    private Boolean isBanned = Boolean.FALSE;
    private List<NotificationDTO> notificationDTOList = null;

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

    public List<NotificationDTO> getNotificationDTOList() {
        return notificationDTOList;
    }

    public void setNotificationDTOList(List<NotificationDTO> notificationDTOList) {
        this.notificationDTOList = notificationDTOList;
    }

    public void setNotifications(User user) {
        notificationDTOList = new ArrayList<>();
        user.getNotifications().stream().forEach(notification -> notificationDTOList.add(new NotificationDTO(notification)));
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
