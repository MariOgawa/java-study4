package student.management7.StudentManagement7.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentCourseDetail;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.service.StudentService;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)  // Mockitoの拡張
public class StudentControllerTest {

  @Mock
  private StudentService studentService;  // Serviceのモック

  @InjectMocks
  private StudentController studentController;  // Controllerのインスタンス

  private MockMvc mockMvc;
  private ObjectMapper objectMapper;  // JSONの処理用
  private StudentDetail studentDetail;

  @BeforeEach
  void setUp() {
    mockMvc = MockMvcBuilders.standaloneSetup(studentController).build();
    objectMapper = new ObjectMapper();
  }

  @Test
  void testGetStudentList() throws Exception {
    // モックしたサービスから返すデータを設定
    Student student1 = new Student();
    student1.setId(1);
    student1.setName("山田太郎");

    Student student2 = new Student();
    student2.setId(2);
    student2.setName("鈴木一郎");

    // StudentDetailリストを作成
    StudentDetail studentDetail1 = new StudentDetail(student1, List.of());
    StudentDetail studentDetail2 = new StudentDetail(student2, List.of());

    List<StudentDetail> studentList = Arrays.asList(studentDetail1, studentDetail2);

    // サービスのモックを設定
    when(studentService.searchStudentList()).thenReturn(studentList);

    // エンドポイントをテスト
    try {

      mockMvc.perform(get("/studentList"))
          .andDo(print())  // レスポンス内容を出力
          .andExpect(status().isOk())  // HTTP ステータス 200 OK
          .andExpect(jsonPath("$[0].student.id").value(1))  // 1番目の受講生の ID (student オブジェクト内)
          .andExpect(jsonPath("$[1].student.id").value(2))  // 2番目の受講生の ID (student オブジェクト内)
          .andExpect(jsonPath("$[0].student.name").value("山田太郎"));  // 1番目の受講生の名前 (student オブジェクト内)

    } catch (java.lang.Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testGetStudentDetail() throws Exception {
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");

    //StudentDetail studentDetail = new StudentDetail(student, null);
    // StudentCourseDetailリストを作成
    StudentCourse studentCourse = new StudentCourse();
    Status status = new Status();
    status.setId(1);
    status.setCourseId(1);
    status.setStatus("仮申込");
    StudentCourseDetail courseDetail = new StudentCourseDetail(studentCourse, status);
    List<StudentCourseDetail> courseDetails = List.of(courseDetail);

    StudentDetail studentDetail = new StudentDetail(student, courseDetails);

    when(studentService.getStudentDetailById(1)).thenReturn(studentDetail);

    try {
      mockMvc.perform(get("/student/1"))
          //.andExpect(status().isOk())
          .andExpect(jsonPath("$.status.status").value("仮申込"))
          .andExpect(jsonPath("$.student.id").value(1))  // studentオブジェクト内のidにアクセス
          .andExpect(jsonPath("$.student.name").value("山田太郎"));  // studentオブジェクト内のnameにアクセス
    } catch (java.lang.Exception e) {
      throw new RuntimeException(e);
    }
  }


  @Test
  void testRegisterStudent() throws Exception {
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");

    //StudentDetail studentDetail = new StudentDetail(student, null);
    // StudentCourseDetailリストを作成
    StudentCourse studentCourse = new StudentCourse();
    Status status = new Status();
    status.setId(1);
    status.setCourseId(1);
    status.setStatus("仮申込");
    StudentCourseDetail courseDetail = new StudentCourseDetail(studentCourse, status);
    List<StudentCourseDetail> courseDetails = List.of(courseDetail);
    when(studentService.registerStudent(any(StudentDetail.class))).thenReturn(studentDetail);

    try {
      mockMvc.perform(post("/registerStudent")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(studentDetail)))
          .andExpect(status().isOk())
          .andExpect(jsonPath("$.student.id").value(1))
          .andExpect(jsonPath("$.student.name").value("山田太郎"));
    } catch (java.lang.Exception e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testUpdateStudent() throws Exception {
    Student student = new Student();
    student.setId(1);
    student.setName("山田太郎");
    student.setKanaName("ヤマダタロウ");
    student.setNickname("タロ");
    student.setEmail("taro@example.com");
    student.setAge(25);
    student.setSex("男性");

    // 適切なStudentCourseDetailオブジェクトをリストに追加
    StudentCourse studentCourse = new StudentCourse();  // 必要なら適切な引数を渡す
    Status status = new Status();
    status.setId(1);
    status.setCourseId(1);
    status.setStatus("仮申込");
    StudentCourseDetail courseDetail = new StudentCourseDetail(studentCourse, status);

    // studentCourseDetailList を空リストではなく、正しいデータでセット
    List<StudentCourseDetail> courseDetails = List.of(courseDetail);

    //StudentDetail studentDetail = new StudentDetail(student, courseDetails); // ここでnullや空のリストを渡さないようにする
    StudentDetail studentDetail = new StudentDetail(student, courseDetails != null ? courseDetails : Collections.emptyList());

    when(studentService.updateStudent(any(StudentDetail.class))).thenReturn(studentDetail);

    try {
      mockMvc.perform(put("/updateStudent")
              .contentType(MediaType.APPLICATION_JSON)
              .content(objectMapper.writeValueAsString(studentDetail)))
          .andExpect(status().isOk())
          .andExpect(content().string("更新処理が成功しました。"));
    } catch (java.lang.Exception e) {
      throw new RuntimeException(e);
    }
  }
}
