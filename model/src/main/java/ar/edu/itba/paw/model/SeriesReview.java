package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "seriesreview")
public class SeriesReview {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seriesreview_id_seq")
    @SequenceGenerator(sequenceName = "seriesreview_id_seq", name = "seriesreview_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(length = 255, nullable = false)
    private String body;
    @Column(columnDefinition = "integer default 0")
    private int numlikes;
    @Column(name = "isspam", nullable = false,columnDefinition = "boolean default false")
    private boolean isSpam;

    @OneToMany(mappedBy = "parent",fetch = FetchType.LAZY,cascade = CascadeType.REMOVE)
    private Set<SeriesReviewComment> comments = new HashSet<>();
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(optional = false, fetch = FetchType.EAGER)
    private Series series;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "haslikedseriesreview",
            joinColumns = { @JoinColumn(name = "seriesreview") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private Set<User> likes = new HashSet<>();

    public SeriesReview(){
    }
    public SeriesReview(String body, Series series, User user, boolean isSpam){
        this.body = body;
        this.series = series;
        this.user = user;
        this.isSpam = isSpam;
        this.numlikes = 0;
    }

    public int getNumLikes() {
        return numlikes;
    }

    public void setNumLikes(int numlikes) {
        this.numlikes = numlikes;
    }

    public boolean getIsSpam() {
        return isSpam;
    }

    public void setIsSpam(boolean spam) {
        isSpam = spam;
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

    public Set<SeriesReviewComment> getComments() {
        return comments;
    }

    public void setComments(Set<SeriesReviewComment> seriesReviewComments) {
        this.comments = seriesReviewComments;
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

    public Set<User> getLikes() {
        return likes;
    }

    public void setLikes(Set<User> likes) {
        this.likes = likes;
    }

    public void addLike(User user){
        this.likes.add(user);
        user.getSeriesReviewLikes().add(this);
    }
    public void removeLike(User user){
        this.likes.remove(user);
        user.getSeriesReviewLikes().remove(this);
    }
    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof SeriesReview)){
            return false;
        }
        SeriesReview other = (SeriesReview)obj;
        return this.id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
