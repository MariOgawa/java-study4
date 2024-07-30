package student.management7.StudentManagement7.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.Service.StudentService;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.repository.StudentRepository;

@RestController
public class StudentController {

  @Autowired
  private StudentService service;

  @Autowired
  private StudentRepository repository;

  @GetMapping("/studentList")
  public List<Student> searchStudentList(){
    return repository.search();
  }

  @GetMapping("/studentsCourseList")
  public List<StudentCourse> searchStudentsCourseList(){
    return repository.searchStudentsCourse();
  }

  @GetMapping("/studentsIn30s")
  public List<Student> searchStudentsIn30s() {
    return service.searchStudentsIn30s();
  }

  @GetMapping("/javaCourses")
  public List<StudentCourse> searchJavaCourses() {
    return service.searchJavaCourses();
  }
}
