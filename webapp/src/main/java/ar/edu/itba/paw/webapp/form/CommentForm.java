package ar.edu.itba.paw.webapp.form;

import javax.validation.constraints.Size;

public class CommentForm {
    @Size(min = 6, max = 255)
    private String commentBody;
    private long commentUserId;
    private long commentSeriesId;
    private long commentPostId;

    public String getCommentBody() {
        return commentBody;
    }

    public long getCommentUserId() {
        return commentUserId;
    }

    public long getCommentSeriesId() {
        return commentSeriesId;
    }

    public long getCommentPostId() {
        return commentPostId;
    }

    public void setCommentBody(String commentBody) {
        this.commentBody = commentBody;
    }

    public void setCommentUserId(long commentUserId) {
        this.commentUserId = commentUserId;
    }

    public void setCommentSeriesId(long commentSeriesId) {
        this.commentSeriesId = commentSeriesId;
    }

    public void setCommentPostId(long commentPostId) {
        this.commentPostId = commentPostId;
    }
}