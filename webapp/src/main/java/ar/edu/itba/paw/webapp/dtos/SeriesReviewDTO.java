package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReview;
import ar.edu.itba.paw.model.SeriesReviewComment;
import ar.edu.itba.paw.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SeriesReviewDTO {

    private List<SeriesReviewCommentDTO> seriesReviewCommentDTOList = Collections.emptyList();
    private UserDTO user;

    @NotNull
    @Size(min = 10, max = 255)
    private String body;

    private Integer likes = 0;
    private Boolean loggedInUserLikes = Boolean.FALSE;
    private Boolean isSpam = Boolean.FALSE;
    private Long id;

    public SeriesReviewDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewDTO(SeriesReview review) {
        Set<SeriesReviewComment> comments = review.getComments();
        seriesReviewCommentDTOList = new ArrayList<>(comments.size());
        comments.stream().forEach(comment -> seriesReviewCommentDTOList.add(new SeriesReviewCommentDTO(comment)));
        this.id = review.getId();
        this.body = review.getBody();
        this.likes = review.getNumLikes();
        this.isSpam = review.getIsSpam();
        this.user = new UserDTO(review.getUser());
    }

    public List<SeriesReviewCommentDTO> getSeriesReviewCommentDTOList() {
        return seriesReviewCommentDTOList;
    }

    public void setSeriesReviewCommentDTOList(List<SeriesReviewCommentDTO> seriesReviewCommentDTOList) {
        this.seriesReviewCommentDTOList = seriesReviewCommentDTOList;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    public Boolean getLoggedInUserLikes() {
        return loggedInUserLikes;
    }

    public void setLoggedInUserLikes(Boolean loggedInUserLikes) {
        this.loggedInUserLikes = loggedInUserLikes;
    }

    public Boolean getSpam() {
        return isSpam;
    }

    public void setSpam(Boolean spam) {
        isSpam = spam;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
