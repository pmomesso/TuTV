package ar.edu.itba.paw.webapp.dtos;

import ar.edu.itba.paw.model.User;

import java.util.Base64;

public class UserAvatarDTO {

    private String avatarBase64;

    public UserAvatarDTO() {
        //Empty constructor for JAX-RS
    }

    public UserAvatarDTO(User user) {
        if(user.getUserAvatar() != null) {
            avatarBase64 = Base64.getEncoder().encodeToString(user.getUserAvatar());
        }
    }

    public String getAvatarBase64() {
        return avatarBase64;
    }

    public void setAvatarBase64(String avatarBase64) {
        this.avatarBase64 = avatarBase64;
    }
}
