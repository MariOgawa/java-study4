package student.management7.StudentManagement7;

import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StudentCourse {
  private String id;
  private String studentid;
  private String coursename;
  private Timestamp coursestartat;
  private Timestamp courseendat;
}

