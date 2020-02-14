package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class SeriesReviewCommentNewDTO {

    @NotNull
    @Size(min = 1, max = 255)
    private String body;

    public SeriesReviewCommentNewDTO() {
        //Empty constructor for JAX-RS
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

}
