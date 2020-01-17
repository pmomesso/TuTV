package ar.edu.itba.paw.webapp.dtos;

public class SampleEchoDTO {

    private String message;

    public SampleEchoDTO(String message) {
        this.message = message;
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

}
