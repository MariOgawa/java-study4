package student.management7.StudentManagement7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.sql.Timestamp;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "受講生コース情報")
@Getter
@Setter
public class StudentCourse {
  //@Pattern(regexp = "^\\d+$")
  @Min(value = 1, message = "IDは1以上でなければなりません")
  //2025.02.02STR
  private Integer id;
  //2025.02.02END
  //@Pattern(regexp = "^\\d+$")
  @Min(value = 1, message = "IDは1以上でなければなりません")
  //2025.02.02STR
  private Integer studentId;
  //2025.02.02END;
  private String courseName;
  private Timestamp courseStartAt;
  private Timestamp courseEndAt;

}

