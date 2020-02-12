package ar.edu.itba.paw.webapp.dtos;

public class ErrorDTO {

    private String error;

    public ErrorDTO(){}

    public ErrorDTO(String error){
        this.error = error;
    }


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
