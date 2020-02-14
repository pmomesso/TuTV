package ar.edu.itba.paw.webapp.dtos;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

public class SerieStateListDTO {

    @NotNull
    @Size(min = 1)
    @Valid
    private List<SerieStateDTO> serieStateDTOList;

    public SerieStateListDTO() {
        //Empty constructor for JAX-RS
    }

    public List<SerieStateDTO> getSerieStateDTOList() {
        return serieStateDTOList;
    }

    public void setSerieStateDTOList(List<SerieStateDTO> serieStateDTOList) {
        this.serieStateDTOList = serieStateDTOList;
    }
}
