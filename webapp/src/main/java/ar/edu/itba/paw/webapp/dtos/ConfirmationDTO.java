package ar.edu.itba.paw.webapp.dtos;

import javax.validation.constraints.NotNull;

public class ConfirmationDTO {

    @NotNull
    private String confirmationKey;

    public ConfirmationDTO() {
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }
}
