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

    //2025.05.18ST
    //Map<Integer, Status> statusMap = statusList.stream()
    //    .collect(Collectors.toMap(Status::getCourseId, Function.identity()));
    Map<Integer, Status> statusMap = statusList.stream()
        .collect(Collectors.toMap(
            Status::getCourseId,
            Function.identity(),

            (existing, replacement) -> {return existing;}  // または replacement にしてもよい



        ));
    //2025.05.18END

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

  //2025.01.19ST
 //public Student getStudent(int id) {
    //return repository.searchStudent(id);
  //}
  //2025.01.19END

  //2025.02.02STR
  @Transactional
  public StudentDetail registerStudent(StudentDetail studentDetail) {
    Student student = studentDetail.getStudent();
    repository.registerStudent(student);

    studentDetail.getStudentCourseDetailList().forEach(detail -> {
      StudentCourse studentCourse = detail.getStudentCourse();
      initStudentsCourse(studentCourse, student.getId());
      repository.registerStudentCourse(studentCourse);

      // ステータスをチェック
      Status status = detail.getStatus();
      if (status == null || status.getStatus() == null || status.getStatus().isEmpty()) {
        throw new IllegalArgumentException("ステータスが未入力です。'本申込'、'受講中'、'仮申込'、'受講終了' のいずれかを指定してください。");
      }

      // ステータスを登録
      status.setCourseId(studentCourse.getId());
      repository.registerStatus(status);  // ここで呼び出し

      // 登録後のステータスを取得
      detail.setStatus(repository.searchStatusByStudentCourseId(studentCourse.getId()));
    });

    return studentDetail;
  }
  //2025.02.02END

  private void initStudentsCourse(StudentCourse studentCourse, int studentId) {
    LocalDateTime now = LocalDateTime.now();
    studentCourse.setStudentId(studentId);
    studentCourse.setCourseStartAt(Timestamp.valueOf(now));
    studentCourse.setCourseEndAt(Timestamp.valueOf(now.plusYears(1)));
  }

  //2025.01.19.ST
  @Transactional
  //public void updateStudent(StudentDetail studentDetail) {
  public StudentDetail updateStudent(StudentDetail studentDetail) {
    repository.updateStudent(studentDetail.getStudent());

    // Nullチェックを追加
    List<StudentCourseDetail> courseDetails = studentDetail.getStudentCourseDetailList();
    if (courseDetails == null) {
      courseDetails = List.of(); // 空のリストに置き換える
    }

    courseDetails.forEach(detail -> {
      repository.updateStudentCourse(detail.getStudentCourse());
      repository.updateStatus(detail.getStatus());
    });
    return studentDetail; // StudentDetail を返していることを確認
  }
  //2025.01.19END

  public StudentDetail getStudentDetailById(int id) {
    Student student = repository.searchStudent(id);
    if (student == null) {
      return null;
    }

    List<StudentCourse> studentCourseList = repository.searchStudentCourseListByStudentId(id);
    List<Status> statusList = repository.searchAllStatus();

    Map<Integer, Status> statusMap = statusList.stream()
        .collect(Collectors.toMap(Status::getCourseId, Function.identity()));

    List<StudentCourseDetail> courseDetails = studentCourseList.stream()
        .map(course -> new StudentCourseDetail(course, statusMap.get(course.getId())))
        .collect(Collectors.toList());

    return new StudentDetail(student, courseDetails);
  }

}
