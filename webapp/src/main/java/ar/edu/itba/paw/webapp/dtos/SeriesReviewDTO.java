package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReview;
import ar.edu.itba.paw.model.SeriesReviewComment;
import ar.edu.itba.paw.model.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

public class SeriesReviewDTO {

    private List<SeriesReviewCommentDTO> seriesReviewCommentDTOList = Collections.emptyList();
    private UserDTO user;
    private String body;
    private Integer likes;
    private boolean isSpam;

    public SeriesReviewDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewDTO(SeriesReview review) {
        Set<SeriesReviewComment> comments = review.getComments();
        seriesReviewCommentDTOList = new ArrayList<>(comments.size());
        comments.stream().forEach(comment -> seriesReviewCommentDTOList.add(new SeriesReviewCommentDTO(comment)));
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

    public boolean isSpam() {
        return isSpam;
    }

    public void setSpam(boolean spam) {
        isSpam = spam;
    }

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }
}
