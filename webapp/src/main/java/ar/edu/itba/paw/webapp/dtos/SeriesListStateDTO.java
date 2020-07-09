package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SeriesListStateDTO {

    @NotNull
    @Size(min = 1)
    private String name;

    public SeriesListStateDTO() {
        //Empty constructor for JAX-RS
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}