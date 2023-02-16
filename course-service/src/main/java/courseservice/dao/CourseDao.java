package courseservice.dao;

import courseservice.entity.CourseEntity;
import org.springframework.data.repository.CrudRepository;

public interface CourseDao extends CrudRepository<CourseEntity,Integer> {


}
