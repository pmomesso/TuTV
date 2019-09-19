package ar.edu.itba.paw.model;

import java.util.Date;

public class Actor {

    private String name;
    private int age;
    private double actorUserRating;
    private Date created;
    private Date updated;

    public Actor(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public Actor(String name) {
        this.name = name;
        this.age = -1;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public double getActorUserRating() {
        return this.actorUserRating;
    }

    public void setActorUserRating(double actorUserRating) {
        this.actorUserRating = actorUserRating;
    }

}
