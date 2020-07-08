package ar.edu.itba.paw.webapp.dtos;

public class FollowersDTO {

    private int followers;
    private boolean loggedInUserFollows;

    public FollowersDTO() {}

    public FollowersDTO(int followers, boolean loggedInUserFollows) {
        this.followers = followers;
        this.loggedInUserFollows = loggedInUserFollows;
    }

    public int getFollowers() {
        return followers;
    }

    public void setFollowers(int followers) {
        this.followers = followers;
    }

    public boolean isLoggedInUserFollows() {
        return loggedInUserFollows;
    }

    public void setLoggedInUserFollows(boolean loggedInUserFollows) {
        this.loggedInUserFollows = loggedInUserFollows;
    }
}
