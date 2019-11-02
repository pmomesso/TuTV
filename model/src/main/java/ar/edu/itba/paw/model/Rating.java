package ar.edu.itba.paw.model;

import javax.persistence.*;

@Entity
@Table(name = "ratings")
public class Rating {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "ratings_id_seq")
    @SequenceGenerator(sequenceName = "ratings_id_seq", name = "ratings_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(nullable = false)
    private int rating;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private User user;
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Series series;

    public Rating() {
    }
    public Rating(User user, Series series, int rating){
        this.user = user;
        this.series = series;
        this.rating = rating;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Rating)){
            return false;
        }
        Rating other = (Rating)obj;
        return this.id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
