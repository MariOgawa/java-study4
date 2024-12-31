package student.management7.StudentManagement7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "申込状況")
@Getter
@Setter
public class Jyoukyou {
  @Pattern(regexp = "^\\d+$")
  private int id;
  @Pattern(regexp = "^\\d+$")
  private int courseId;
  private String jyoukyou;

}

