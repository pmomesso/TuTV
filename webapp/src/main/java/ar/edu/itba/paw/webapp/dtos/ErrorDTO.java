package ar.edu.itba.paw.webapp.dtos;

public class ErrorDTO {

    private String field;
    private String i18Key;

    public ErrorDTO(){}

    public ErrorDTO(String field, String i18Key){
        this.field = field;
        this.i18Key = i18Key;
    }
    public String getField() {
        return field;
    }

    public void setField(String field) {
        this.field = field;
    }

    public String getI18Key() {
        return i18Key;
    }

    public void setI18Key(String i18Key) {
        this.i18Key = i18Key;
    }
}
