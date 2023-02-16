package courseservice.entity;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="COURSE_TBL")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
     @Id
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int courseId;
    private String name;
    private String trainerName;
    private String duration; //days
    private Date startDate;
    private String courseType; //live or rec
    private double fees;
    private boolean isCertificateAvailable;
    public String description;
    private  String emailId;
    private String contact;
}
