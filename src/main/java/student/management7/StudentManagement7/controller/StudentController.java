package student.management7.StudentManagement7.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;
import student.management7.StudentManagement7.service.StudentService;
import student.management7.StudentManagement7.domain.StudentDetail;

@RestController
public class StudentController {

  @Autowired
  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter){
    this.service = service;
    this.converter = converter;
  };

  @GetMapping("/studentList")
  public List<StudentDetail> searchStudentList(){
    List<Student> students = service.searchStudent();
    List<StudentsCourses> studentsCourses =service.searchStudentsCourse();

    return converter.convertStudentDetails(students, studentsCourses);
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> searchStudentsCourseList(){
    return service.searchStudentsCourse();
  }

  @GetMapping("/studentsIn30s")
  public List<Student> searchStudentsIn30s() {
    return service.searchStudentsIn30s();
  }

  @GetMapping("/javaCourses")
  public List<StudentsCourses> searchJavaCourses() {
    return service.searchJavaCourses();
  }
}
