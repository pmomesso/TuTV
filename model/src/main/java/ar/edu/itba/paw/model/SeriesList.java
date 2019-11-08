package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "list")
public class SeriesList {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "list_id_seq")
    @SequenceGenerator(sequenceName = "list_id_seq", name = "list_id_seq", allocationSize = 1)
    @Column(name = "id")
    private long id;

    @Column(name = "name", length = 50)
    private String name;

    @ManyToMany(mappedBy = "seriesList", fetch = FetchType.LAZY)
    private Set<Series> series = new HashSet<>();

    @ManyToOne
    @JoinTable(
            name = "serieslist",
            joinColumns = { @JoinColumn(name = "listid") },
            inverseJoinColumns = { @JoinColumn(name = "userid") }
    )
    private User listUser;

    public SeriesList() {}

    public SeriesList(String name) {
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Series> getSeries() {
        return series;
    }

    public void setSeries(Set<Series> series) {
        this.series = series;
    }

    public User getListUser() {
        return listUser;
    }

    public void setListUser(User user) {
        this.listUser = user;
    }
}
