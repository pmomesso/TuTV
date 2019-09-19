package ar.edu.itba.paw.model;

public class ActorRole {

    private String role;
    private Actor actor;
    private Series series;

    public ActorRole(String role, Actor actor, Series series) {
        this.role = role;
        this.actor = actor;
        this.series = series;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
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
