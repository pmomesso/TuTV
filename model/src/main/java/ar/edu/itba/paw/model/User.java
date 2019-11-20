package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(sequenceName = "users_id_seq", name = "users_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "username", length = 50)
    private String userName;

    @Column(name = "mail")
    private String mailAddress;

    @Column(name = "password")
    private String password;

    private Date birthDate;

    @Column(name = "confirmation_key", length = 60)
    private String confirmationKey;

    @Column(name = "isadmin", nullable = false, columnDefinition = "boolean default false")
    private boolean isAdmin = false;

    @Column(name = "isbanned", nullable = false, columnDefinition = "boolean default false")
    private boolean isBanned = false;

    @Column(name = "avatar")
    private byte[] userAvatar;

    @ManyToMany(mappedBy = "userFollowers", fetch = FetchType.LAZY)
    private Set<Series> follows = new HashSet<>();
    @OneToMany(mappedBy = "listUser", fetch = FetchType.LAZY)
    private Set<SeriesList> lists = new HashSet<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SeriesReview> seriesReviews = new HashSet<>();
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private Set<SeriesReviewComment> seriesReviewComments = new HashSet<>();
    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    private Set<SeriesReview> seriesReviewLikes = new HashSet<>();
    @ManyToMany(mappedBy = "likes", fetch = FetchType.LAZY)
    private Set<SeriesReviewComment> seriesReviewCommentLikes = new HashSet<>();
    @ManyToMany(mappedBy = "viewers", fetch = FetchType.LAZY)
    private Set<Episode> viewed = new HashSet<>();
    @OneToMany(mappedBy = "user",fetch = FetchType.LAZY)
    private Set<Rating> ratings = new HashSet<>();
    @OrderBy("id DESC")
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private SortedSet<Notification> notifications = new TreeSet<>();
    @Transient
    private long notificationsToView = 0;

    /* Hibernate*/ public User() {}

    public User(String userName,String password,String mailAddress,boolean isAdmin){
        this.userName = userName;
        this.password = password;
        this.mailAddress = mailAddress;
        this.isAdmin = isAdmin;
    }
    public String getConfirmationKey() {
        return confirmationKey;
    }
    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMailAddress() {
        return mailAddress;
    }
    public void setMailAddress(String mailAddress) {
        this.mailAddress = mailAddress;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public boolean getIsAdmin() {
        return isAdmin;
    }

    public void setIsAdmin(boolean admin) {
        isAdmin = admin;
    }

    public boolean getIsBanned() {
        return isBanned;
    }

    public void setIsBanned(boolean banned) {
        isBanned = banned;
    }

    public void setUserAvatar(byte[] userAvatar) {
        this.userAvatar = userAvatar;
    }

    public byte[] getUserAvatar() {
        return userAvatar;
    }

    public Set<Series> getFollows() {
        return follows;
    }

    public void setFollows(Set<Series> follows) {
        this.follows = follows;
    }

    public Set<SeriesReview> getSeriesReviews() {
        return seriesReviews;
    }

    public void setSeriesReviews(Set<SeriesReview> seriesReviews) {
        this.seriesReviews = seriesReviews;
    }

    public Set<SeriesReviewComment> getSeriesReviewComments() {
        return seriesReviewComments;
    }

    public void setSeriesReviewComments(Set<SeriesReviewComment> seriesReviewComments) {
        this.seriesReviewComments = seriesReviewComments;
    }

    public Set<SeriesReview> getSeriesReviewLikes() {
        return seriesReviewLikes;
    }

    public void setSeriesReviewLikes(Set<SeriesReview> seriesReviewLikes) {
        this.seriesReviewLikes = seriesReviewLikes;
    }

    public Set<SeriesReviewComment> getSeriesReviewCommentLikes() {
        return seriesReviewCommentLikes;
    }

    public void setSeriesReviewCommentLikes(Set<SeriesReviewComment> seriesReviewCommentLikes) {
        this.seriesReviewCommentLikes = seriesReviewCommentLikes;
    }

    public Set<Episode> getViewed() {
        return viewed;
    }

    public void setViewed(Set<Episode> viewed) {
        this.viewed = viewed;
    }

    public Set<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(Set<Rating> ratings) {
        this.ratings = ratings;
    }

    public Set<SeriesList> getLists() {
        return lists;
    }

    public void setLists(Set<SeriesList> lists) {
        this.lists = lists;
    }

    public SortedSet<Notification> getNotifications() {
        return notifications;
    }

    public void setNotifications(SortedSet<Notification> notifications) {
        this.notifications = notifications;
    }

    public long getNotificationsToView() {
        return notificationsToView;
    }

    public void setNotificationsToView(long notificationsToView) {
        this.notificationsToView = notificationsToView;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof User)){
            return false;
        }
        User u = (User)obj;
        return this.getId() == u.getId();
    }
    @Override
    public int hashCode(){
        return Long.hashCode(id);
    }
}
