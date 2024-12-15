package student.management7.StudentManagement7.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Jyoukyou;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.repository.StudentRepository;

@Service
public class StudentService {

  private StudentRepository repository;
  private StudentConverter converter;

  @Autowired
  public StudentService(StudentRepository repository, StudentConverter converter) {
    this.repository = repository;
    this.converter = converter;
  }

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<Jyoukyou> jyoukyouList = repository.searchAllJyoukyou();

//    Map<Integer, Jyoukyou> jyoukyouMap = jyoukyouList.stream()
//        .collect(Collectors.toMap(Jyoukyou::getCourseId, Function.identity()));

    List<StudentDetail> studentDetails = studentList.stream().map(student -> {
      List<StudentCourse> courses = studentCourseList.stream()
          .filter(course -> course.getStudentId() == student.getId())
          .collect(Collectors.toList());

//      List<Jyoukyou> jyoukyouForStudent = courses.stream()
//          .map(course -> jyoukyouMap.get(course.getId()))
//          .collect(Collectors.toList());

//      return new StudentDetail(student, courses, jyoukyouForStudent);
//    }).collect(Collectors.toList());
      return new StudentDetail(student, courses, null);
    }).collect(Collectors.toList());

    return studentDetails;
  }


  //↓追加
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourses = repository.searchStudentsCourses(id);
    return new StudentDetail(student, studentCourses, null);
  }
//↑追加


//  public StudentDetail searchStudent(int id) {
//    Student student = repository.searchStudent(id);
//    List<StudentCourse> studentCourse = repository.searchStudentsCourses(id);
//    List<Jyoukyou> jyoukyouList = studentCourse.stream()
//        .map(course -> repository.searchJyoukyouByCourseId(course.getId()))
//        .collect(Collectors.toList());
//    return new StudentDetail(student, studentCourse, jyoukyouList);
//  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    studentDetail.getStudentCourseList().forEach(studentCourse -> {
      initStudentsCourse(studentCourse, student.getId());
      repository.registerStudentCourse(studentCourse);
    });
    return studentDetail;
  }

  private void initStudentsCourse(StudentCourse studentCourse, int id) {
    LocalDateTime now = LocalDateTime.now();
    studentCourse.setStudentId(id);
    studentCourse.setCourseStartAt(Timestamp.valueOf(now));
    studentCourse.setCourseEndAt(Timestamp.valueOf(now.plusYears(1)));
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseList().forEach(repository::updateStudentCourse);
  }

//  @Transactional
//  public Jyoukyou registerJyoukyou(Jyoukyou jyoukyou) {
//    repository.registerJyoukyou(jyoukyou);
//    return jyoukyou;
//  }

//  @Transactional
//  public void updateJyoukyou(Jyoukyou jyoukyou) {
//    repository.updateJyoukyou(jyoukyou);
//  }
}
