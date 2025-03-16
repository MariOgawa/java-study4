package student.management7.StudentManagement7.domain;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import student.management7.StudentManagement7.data.Student;

@Getter
@Setter
public class StudentDetail {
  @Valid
  private Student student;

  @Valid
  private List<StudentCourseDetail> studentCourseDetailList;

  // コンストラクタで studentCourseDetailList を必須にする
  public StudentDetail(Student student, List<StudentCourseDetail> studentCourseDetailList) {
    this.student = student;
    // null チェックして、null の場合は例外をスロー
    if (studentCourseDetailList == null || studentCourseDetailList.isEmpty()) {
      throw new IllegalArgumentException("studentCourseDetailList cannot be null or empty");
    }
    this.studentCourseDetailList = studentCourseDetailList;
  }
}

