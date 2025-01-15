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
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.domain.StudentCourseDetail;
import student.management7.StudentManagement7.repository.StudentRepository;

@Service
public class StudentService {

  private final StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<StudentDetail> searchStudentList() {
    List<Student> studentList = repository.search();
    List<StudentCourse> studentCourseList = repository.searchStudentCourseList();
    List<Status> statusList = repository.searchAllStatus();

    Map<Integer, Status> statusMap = statusList.stream()
        .collect(Collectors.toMap(Status::getCourseId, Function.identity()));

    List<StudentDetail> studentDetails = studentList.stream().map(student -> {
      List<StudentCourseDetail> courseDetails = studentCourseList.stream()
          .filter(course -> course.getStudentId() == student.getId())
          .map(course -> new StudentCourseDetail(course, statusMap.get(course.getId())))
          .collect(Collectors.toList());

      return new StudentDetail(student, courseDetails);
    }).collect(Collectors.toList());

    return studentDetails;
  }

  public StudentDetail getStudentCourseDetails(int studentCourseId) {
    StudentCourse studentCourse = repository.searchStudentCourseById(studentCourseId);
    Student student = repository.searchStudent(studentCourse.getStudentId());
    Status status = repository.searchStatusByStudentCourseId(studentCourseId);

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, status);

    return new StudentDetail(student, List.of(studentCourseDetail));
  }

  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);
    studentDetail.getStudentCourseDetailList().forEach(detail -> {
      StudentCourse studentCourse = detail.getStudentCourse();
      initStudentsCourse(studentCourse, student.getId());
      repository.registerStudentCourse(studentCourse);
      detail.setStatus(repository.searchStatusByStudentCourseId(studentCourse.getId()));
    });
    return studentDetail;
  }

  private void initStudentsCourse(StudentCourse studentCourse, int studentId) {
    LocalDateTime now = LocalDateTime.now();
    studentCourse.setStudentId(studentId);
    studentCourse.setCourseStartAt(Timestamp.valueOf(now));
    studentCourse.setCourseEndAt(Timestamp.valueOf(now.plusYears(1)));
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());
    studentDetail.getStudentCourseDetailList().forEach(detail -> {
      repository.updateStudentCourse(detail.getStudentCourse());
      repository.updateStatus(detail.getStatus());
    });
  }
}
