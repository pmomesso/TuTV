package ar.edu.itba.paw.model;

public class Rating {

    private double userRating;
    private int numRatings;
    private double numPoints;

    public Rating() {
        numRatings = 0;
        numPoints = 0;
        userRating = -1;
    }

    public double getUserRating() {
        return userRating;
    }

    public void update(double points) {
        numPoints += points;
        numRatings++;
        updateRatings();
    }

    private void updateRatings() {
        userRating = numPoints/numRatings;
    }

}
