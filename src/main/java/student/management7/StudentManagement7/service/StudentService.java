package student.management7.StudentManagement7.service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.repository.StudentRepository;

@Service
public class StudentService {

    private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }

  public List<Student> searchStudentList() {
    List<Student> students = repository.search();
    return repository.search();
  }

  public StudentDetail searchStudent(String id) {
    Student student = repository.searchStudent(id);
    List<StudentsCourses> studentsCourses = repository.searchStudentsCourses(student.getId());
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentsCourses(studentsCourses);
    return studentDetail;
  }

  public List<StudentsCourses> searchStudentsCourseList() {
    List<StudentsCourses> studentCourses = repository.searchStudentsCoursesList();
    return repository.searchStudentsCoursesList();
  }

  @Transactional
  //public void registerStudent(StudentDetail studentDetail){
  public StudentDetail registerStudent(StudentDetail studentDetail){
    repository.registerStudent(studentDetail.getStudent());
    //コース情報登録

    for (StudentsCourses studentsCourse :studentDetail.getStudentsCourses()){
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      studentsCourse.setCourseStartAt(Timestamp.valueOf(LocalDateTime.now()));
      studentsCourse.setCourseEndAt(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));
      repository.registerStudentsCourses(studentsCourse);
    }
    return studentDetail;
  }

  @Transactional
  public void updateStudent(StudentDetail studentDetail){
    repository.updateStudent(studentDetail.getStudent());
    for (StudentsCourses studentsCourse :studentDetail.getStudentsCourses()){
      studentsCourse.setStudentId(studentDetail.getStudent().getId());
      repository.updateStudentsCourses(studentsCourse);
    }
  }
}

