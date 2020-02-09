package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReviewComment;

public class SeriesReviewCommentDTO {

    private Long id;
    private String body;
    private Integer numLikes;
    private Boolean loggedInUserLikes;

    public SeriesReviewCommentDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewCommentDTO(SeriesReviewComment comment) {
        this.id = comment.getId();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getLoggedInUserLikes() {
        return loggedInUserLikes;
    }

    public void setLoggedInUserLikes(Boolean loggedInUserLikes) {
        this.loggedInUserLikes = loggedInUserLikes;
    }
}
