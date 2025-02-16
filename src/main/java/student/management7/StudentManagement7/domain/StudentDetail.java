package student.management7.StudentManagement7.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;

@Getter
@Setter
public class StudentDetail {
  @Valid
  private Student student;
  @Valid
  private List<StudentCourseDetail> studentCourseDetailList = List.of(); // 初期化;

  public StudentDetail(Student student, List<StudentCourseDetail> studentCourseDetailList) {
    this.student = student;
    this.studentCourseDetailList = studentCourseDetailList;
  }
}

