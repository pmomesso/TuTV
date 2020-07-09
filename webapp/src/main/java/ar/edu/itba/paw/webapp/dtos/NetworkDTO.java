package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.Network;

public class NetworkDTO {

    private String name;
    private Long id;

    public NetworkDTO() {}

    public NetworkDTO(Network n) {
        this.name = n.getName();
        this.id = n.getId();
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
}
