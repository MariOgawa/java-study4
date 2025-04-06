package student.management7.StudentManagement7.controller;

import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.service.StudentService;

/**
 * 受講生の検索や登録、更新などを行うREST APIとして受け付けるControllerです。
 */
@Validated
@RestController
public class StudentController {

  /** 受講生サービス */
  private final StudentService service;
  private Process log;

  /**
   * コンストラクタ
   *
   * @param service 受講生サービス
   */  
  @Autowired
  public StudentController(StudentService service) {
    this.service = service;
  }

  /**
   * 受講生一覧検索です。 全件検索を行うので、条件指定は行いません。
   *
   * @return 受講生一覧(全件)
   */  
  @Operation(summary = "一覧検索", description = "受講生の一覧を検索します。")
  @GetMapping("/studentList")
  public List<StudentDetail> getStudentList() {
    return service.searchStudentList();
  }

  /**
   * StudentのIDでStatus等の受講生情報を検索します。
   *
   * @param id StudentのID
   * @return 紐づくStatus等の受講生情報
   */
  //2025.01.19ST
  @GetMapping("/student/{id}")
  public ResponseEntity<StudentDetail> getStudentDetail(@PathVariable int id) {
    List<StudentDetail> studentDetails = service.searchStudentList();
    StudentDetail studentDetail = studentDetails.stream()
        .filter(detail -> detail.getStudent().getId() == id)
        .findFirst()
        .orElse(null);

    if (studentDetail == null) {
      return ResponseEntity.notFound().build();
    }
    return ResponseEntity.ok(studentDetail);
  }
  //2025.01.19END

  /**
   * StudentCourseのIDでStatusを検索します。
   *
   * @param id StudentCourseのID
   * @return 紐づくStatus
   */
  @Operation(summary = "StudentCourse詳細", description = "StudentCourseの詳細情報を取得します")
  @GetMapping("/studentCourse/{id}")
  public ResponseEntity<StudentDetail> getStudentCourseDetail(@PathVariable int id) {
    StudentDetail studentDetail = service.getStudentCourseDetails(id);
    return ResponseEntity.ok(studentDetail);
  }

  /**
   * 受講生詳細の登録を行います。
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */  
  @Operation(summary = "受講生登録", description = "受講生を登録します。")
  @PostMapping("/registerStudent")
  public ResponseEntity<StudentDetail> registerStudent(@RequestBody @Valid StudentDetail studentDetail) {
    StudentDetail responseStudentDetail = service.registerStudent(studentDetail);
    return ResponseEntity.ok(responseStudentDetail);
  }

  /**
   * 受講生詳細の登録を行います。キャンセルフラグの更新もここで行います(論理削除)
   *
   * @param studentDetail 受講生詳細
   * @return 実行結果
   */  
  @Operation(summary = "受講生更新とキャンセル", description = "受講生更新とキャンセルを行います。")
  @PutMapping("/updateStudent")
  public ResponseEntity<String> updateStudent(@RequestBody @Valid StudentDetail studentDetail) {
    service.updateStudent(studentDetail);
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
