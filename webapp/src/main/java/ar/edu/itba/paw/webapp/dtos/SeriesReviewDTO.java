package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReview;
import ar.edu.itba.paw.model.SeriesReviewComment;
import ar.edu.itba.paw.model.User;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

public class SeriesReviewDTO {

    private List<SeriesReviewCommentDTO> seriesReviewComments = Collections.emptyList();
    private UserDTO user;

    @NotNull
    @Size(min = 10, max = 255)
    private String body;

    private Integer likes = 0;
    private Boolean loggedInUserLikes = null;
    private Boolean isSpam = Boolean.FALSE;
    private Long id;

    public SeriesReviewDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewDTO(SeriesReview review, Optional<User> loggedUser) {
        Set<SeriesReviewComment> comments = review.getComments();
        seriesReviewComments = new ArrayList<>(comments.size());
        comments.stream().forEach(comment -> {
            SeriesReviewCommentDTO seriesReviewCommentDTO = new SeriesReviewCommentDTO(comment, loggedUser);
            seriesReviewComments.add(seriesReviewCommentDTO);
        });
        this.id = review.getId();
        this.body = review.getBody();
        this.likes = review.getNumLikes();
        this.isSpam = review.getIsSpam();
        this.user = new UserDTO(review.getUser());
        setUserFields(review, loggedUser);
    }

    public List<SeriesReviewCommentDTO> getSeriesReviewComments() {
        return seriesReviewComments;
    }

    public void setSeriesReviewComments(List<SeriesReviewCommentDTO> seriesReviewComments) {
        this.seriesReviewComments = seriesReviewComments;
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

    private void setUserFields(SeriesReview seriesReview, Optional<User> loggedUser) {
        loggedUser.ifPresent(user -> {
            if(user.getId() != seriesReview.getUser().getId()) {
                if(seriesReview.getLikes().contains(user)) {
                    loggedInUserLikes = Boolean.TRUE;
                } else {
                    loggedInUserLikes = Boolean.FALSE;
                }
            }
        });
    }
}
