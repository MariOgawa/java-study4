package student.management7.StudentManagement7.service;

import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;
import student.management7.StudentManagement7.repository.StudentRepository;

@Service
public class StudentService {

    private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudent() {
    return repository.search();
   }

  public List<StudentsCourses> searchStudentsCourse() {
  return repository.searchStudentsCourse();
   }

  public List<Student> searchStudentsIn30s() {
    List<Student> students = repository.search();
    return students.stream()
        .filter(student -> student.getAge() >= 30 && student.getAge() <= 39)
        .collect(Collectors.toList());
  }

  public List<StudentsCourses> searchJavaCourses() {
    List<StudentsCourses> studentCourses = repository.searchStudentsCourse();
    return studentCourses.stream()
        .filter(course -> "JAVA".equalsIgnoreCase(course.getCoursename()))
        .collect(Collectors.toList());
  }
}
