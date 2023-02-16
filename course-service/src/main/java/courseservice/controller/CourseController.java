package courseservice.controller;

import ch.qos.logback.core.boolex.EvaluationException;
import courseservice.dto.CourseRequestDto;
import courseservice.dto.CourseResponseDto;
import courseservice.service.CourseService;
import courseservice.service.ServiceResponse;
import courseservice.util.AppUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import net.bytebuddy.implementation.bytecode.Throw;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/course")
public class CourseController {

    Logger log = LoggerFactory.getLogger(CourseController.class);
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }
    @PostMapping("/add")
    public ServiceResponse<CourseResponseDto> addCourse(@RequestBody @Valid CourseRequestDto courseRequestDto) {
        //Validate Request
        //validate CourseRequestPayload(CourseRequestDto)
        log.info("CourseController::addCourse request payload {} ", AppUtils.convertObjectToJson(courseRequestDto));
        validCourseRequestPayload(courseRequestDto);
        ServiceResponse<CourseResponseDto> serviceResponse = new ServiceResponse<>();
        // try {
        CourseResponseDto newCourse = courseService.onboardNewCourse(courseRequestDto);
        serviceResponse.setStatus(HttpStatus.OK);
        serviceResponse.setResponse(newCourse);
        log.info("CourseController::addCourse response {}", AppUtils.convertObjectToJson(serviceResponse));
        // }
      /*  catch(Exception exception){
            serviceResponse.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
        return serviceResponse;

    }

    @GetMapping
    public ServiceResponse<List<CourseResponseDto>> findAllCourse() {
        List<CourseResponseDto> courseResponseDtoS = courseService.viewAllCourses();
        return new ServiceResponse<>(HttpStatus.OK, courseResponseDtoS);
    }

    @Operation(summary = "Find Course By Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "course found"
                    , content = {
                    @Content(mediaType = "application/json", schema = @Schema(implementation = CourseResponseDto.class))
            }),
            @ApiResponse(responseCode = "400",description = "course not found with given id")

    })
    @GetMapping("/search/path/{courseId}")
    public ServiceResponse<?> findCourse(@PathVariable Integer courseId) {
        CourseResponseDto responseDto = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDto);

    }

    @GetMapping("/search/request")
    public ServiceResponse<?> findCourseUsingRequestParam(@RequestParam(required = false) Integer courseId) {
        CourseResponseDto responseDto = courseService.findByCourseId(courseId);
        return new ServiceResponse<>(HttpStatus.OK, responseDto);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        log.info("CourseController::deleteCourse deleting course with id {}", courseId);
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    //assignment
    @PutMapping("/{courseId}")
    public ServiceResponse<CourseResponseDto> updateCourse(@PathVariable int courseId, @RequestBody @Valid CourseRequestDto courseRequestDto) {
        log.info("CourseController::updateCourse update request payload {} & id {}", AppUtils.convertObjectToJson(courseRequestDto), courseId);
        validCourseRequestPayload(courseRequestDto);
        CourseResponseDto courseResponseDto = courseService.updateCourse(courseId, courseRequestDto);
        ServiceResponse<CourseResponseDto> response = new ServiceResponse<>(HttpStatus.OK, courseResponseDto);
        log.info("CourseController::updateCourse update response body {} ", AppUtils.convertObjectToJson(response));
        return response;
    }

    @GetMapping("/log")
    public String loggingLevel() {
        log.trace("trace message");
        log.debug("debug message");
        log.info("info message");
        log.warn("warn message");
        log.error("error message");


        return "<h1>log printed in console</h1> ";

    }

    private void validCourseRequestPayload(CourseRequestDto courseRequestDto) {
        if (courseRequestDto.getDuration() == null || courseRequestDto.getDuration().isEmpty()) {
            throw new RuntimeException("duration field must need to be pass");
        }
        if (courseRequestDto.getFees() == 0) {
            throw new RuntimeException("Fee must be provided");
        }

    }
}









   /* private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    //@RequestMapping(value = "/course",method = RequestMethod.POST)
    @PostMapping
    public ResponseEntity<?> addCourse(@RequestBody Course course) {
        Course newCourse = courseService.onboardNewCourse(course);
        return new ResponseEntity<>(newCourse, HttpStatus.CREATED);//201
    }

    // public List<Course> findAllCourse(){
    @GetMapping
    public ResponseEntity<?> findAllCourse() {
        return new ResponseEntity<>(courseService.viewAllCourses(), HttpStatus.OK);
    }

    @GetMapping("/search/path/{courseId}")
    public ResponseEntity<?> findCourse(@PathVariable Integer courseId) {
        return new ResponseEntity<>(courseService.findByCourseId(courseId), HttpStatus.OK);
    }

    @GetMapping("/search/request")
    public ResponseEntity<?> findCourseUsingRequestParam(@RequestParam(required = false) Integer courseId) {
        return new ResponseEntity<>(courseService.findByCourseId(courseId), HttpStatus.OK);
    }

    @DeleteMapping("/{courseId}")
    public ResponseEntity<?> deleteCourse(@PathVariable int courseId) {
        courseService.deleteCourse(courseId);
        return new ResponseEntity<>("", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/{courseId}")
    public ResponseEntity<?> updateCourse(@PathVariable int courseId, @RequestBody Course course) {
        return new ResponseEntity<>(courseService.updateCourse(courseId, course), HttpStatus.OK);
    }
    @GetMapping("/search/cType")
    public ResponseEntity<?> countCourseType() {
        return new ResponseEntity<>(courseService.countCourseType(), HttpStatus.OK);
    }
*/

