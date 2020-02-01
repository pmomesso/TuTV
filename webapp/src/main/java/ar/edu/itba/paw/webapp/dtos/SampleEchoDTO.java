package ar.edu.itba.paw.webapp.dtos;

import java.util.Collections;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class SampleEchoDTO {

    private String message;
    private Set<String> set;

    public SampleEchoDTO(String message) {
        this.message = message;
        set = new HashSet<>();
        set.add("Hola");
        set.add("Chau");
    }

    public SampleEchoDTO() {
        //Empty constructor for JAX-RS
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Set<String> getSet() {
        return set;
    }

    public void setSet(Set<String> set) {
        this.set = set;
    }

}
