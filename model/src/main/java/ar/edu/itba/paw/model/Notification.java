package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "notification")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "notification_id_seq")
    @SequenceGenerator(sequenceName = "notification_id_seq", name = "notification_id_seq", allocationSize = 1)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    private boolean viewed = false;

    @ManyToOne(fetch = FetchType.LAZY)
    private Series resource;

    private String message;

    /* Hibernate*/ public Notification() {}

    public Notification(User user, Series resource, String message) {
        this.user = user;
        this.resource = resource;
        this.message = message;
        this.viewed = false;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public Series getResource() {
        return resource;
    }

    public void setResource(Series resource) {
        this.resource = resource;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
