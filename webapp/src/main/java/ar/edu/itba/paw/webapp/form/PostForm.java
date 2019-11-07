package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class PostForm {
    @Size(min = 6, max = 255)
    private String body;
    private long seriesId;
    private boolean isSpam;

    public String getBody() {
        return body;
    }

    public long getSeriesId() {
        return seriesId;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public void setSeriesId(long seriesId) {
        this.seriesId = seriesId;
    }

    public boolean getIsSpam() {
        return isSpam;
    }

    public void setIsSpam(boolean isSpam) {
        this.isSpam = isSpam;
    }
}
