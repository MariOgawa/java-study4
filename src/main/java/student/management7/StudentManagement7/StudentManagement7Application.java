package student.management7.StudentManagement7;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class StudentManagement7Application {

	public static void main(String[] args) {
		SpringApplication.run(StudentManagement7Application.class, args);
	}
	@GetMapping("/hello2")
	public String hello3() {
		return "Hello, World4!";
	}
}
