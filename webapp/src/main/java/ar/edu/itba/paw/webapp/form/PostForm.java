package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class PostForm {
    @Size(min = 6, max = 255)
    private String description;
    private long userId;
    private long seriesId;

    public String getDescription() {
        return description;
    }

    public long getUserId() {
        return userId;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }
}
