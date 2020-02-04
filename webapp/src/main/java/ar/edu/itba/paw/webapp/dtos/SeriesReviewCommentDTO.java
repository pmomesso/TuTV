package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReviewComment;

public class SeriesReviewCommentDTO {

    private String body;
    private Integer numLikes;

    public SeriesReviewCommentDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewCommentDTO(SeriesReviewComment comment) {
        this.body = comment.getBody();
        this.numLikes = comment.getNumLikes();
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getNumLikes() {
        return numLikes;
    }

    public void setNumLikes(Integer numLikes) {
        this.numLikes = numLikes;
    }
}
