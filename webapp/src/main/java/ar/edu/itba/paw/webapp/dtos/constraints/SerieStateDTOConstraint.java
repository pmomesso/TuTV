package ar.edu.itba.paw.webapp.dtos.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {SeriesStateDTOConstraintValidator.class})
public @interface SerieStateDTOConstraint {
    String message() default "";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
