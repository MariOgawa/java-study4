package student.management7.StudentManagement7.domain;

import jakarta.validation.Valid;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
public class StudentCourseDetail {
  @Valid
  private StudentCourse studentCourse;
  @Valid
  private Status status;

  public StudentCourseDetail(StudentCourse studentCourse, Status status) {
    this.studentCourse = studentCourse;
    this.status = status;
  }
}
