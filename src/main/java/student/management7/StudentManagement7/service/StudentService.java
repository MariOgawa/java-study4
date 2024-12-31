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
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
//import student.management7.StudentManagement7.data.Status;
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
      List<Status> StatusList = repository.searchAllStatus();

    Map<Integer, Status> StatusMap = StatusList.stream()
        .collect(Collectors.toMap(Status::getCourseId, Function.identity()));

    List<StudentDetail> studentDetails = studentList.stream().map(student -> {
      List<StudentCourse> courses = studentCourseList.stream()
          .filter(course -> course.getStudentId() == student.getId())
          .collect(Collectors.toList());

      List<Status> StatusForStudent = courses.stream()
          .map(course -> StatusMap.get(course.getId()))
          .collect(Collectors.toList());

      return new StudentDetail(student, courses, StatusForStudent);
    }).collect(Collectors.toList());
//      return new StudentDetail(student, courses, null);
//    }).collect(Collectors.toList());

    return studentDetails;
  }


  //↓追加
//  public StudentDetail searchStudent(int id) {
//    Student student = repository.searchStudent(id);
//    List<StudentCourse> studentCourses = repository.searchStudentsCourses(id);
//    return new StudentDetail(student, studentCourses, null);
//  }
//↑追加

/*
  public StudentDetail searchStudent(int id) {
    Student student = repository.searchStudent(id);
    List<StudentCourse> studentCourse = repository.searchStudentsCourses(id);
    List<Status> StatusList = studentCourse.stream()
        .map(course -> repository.searchStatusByCourseId(course.getId()))
        .collect(Collectors.toList());
    return new StudentDetail(student, studentCourse, StatusList);
  }
*/
  /**
   * StudentCourseのIDから紐づくStatusを取得します。
   *
   * @param studentCourseId StudentCourseのID
   * @return 紐づくStatus
   */

  public StudentDetail getStudentCourseDetails(int studentCourseId) {
    // StudentCourseの詳細を取得
    StudentCourse studentCourse = repository.searchStudentCourseById(studentCourseId);

    // Student情報を取得
    Student student = repository.searchStudent(studentCourse.getStudentId());

    // Statusを取得
    Status status = repository.searchStatusByStudentCourseId(studentCourseId);

    return new StudentDetail(student, List.of(studentCourse), List.of(status));
  }













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

  //@Transactional
  //public Status registerStatus(Status Status) {
  //  repository.registerStatus(Status);
  //  return Status;
  //}

  // @Transactional
  //public void updateStatus(Status Status) {
  //  repository.updateStatus(Status);
  //}
}
