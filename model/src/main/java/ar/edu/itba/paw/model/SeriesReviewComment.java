package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seriesreviewcomments")
public class SeriesReviewComment {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seriesreviewcomments_id_seq")
    @SequenceGenerator(sequenceName = "seriesreviewcomments_id_seq", name = "seriesreviewcomments_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(length = 255, nullable = false)
    private String body;
    @Column(columnDefinition = "integer default 0")
    private int numlikes;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private SeriesReview parent;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "haslikedseriesreviewcomment",
            joinColumns = { @JoinColumn(name = "seriesreviewcomment") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private Set<User> likes = new HashSet<>();

    public SeriesReviewComment(){
    }
    public SeriesReviewComment(String body, SeriesReview parent, User user){
        this.body = body;
        this.parent = parent;
        this.user = user;
        this.numlikes = 0;
    }

    public int getNumLikes() {
        return numlikes;
    }

    public void setNumLikes(int points) {
        this.numlikes = points;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public long getUserId() {
        return user.getId();
    }

    public SeriesReview getParent() {
        return parent;
    }

    public void setParent(SeriesReview parent) {
        this.parent = parent;
    }

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public void addLike(User user) {
        this.likes.add(user);
        user.getSeriesReviewCommentLikes().add(this);
    }
    public void removeLike(User user) {
        this.likes.remove(user);
        user.getSeriesReviewCommentLikes().remove(this);
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof SeriesReviewComment)){
            return false;
        }
        SeriesReviewComment other = (SeriesReviewComment)obj;
        return this.id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
