package ar.edu.itba.paw.model;

public class ReviewSeries {

    private User commenter;
    private String language;
    private String body;
    private Series series;
    private Rating userRating;

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public Rating getUserRating() {
        return userRating;
    }

    public void setUserRating(Rating rating) {
        userRating = rating;
    }

    public void addPoints(int points) {
        userRating.update(points);
    }
}
