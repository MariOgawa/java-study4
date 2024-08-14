package student.management7.StudentManagement7.data;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentsCourses {
  private String id;
  private String studentid;
  private String courseName;
  private Timestamp coursestartat;
  private Timestamp courseendat;
}

