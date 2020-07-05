package ar.edu.itba.paw.webapp.dtos.constraints;

import ar.edu.itba.paw.webapp.dtos.SerieStateDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class SeriesStateDTOConstraintValidator implements ConstraintValidator<SerieStateDTOConstraint, SerieStateDTO> {

    @Override
    public void initialize(SerieStateDTOConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(SerieStateDTO serieStateDTO, ConstraintValidatorContext context) {
        if(serieStateDTO.getLoggedInUserFollows() == null && serieStateDTO.getLoggedInUserRating() == null) {
            return false;
        }
        return true;
    }

}
