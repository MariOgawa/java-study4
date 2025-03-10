package student.management7.StudentManagement7.data;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "受講生")
@Getter
@Setter
public class Student {

  //@Pattern(regexp = "^\\d+$")
  @Min(value = 1, message = "IDは1以上でなければなりません")
  //2025.02.02STR
  private Integer id;
  //2025.02.02END
  @NotBlank
  private String name;
  @NotBlank
  private String kanaName;
  @NotBlank
  private String nickname;
  @NotBlank
  private String email;
  @NotBlank
  private String area;
  private int age;
  @NotBlank
  private String sex;
  private String remark;
  private boolean isDeleted;

}

