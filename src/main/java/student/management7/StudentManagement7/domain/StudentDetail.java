package student.management7.StudentManagement7.domain;

import jakarta.validation.Valid;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import student.management7.StudentManagement7.data.Jyoukyou;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentDetail {
  @Valid
  private Student student;
  @Valid
  private List<StudentCourse> studentCourseList;
  @Valid
  private List<Jyoukyou>jyoukyouList;

}

