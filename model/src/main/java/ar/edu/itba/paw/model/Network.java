package ar.edu.itba.paw.model;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "network")
public class Network {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "network_id_seq")
    @SequenceGenerator(sequenceName = "network_id_seq", name = "network_id_seq", allocationSize = 1)
    private Long id = -1L;

    @Column(length = 255,nullable = false)
    private String name;

    @OneToMany(mappedBy = "network",fetch = FetchType.LAZY)
    private Set<Series> series;

    public Network(){
    }

    public Network(String name){
        this.name = name;
    }
    public Network(Long id,String name){
        this.id = id;
        this.name = name;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @Override
    public String toString(){
        return name;
    }
    @Override
    public boolean equals(Object obj){
        if(this == obj){
            return true;
        }
        if(!(obj instanceof Network)){
            return false;
        }
        Network other = (Network)obj;
        return this.id.equals(other.id);
    }
    @Override
    public int hashCode(){
        return id.hashCode();
    }
}
