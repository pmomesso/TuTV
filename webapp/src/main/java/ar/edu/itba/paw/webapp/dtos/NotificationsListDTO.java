package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

import java.util.ArrayList;
import java.util.List;

public class NotificationsListDTO {

    private Long userId;
    private List<NotificationDTO> notifications;

    public NotificationsListDTO() {
        //Empty constructor for JAX-RS
    }

    public NotificationsListDTO(User user) {
        this.userId = user.getId();
        notifications = new ArrayList<>();
        user.getNotifications().stream().forEach(notification -> {
            notifications.add(new NotificationDTO(notification));
        });
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<NotificationDTO> getNotifications() {
        return notifications;
    }

    public void setNotifications(List<NotificationDTO> notifications) {
        this.notifications = notifications;
    }
}
