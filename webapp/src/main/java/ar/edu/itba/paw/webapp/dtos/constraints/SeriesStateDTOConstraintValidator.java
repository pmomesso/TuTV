package ar.edu.itba.paw.webapp.dtos.constraints;

import ar.edu.itba.paw.webapp.dtos.SerieStateDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


public class SeriesStateDTOConstraintValidator implements ConstraintValidator<SerieStateDTOConstraint, SerieStateDTO> {


    @Override
    public void initialize(SerieStateDTOConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(SerieStateDTO serieStateDTO, ConstraintValidatorContext context) {
        //A user may not rate a serie if he does not follow it
        if(serieStateDTO.getLoggedInUserFollows() != null &&
                !serieStateDTO.getLoggedInUserFollows() && serieStateDTO.getLoggedInUserRating() != null) {
            return false;
        }

        if(serieStateDTO.getLoggedInUserRating() != null &&
                (serieStateDTO.getLoggedInUserRating() > 5 || serieStateDTO.getLoggedInUserRating() < 0)) {
            return false;
        }
        return true;
    }
}
