package courseservice.util;
import com.fasterxml.jackson.databind.ObjectMapper;
import courseservice.dto.CourseRequestDto;
import courseservice.dto.CourseResponseDto;
import courseservice.entity.CourseEntity;

public class AppUtils {
    //DTO -> ENTITY
    public static CourseEntity mapDtoToEntity(CourseRequestDto courseRequestDto) {
        CourseEntity courseEntity = new CourseEntity();
        courseEntity.setName(courseRequestDto.getName());
        courseEntity.setTrainerName(courseRequestDto.getTrainerName());
        courseEntity.setDuration(courseRequestDto.getDuration());
        courseEntity.setStartDate(courseRequestDto.getStartDate());
        courseEntity.setCourseType(courseRequestDto.getCourseType());
        courseEntity.setFees(courseRequestDto.getFees());
        courseEntity.setCertificateAvailable(courseRequestDto.isCertificateAvailable());
        courseEntity.setDescription(courseRequestDto.getDescription());
        courseEntity.setEmailId(courseRequestDto.getEmailId());
        courseEntity.setContact(courseRequestDto.getContact());

        return courseEntity;

    }

    //ENTITY TO DTO
    public static CourseResponseDto mapEntityToDto(CourseEntity courseEntity) {
        CourseResponseDto courseResponseDto = new CourseResponseDto();
        courseResponseDto.setCourseId(courseEntity.getCourseId());
        courseResponseDto.setName(courseEntity.getName());
        courseResponseDto.setTrainerName(courseEntity.getTrainerName());
        courseResponseDto.setDuration(courseEntity.getDuration());
        courseResponseDto.setStartDate(courseEntity.getStartDate());
        courseResponseDto.setCourseType(courseEntity.getCourseType());
        courseResponseDto.setFees(courseEntity.getFees());
        courseResponseDto.setCertificateAvailable(courseEntity.isCertificateAvailable());
        courseResponseDto.setDescription(courseEntity.getDescription());
        courseResponseDto.setEmailId(courseEntity.getEmailId());
        courseResponseDto.setContact(courseEntity.getContact());
        return courseResponseDto;

    }

    public static String convertObjectToJson(Object object) {
        try {
           return new ObjectMapper().writeValueAsString(object);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return null;
    }
}
