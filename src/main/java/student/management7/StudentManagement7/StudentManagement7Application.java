package student.management7.StudentManagement7;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement7Application{

	@Autowired
	private StudentRepository repository;
	@Autowired
	private StudentCourseRepository studentCourseRepository;

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement7Application.class, args);
	}

	@GetMapping("/studentList")
	public List<Student> getStudentList(){
		return repository.search();
	}

	@GetMapping("/studentCourseList")
	public List<StudentCourse> getStudentCourseList(){
		return studentCourseRepository.getAllStudentCourse();
	}

}

