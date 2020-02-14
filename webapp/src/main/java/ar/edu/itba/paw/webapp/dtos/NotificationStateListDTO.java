package ar.edu.itba.paw.webapp.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class NotificationStateListDTO {

    @NotNull(message = "must proved list of notification states")
    @Size(min = 1, message = "list must be non empty")
    @Valid
    private List<Long> notificationIds;

    public NotificationStateListDTO() {
        //Empty constructor for JAX-RS
    }

    public List<Long> getNotificationIds() {
        return notificationIds;
    }

    public void setNotificationIds(List<Long> notificationIds) {
        this.notificationIds = notificationIds;
    }
}
