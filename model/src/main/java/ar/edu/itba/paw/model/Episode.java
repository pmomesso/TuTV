package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Entity
@Table(name = "episode")
public class Episode {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "series_id_seq")
    @SequenceGenerator(sequenceName = "series_id_seq", name = "series_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(nullable = true)
    private Long tvdbid;
    @Column(length = 255,nullable = false)
    private String name;
    @Column(length = 2048,nullable = false)
    private String overview;
    @Column(length = 255,nullable = false)
    private Integer numEpisode;
    @Column
    @Temporal(TemporalType.DATE)
    private Date aired;
    //Columna para el apicrawler
    @Column(name = "series_id",nullable = true)
    private Long seriesId;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Season season;
    @ManyToMany(cascade = {
            CascadeType.PERSIST,
            CascadeType.MERGE
    },fetch = FetchType.LAZY)
    @JoinTable(
            name = "hasviewedepisode",
            joinColumns = { @JoinColumn(name = "episodeid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private Set<User> viewers = new HashSet<>();

    @Transient
    private boolean hasPreviousUnseenEpisodes = false;

    public Episode(){
    }
    public Episode(String name, String overview, int numEpisode, String aired){
        this.name = name;
        this.overview = overview;
        this.numEpisode = numEpisode;
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd");
        try {
            this.aired = f.parse(aired);
        } catch (ParseException e) {
            this.aired = null;
        }
    }
    public int getNumEpisode() {
        return numEpisode;
    }

    public void setNumEpisode(int numEpisode) {
        this.numEpisode = numEpisode;
    }

    public String getName() {
        return name;
    }

    public String getOverview() {
        return overview;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Date getAiring() {
        return aired;
    }

    public void setAiring(Date aired) {
        this.aired = aired;
    }

    public Season getSeason() {
        return season;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public Set<User> getViewers() {
        return viewers;
    }

    public void setViewers(Set<User> viewers) {
        this.viewers = viewers;
    }

    public void addViewer(User viewer){
        this.viewers.add(viewer);
        viewer.getViewed().add(this);
    }
    public void removeViewer(User viewer){
        this.viewers.remove(viewer);
        viewer.getViewed().remove(this);
    }

    public boolean isHasPreviousUnseenEpisodes() {
        return hasPreviousUnseenEpisodes;
    }

    public void setHasPreviousUnseenEpisodes(boolean hasPreviousUnseenEpisodes) {
        this.hasPreviousUnseenEpisodes = hasPreviousUnseenEpisodes;
    }

    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Episode)){
            return false;
        }
        Episode other = (Episode)obj;
        return this.id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
