package student.management7.StudentManagement7.domain;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;

@Getter
@Setter
public class StudentDetail {

  private Student student;
  private List<StudentsCourses> studentsCourses;
}

