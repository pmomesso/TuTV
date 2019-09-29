package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class CommentForm {
    @Size(min = 6, max = 255)
    private String body;
    private long userId;
    private long seriesId;
    private long postId;

    public String getBody() {
        return body;
    }

    public long getUserId() {
        return userId;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }
}