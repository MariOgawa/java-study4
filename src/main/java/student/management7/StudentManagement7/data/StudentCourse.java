package student.management7.StudentManagement7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {
  @Pattern(regexp = "^\\d+$")
  private int id;
  @Pattern(regexp = "^\\d+$")
  private int studentId;
  private String courseName;
  private Timestamp courseStartAt;
  private Timestamp courseEndAt;

}

