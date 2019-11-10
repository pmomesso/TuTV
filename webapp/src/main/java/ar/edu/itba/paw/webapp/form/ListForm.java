package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class ListForm {
    @Size(max = 50)
    private String name;
    private long userId;

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
}
