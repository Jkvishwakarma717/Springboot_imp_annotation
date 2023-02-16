package courseservice.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseResponseDto {
    private int courseId;
    private String name;
    private String trainerName;
    private String duration; //days
    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "dd-MM-yyyy")
    private Date startDate;
    private String courseType; //live or rec
    private double fees;
    private boolean isCertificateAvailable;
    public String description;
    private String courseUniqueCode;
    private  String emailId;
    private String contact;
}
