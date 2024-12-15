
package student.management7.StudentManagement7.controller;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import jakarta.validation.ConstraintViolation;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import student.management7.StudentManagement7.controller.StudentController;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.service.StudentService;

@WebMvcTest(StudentController.class)
class StudentControllerTest {



  @Autowired
  private MockMvc mockMvc;

  @MockBean
  private StudentService service;

  private Validator validator = Validation.buildDefaultValidatorFactory().getValidator();

  @Test
  void 受講生詳細の一覧検索が実行できて空のリストが返ってくること()
      throws Exception, java.lang.Exception {

    mockMvc.perform(MockMvcRequestBuilders.get("/studentList"))
        .andExpect(status().isOk());

    verify(service,times(1)).searchStudentList();
  }

  @Test
  void 受講生詳細の検索が実行できて空のリストが返ってくること()
      throws Exception, java.lang.Exception {
    int id = 999;
    mockMvc.perform(MockMvcRequestBuilders.get("/student/{id}", id))
        .andExpect(status().isOk());

    verify(service,times(1)).searchStudent(id);
  }

  @Test
  void 受講生詳細の登録が実行できて空で返ってくること()
      throws Exception, java.lang.Exception {

    mockMvc.perform(MockMvcRequestBuilders.post("/registerStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
                    "student": {
                        "name": "鈴木　花子",
                        "kanaName": "すずき　はなこ",
                        "nickname": "はーちゃん",
                        "email": "abc2@de.fg",
                        "area": "大阪府",
                        "age": 31,
                        "sex": "F",
                        "remark": "OK"
                    },
                    "studentCourseList": [
                        {
                            "courseName": "英会話"
                        }
                    ]
              }
              """))
    .andExpect(status().isOk());

  verify(service, times(1)).registerStudent(any());
  }

  @Test
  void 受講生詳細の更新が実行できて空で返ってくること()
      throws Exception, java.lang.Exception {

    mockMvc.perform(MockMvcRequestBuilders.put("/updateStudent")
            .contentType(MediaType.APPLICATION_JSON)
            .content("""
              {
                    "student": {
                        "id": "12",
                        "name": "鈴木　花子",
                        "kanaName": "すずき　はなこ",
                        "nickname": "はーちゃん",
                        "email": "abc2@de.fg",
                        "area": "大阪府",
                        "age": 31,
                        "sex": "F",
                        "remark": "OK"
                    },
                    "studentCourseList": [
                        {
                            "id": "15",
                            "studentId": "12",
                            "courseName": "英会話",
                            "courseStartAt": "2024-07-19T15:00:00.000+00:00",
                            "courseEndAt": "2024-12-30T15:00:00.000+00:00"
                        }
                    ]
              }
              """))
        .andExpect(status().isOk());

    verify(service, times(1)).updateStudent(any());
  }

  @Test
  void 受講生詳細の受講生でIDに適切な値を入力した時に入力チェックに異常が発生しないこと(){
    Student student = new Student();
    student.setId(1);
    student.setName("田中");
    student.setKanaName("タナカタロウ");
    student.setNickname("タナカ");
    student.setEmail("ab@cd.com");
    student.setArea("岡山県");
    student.setSex("女性");

    Set<ConstraintViolation<Student>> violation = validator.validate(student);

    Assertions.assertEquals(0, violation.size());
  }

  @Test
  void 受講生詳細の受講生でIDに数字以外を用いたときに入力チェックにかかること(){
    Student student = new Student();
    student.setId(1);
    student.setName("田中");
    student.setKanaName("タナカタロウ");
    student.setNickname("タナカ");
    student.setEmail("ab@cd.com");
    student.setArea("岡山県");
    student.setSex("女性");

    Set<ConstraintViolation<Student>> violation = validator.validate(student);

    Assertions.assertEquals(1, violation.size());
  }


}
