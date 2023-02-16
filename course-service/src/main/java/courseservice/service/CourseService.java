package courseservice.service;

import courseservice.dao.CourseDao;
import courseservice.dto.CourseRequestDto;
import courseservice.dto.CourseResponseDto;
import courseservice.entity.CourseEntity;
import courseservice.exception.CourseServiceBusinessException;
import courseservice.util.AppUtils;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service

//@Service/Component
public class CourseService {
    @Autowired
    private CourseDao courseDao;
    Logger log = LoggerFactory.getLogger(CourseService.class);

    //Create Course Object In DB
    public CourseResponseDto onboardNewCourse(CourseRequestDto courseRequestDto) {
        //convert DTO into ENTITY
        CourseEntity courseEntity = AppUtils.mapDtoToEntity(courseRequestDto);
        CourseEntity entity = null;
        log.info("CourseService::onboardNewCourse method execution started");
        try {
            entity = courseDao.save(courseEntity);
            log.debug("course entity response from database {} ", AppUtils.convertObjectToJson(entity));
            CourseResponseDto courseResponseDto = AppUtils.mapEntityToDto(entity);
            courseResponseDto.setCourseUniqueCode(UUID.randomUUID().toString().split("-")[0]);
            log.debug("CourseService::onboardNewCourse method Response {}", AppUtils.convertObjectToJson(courseResponseDto));
            log.info("CourseService::onboardNewCourse method execution ended");
            return courseResponseDto;
        } catch (Exception exception) {
            log.error("CourseService::onboardNewCourse Exception while persisting the course object to DB");
            throw new CourseServiceBusinessException("onboardNewCourse service method failed");
        }
        //Convert Entity -> Response DTO


    }

    //Assignment to add logger for all get and put method
    //Load all course from database
    public List<CourseResponseDto> viewAllCourses() {
        try {
            Iterable<CourseEntity> courseEntities = courseDao.findAll();
            return StreamSupport.stream(courseEntities.spliterator(), false)
                    .map(AppUtils::mapEntityToDto)
                    .collect(Collectors.toList());
        } catch (Exception exception) {
            throw new CourseServiceBusinessException("viewAllCourses service method failed "+exception.getMessage());
        }

    }

    //Filter Course by course Id
    public CourseResponseDto findByCourseId(Integer courseId) {
        try {
            CourseEntity courseEntity = courseDao.findById(courseId)
                    //.orElseThrow(() -> new RuntimeException(courseId + "not valid ID"));
                    .orElseThrow(() -> new CourseServiceBusinessException(courseId + " courseId doesn't Exist"));
            return AppUtils.mapEntityToDto(courseEntity);
        } catch (Exception exception) {
            throw new CourseServiceBusinessException("findByCourseId service failed "+exception.getMessage());
        }
    }

    //Delete Course By Id
    public void deleteCourse(int courseId) {
        try {
            log.debug("CourseService::deleteCourse method input {}", courseId);
            courseDao.deleteById(courseId);
        } catch (Exception exception) {
            log.error("CourseService::deleteCourse Exception while deleting the course object.."+exception.getMessage());
            throw new CourseServiceBusinessException("CourseService::deleteCourse method execution started..");
        }
        log.info("CourseService::deleteCourse method execution ended..");
    }

    //update the course
    //-get the existing object
    //-modify existing object with new value
    //-save modify value into database
    public CourseResponseDto updateCourse(int courseId, CourseRequestDto courseRequestDto) {
        log.info("CourseService::updateCourse method execution started");
        try {
            log.debug("CourseService::updateCourse request payload {}& method input {}", AppUtils.convertObjectToJson(courseRequestDto), courseId);
            CourseEntity exitingCourseEntity = courseDao.findById(courseId)
                            .orElseThrow(()->new RuntimeException("course object not present in db with id "+courseId));
            log.debug("CourseService::updateCourse getting existing course object as {}", AppUtils.convertObjectToJson(exitingCourseEntity));
            exitingCourseEntity.setName(courseRequestDto.getName());
            exitingCourseEntity.setTrainerName(courseRequestDto.getTrainerName());
            exitingCourseEntity.setDuration(courseRequestDto.getDuration());
            exitingCourseEntity.setStartDate(courseRequestDto.getStartDate());
            exitingCourseEntity.setCourseType(courseRequestDto.getCourseType());
            exitingCourseEntity.setFees(courseRequestDto.getFees());
            exitingCourseEntity.setCertificateAvailable(courseRequestDto.isCertificateAvailable());
            exitingCourseEntity.setDescription(courseRequestDto.getDescription());
            CourseEntity updatedCourseEntity = courseDao.save(exitingCourseEntity);
            CourseResponseDto courseResponseDto = AppUtils.mapEntityToDto(updatedCourseEntity);
            log.debug("CourseService::updateCourse getting updated object as {} ", AppUtils.convertObjectToJson(courseResponseDto));
            log.info("CourseService::updateCourse method execution ended");
            return courseResponseDto;

        } catch (Exception exception) {
            log.error("CourseService::updateCourse Exception while updating  the course object {}"+exception.getMessage());
            throw new CourseServiceBusinessException("updateCourse Service failed "+exception.getMessage());
        }

    }













   /* private List<Course>courseDatabase=new ArrayList<>();

    //Create Course Object In DB
    public Course onboardNewCourse(Course course){
        course.setCourseId(new Random().nextInt(3756));
        courseDatabase.add(course);
        return course;
    }
    public List<Course>viewAllCourses(){
        return courseDatabase;
    }

    //Filter Course By CourseId
    public Course findByCourseId(Integer courseId){
        return courseDatabase.stream()
                .filter(course -> course.getCourseId()==courseId)
                .findFirst().orElse(null);
    }

    //delete course
    public void deleteCourse(int courseId){
        Course course=findByCourseId(courseId);
        courseDatabase.remove(course);
    }

    //update the course
    public Course updateCourse(int courseId,Course course){
       Course existingCourse= findByCourseId(courseId);
      // existingCourse.setTrainerName(course.getTrainerName());
        courseDatabase.set(courseDatabase.indexOf(existingCourse),course);//never try to like this
        return course;

    }
    public Map countCourseType(){
        int countLive = 0;
        int countRec = 0;
       Map allCount= new HashMap<String,Integer>();
        for(Course course:courseDatabase){
            if(course.getCourseType().equalsIgnoreCase("live")){
                countLive++;
                allCount.put("live",countLive);
                System.out.println(countLive);
            }
            if(course.getCourseType().equalsIgnoreCase("rec")){
                countRec++;
                allCount.put("rec",countRec);
                System.out.println(countRec);
            }

        }
        return allCount;
    }*/
}
