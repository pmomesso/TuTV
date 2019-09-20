package ar.edu.itba.paw.model;

public class CommentEpisode {

    private User commenter;
    private String body;
    private Episode episode;
    private CommentEpisode parent;
    private int points;

    public User getCommenter() {
        return commenter;
    }

    public void setCommenter(User commenter) {
        this.commenter = commenter;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Episode getEpisode() {
        return episode;
    }

    public void setEpisode(Episode episode) {
        this.episode = episode;
    }

    public CommentEpisode getParent() {
        return parent;
    }

    public void setParent(CommentEpisode parent) {
        this.parent = parent;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public void updatePoints(int points) { this.points += points; }
}
