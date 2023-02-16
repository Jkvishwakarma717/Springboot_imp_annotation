package courseservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import courseservice.annotation.CourseTypeValidation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.*;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseRequestDto {
    @NotBlank(message = "Course name Shouldn't be Null")
    private String name;
    @NotEmpty(message = "Trainer name should be define")
    private String trainerName;
    @NotNull(message = "Duration must need to specify")

    private String duration; //days
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @Past(message = "start date can't be before date from start date")
    private Date startDate;
    @CourseTypeValidation
    private String courseType; //live or rec
    @Min(value = 1500, message = "Course price can't be less than 1500")
    @Max(value = 5000, message = "Course price can't be more than 5000")
    private double fees;
    private boolean isCertificateAvailable;
    @NotEmpty(message = "Description must be present")
   // @Length(min = 5,max = 10)
    //@Min(20)
    private String description;
    @Email(message = "Invalid Email Id")
    private String emailId;
    @Pattern(regexp = "^[0-9]{10}$")
    private String contact;
}
