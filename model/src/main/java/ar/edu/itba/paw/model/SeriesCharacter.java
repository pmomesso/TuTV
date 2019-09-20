package ar.edu.itba.paw.model;

public class SeriesCharacter {

    private String name;
    private Rating userRating;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating rating) {
        userRating = rating;
    }

}
