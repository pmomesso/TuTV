package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class ListForm {
    private long id;
    @Size(min = 2, max = 50)
    private String name;
    private long userId;
    private long[] seriesId;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long[] getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(long[] seriesId) {
        this.seriesId = seriesId;
    }
}
