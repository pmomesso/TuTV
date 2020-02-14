package ar.edu.itba.paw.webapp.dtos.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {UserEditDTOConstraintValidator.class})
public @interface UserEditDTOConstraint {
    String message() default "Cannot contain both isBanned and userName fields";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
