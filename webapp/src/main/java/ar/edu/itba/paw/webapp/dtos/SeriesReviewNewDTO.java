package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SeriesReviewNewDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String body;
    @NotNull
    private Boolean isSpam;

    public SeriesReviewNewDTO() {
        //Empty constructor for JAX-RS
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Boolean getIsSpam() {
        return isSpam;
    }

    public void setIsSpam(Boolean isSpam) {
        this.isSpam = isSpam;
    }
}