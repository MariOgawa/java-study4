package student.management7.StudentManagement7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Schema(description = "申込状況")
@Getter
@Setter
public class Status {
  //@Pattern(regexp = "^\\d+$")
  @Min(value = 1, message = "IDは1以上でなければなりません")
  //2025.02.02STR
  private Integer id;
  //2025.02.02END
  //@Pattern(regexp = "^\\d+$")
  @Min(value = 1, message = "IDは1以上でなければなりません")
  //2025.02.02STR
  private Integer courseId;
  //2025.02.02END
  private String status;

}

