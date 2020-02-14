package ar.edu.itba.paw.webapp.dtos.constraints;

import ar.edu.itba.paw.webapp.controller.UserEditDTO;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class UserEditDTOConstraintValidator implements ConstraintValidator<UserEditDTOConstraint, UserEditDTO> {

    @Override
    public void initialize(UserEditDTOConstraint constraintAnnotation) {}

    @Override
    public boolean isValid(UserEditDTO userEditDTO, ConstraintValidatorContext context) {
        return !(userEditDTO.getBanned() != null && userEditDTO.getUserName() != null)
                && !(userEditDTO.getBanned() == null && userEditDTO.getUserName() == null);
    }
}
