package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.SeriesReviewComment;
import ar.edu.itba.paw.model.User;

import java.util.Optional;

public class SeriesReviewCommentDTO {

    private Long id;
    private String body;
    private Integer numLikes;
    private Boolean loggedInUserLikes = null;
    private UserDTO user;

    public SeriesReviewCommentDTO() {
        //Empty constructor for JAX-RS
    }

    public SeriesReviewCommentDTO(SeriesReviewComment comment, Optional<User> loggedUser) {
        this.id = comment.getId();
        this.body = comment.getBody();
        this.numLikes = comment.getNumLikes();
        this.user = new UserDTO(comment.getUser());
        setUserFields(comment, loggedUser);
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

    public UserDTO getUser() {
        return user;
    }

    public void setUser(UserDTO user) {
        this.user = user;
    }

    private void setUserFields(SeriesReviewComment comment, Optional<User> loggedUser) {
        loggedUser.ifPresent(user -> {
            if(comment.getUser().getId() != user.getId()) {
                if(comment.getLikes().contains(user)) {
                    loggedInUserLikes = Boolean.FALSE;
                } else {
                    loggedInUserLikes = Boolean.TRUE;
                }
            }
        });
    }

}
