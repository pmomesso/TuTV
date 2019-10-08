package ar.edu.itba.paw.model;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class ActorRole {

    private List<SeriesCharacter> roles = Collections.emptyList();
    private Actor actor;
    private Series series;

    public ActorRole(Actor actor, Series series) {
        this.actor = actor;
        this.series = series;
    }

    public ActorRole() {}

    public List<SeriesCharacter> getRoles() {
        return roles;
    }

    public void addRole(String role) {
        SeriesCharacter sc = new SeriesCharacter();
        sc.setName(role);
        roles.add(sc);
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }
}
