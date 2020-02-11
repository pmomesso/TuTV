package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.UsersList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersListDTO {

    private List<UserDTO> users = Collections.emptyList();
    private Integer from;
    private Long to;
    private Long total;
    private boolean arePrevious;
    private boolean areNext;

    public UsersListDTO() {
        //Empty constructor for JAX-RS
    }

    public UsersListDTO(UsersList usersList) {
        users = new ArrayList<>(usersList.getUsersList().size());
        usersList.getUsersList().stream().forEach(user -> users.add(new UserDTO(user)));
        this.from = usersList.getFrom();
        this.to = usersList.getTo();
        this.total = usersList.getTotal();
        this.arePrevious = usersList.isArePrevious();
        this.areNext = usersList.isAreNext();
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }

    public Integer getFrom() {
        return from;
    }

    public void setFrom(Integer from) {
        this.from = from;
    }

    public Long getTo() {
        return to;
    }

    public void setTo(Long to) {
        this.to = to;
    }

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
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
}
