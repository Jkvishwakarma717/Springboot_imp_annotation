package courseservice.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

public class CourseTypeValidator implements ConstraintValidator<CourseTypeValidation,String> {
    @Override
    public boolean isValid(String courseType, ConstraintValidatorContext context) {
        List list = Arrays.asList("LIVE","REC");
        return list.contains(courseType);
    }
}
