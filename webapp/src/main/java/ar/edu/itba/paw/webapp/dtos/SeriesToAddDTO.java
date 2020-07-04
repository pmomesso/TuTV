package ar.edu.itba.paw.webapp.dtos;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class SeriesToAddDTO {

    private Long seriesId;

    public SeriesToAddDTO() {}

    public SeriesToAddDTO(Long seriesId) {
        this.seriesId = seriesId;
    }

    public Long getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(Long seriesId) {
        this.seriesId = seriesId;
    }
}
