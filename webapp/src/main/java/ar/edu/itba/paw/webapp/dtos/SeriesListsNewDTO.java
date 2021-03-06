package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SeriesListsNewDTO {

    @NotNull
    @Size(min = 1)
    private String name;

    private List<Long> series;

    public SeriesListsNewDTO() {
        //Empty constructor for JAX-RS
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getSeries() {
        return series;
    }

    public void setSeries(List<Long> series) {
        this.series = series;
    }
}
