package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.UsersList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UsersListDTO {

    private List<UserDTO> users = Collections.emptyList();

    public UsersListDTO() {
        //Empty constructor for JAX-RS
    }

    public UsersListDTO(UsersList usersList) {
        users = new ArrayList<>(usersList.getUsersList().size());
        usersList.getUsersList().stream().forEach(user -> users.add(new UserDTO(user)));
    }

    public List<UserDTO> getUsers() {
        return users;
    }

    public void setUsers(List<UserDTO> users) {
        this.users = users;
    }
}
