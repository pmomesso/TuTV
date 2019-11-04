package ar.edu.itba.paw.model;

import java.util.List;

public class UsersList {
    private List<User> usersList;
    private Integer from;
    private Long to;
    private Long total;
    private boolean arePrevious;
    private boolean areNext;

    public List<User> getUsersList() {
        return usersList;
    }

    public void setUsersList(List<User> usersList) {
        this.usersList = usersList;
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
