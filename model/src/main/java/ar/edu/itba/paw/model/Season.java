package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "season")
public class Season {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "season_id_seq")
    @SequenceGenerator(sequenceName = "season_id_seq", name = "season_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(length = 255, nullable = true)
    private String name;
    @Column(nullable = false)
    private int seasonNumber;

    @OneToMany(cascade = {CascadeType.PERSIST,CascadeType.MERGE},mappedBy = "season",fetch = FetchType.LAZY)
    @OrderBy(value = "numEpisode asc")
    private Set<Episode> episodes = new HashSet<>();
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Series series;

    @Transient
    private boolean viewed;
    @Transient
    private int episodesViewed;
    @Transient
    private boolean seasonAired;

    public Season(){
    }
    public Season(String name, int seasonNumber){
        this.name = name;
        this.seasonNumber = seasonNumber;
    }
    public String getName() {
        return name;
    }

    public Set<Episode> getEpisodes() {
        return episodes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSeasonNumber() {
        return seasonNumber;
    }

    public void setSeasonNumber(int seasonNumber) {
        this.seasonNumber = seasonNumber;
    }

    public long getId() {
        return id;
    }

    public void setId(long seasonId) {
        this.id = seasonId;
    }

    public void setEpisodes(Set<Episode> episodes) {
        this.episodes = episodes;
    }

    public void addEpisode(Episode episode){
        this.episodes.add(episode);
        episode.setSeason(this);
    }
    public Series getSeries() {
        return series;
    }

    public void setSeries(Series series) {
        this.series = series;
    }

    public boolean getViewed() {
        return viewed;
    }

    public void setViewed(boolean viewed) {
        this.viewed = viewed;
    }

    public int getEpisodesViewed() {
        return episodesViewed;
    }

    public void setEpisodesViewed(int episodesViewed) {
        this.episodesViewed = episodesViewed;
    }

    public boolean getSeasonAired() {
        return seasonAired;
    }

    public void setSeasonAired(boolean seasonAired) {
        this.seasonAired = seasonAired;
    }
}
