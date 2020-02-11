package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "genres")
public class Genre {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "genre_id_seq")
    @SequenceGenerator(sequenceName = "genre_id_seq", name = "genre_id_seq", allocationSize = 1)
    private Long id = -1L;
    @Column(length = 255,nullable = false)
    private String name;
    @Column(length = 255,nullable = false)
    private String i18Key;
    @ManyToMany(mappedBy = "genres",fetch = FetchType.LAZY)
    private Set<Series> series = new HashSet<>();
    @Transient
    private Long page;
    @Transient
    private boolean arePrevious;
    @Transient
    private boolean areNext;

    public Genre(){
        this.name = null;
        this.id = -1L;
    }
    public Genre(String name){
        this.name = name;
    }
    public Genre(Long id, String name){
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Genre genre = (Genre) o;
        return id.equals(genre.id) &&
                Objects.equals(name, genre.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, id);
    }

    @Override
    public String toString() {
        return name;
    }

    public Set<Series> getSeries() {
        return series;
    }

    public void setSeries(Set<Series> series) {
        this.series = series;
    }

    public Long getPage() {
        return page;
    }

    public void setPage(Long page) {
        this.page = page;
    }

    public boolean isArePrevious() {
        return arePrevious;
    }

    public void setArePrevious(boolean arePrevious) {
        this.arePrevious = arePrevious;
    }

    public boolean isAreNext() {
        return areNext;
    }

    public void setAreNext(boolean areNext) {
        this.areNext = areNext;
    }

    public String getI18Key() {
        return i18Key;
    }

    public void setI18Key(String i18Key) {
        this.i18Key = i18Key;
    }
}
