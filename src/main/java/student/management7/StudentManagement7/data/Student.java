package student.management7.StudentManagement7.data;

import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.NotBlank;

@Getter
@Setter
public class Student {
//  @NotBlank
  private String id;
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

