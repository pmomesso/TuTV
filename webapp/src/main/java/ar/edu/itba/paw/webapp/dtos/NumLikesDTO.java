package ar.edu.itba.paw.webapp.dtos;

public class NumLikesDTO {

    private int numLikes;
    private boolean loggedInUserLikes;

    public NumLikesDTO() {}

    public NumLikesDTO(int numLikes, boolean loggedInUserLikes) {
        this.numLikes = numLikes;
        this.loggedInUserLikes = loggedInUserLikes;
    }

    public int getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(int numLikes) {
        this.numLikes = numLikes;
    }

    public boolean isLoggedInUserLikes() {
        return loggedInUserLikes;
    }

    public void setLoggedInUserLikes(boolean loggedInUserLikes) {
        this.loggedInUserLikes = loggedInUserLikes;
    }
}
