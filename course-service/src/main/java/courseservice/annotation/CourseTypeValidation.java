package courseservice.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD,ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = CourseTypeValidator.class)
public @interface CourseTypeValidation {
    String message() default "COURSE TYPE SHOULD BE EITHER LIVE OR REC";

    Class<?>[] groups() default { };

    Class<? extends Payload>[] payload() default { };


}
