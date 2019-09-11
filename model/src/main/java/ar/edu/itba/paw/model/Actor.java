package ar.edu.itba.paw.model;

public class Actor {

    private String name;
    private int age;

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
}
