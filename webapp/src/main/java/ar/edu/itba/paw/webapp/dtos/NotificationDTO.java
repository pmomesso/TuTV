package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Notification;

public class NotificationDTO {

    private Long id;
    private Boolean viewedByUser;
    private SeriesDTO seriesDTO;

    public NotificationDTO() {
        //Empty constructor for JAX-RS
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.seriesDTO = new SeriesDTO();
        this.seriesDTO.setId(notification.getResource().getId());
        this.seriesDTO.setName(notification.getResource().getName());
        this.viewedByUser = Boolean.valueOf(notification.getViewed());
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

    public SeriesDTO getSeriesDTO() {
        return seriesDTO;
    }

    public void setSeriesDTO(SeriesDTO seriesDTO) {
        this.seriesDTO = seriesDTO;
    }

}
