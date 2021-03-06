package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Notification;

public class NotificationDTO {

    private Long id;
    private Boolean viewedByUser;
    private SeriesDTO series;
    private String message;

    public NotificationDTO() {
        //Empty constructor for JAX-RS
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.series = new SeriesDTO();
        this.series.setId(notification.getResource().getId());
        this.series.setName(notification.getResource().getName());
        this.viewedByUser = notification.getViewed();
        this.message = notification.getMessage();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getViewedByUser() {
        return viewedByUser;
    }

    public void setViewedByUser(Boolean viewedByUser) {
        this.viewedByUser = viewedByUser;
    }

    public SeriesDTO getSeries() {
        return series;
    }

    public void setSeries(SeriesDTO series) {
        this.series = series;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
