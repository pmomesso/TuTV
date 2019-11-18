package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "notification")
public class Notification implements Comparable {

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
        user.getNotifications().add(this);
        this.resource = resource;
        resource.getNotifications().add(this);
        this.message = message;
        this.viewed = false;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public int compareTo(Object o) {
        if (this == o) return 0;
        if (o == null || getClass() != o.getClass()) return 1;
        Notification that = (Notification) o;
        return (int) (that.getId() - this.getId());
    }
}
