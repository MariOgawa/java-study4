package student.management7.StudentManagement7.domain;

import jakarta.validation.Valid;
import java.util.Objects;
import lombok.Getter;
import lombok.Setter;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.StudentCourse;

@Getter
@Setter
public class StudentCourseDetail {
  @Valid
  private StudentCourse studentCourse;
  @Valid
  private Status status;

  public StudentCourseDetail(StudentCourse studentCourse, Status status) {
    this.studentCourse = studentCourse;
    this.status = status;
  }

  // equalsメソッドのオーバーライド
  @Override
  public boolean equals(Object obj) {
    if (this == obj) return true;
    if (obj == null || getClass() != obj.getClass()) return false;
    StudentCourseDetail that = (StudentCourseDetail) obj;
    return Objects.equals(studentCourse, that.studentCourse) &&
        Objects.equals(status, that.status);
  }

  // hashCodeメソッドのオーバーライド
  @Override
  public int hashCode() {
    return Objects.hash(studentCourse, status);
  }
}
