package student.management7.StudentManagement7.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.exception.TestException;
import student.management7.StudentManagement7.service.StudentService;

@Validated
@RestController
public class Exception extends Throwable {
  private final StudentService service;
  @Autowired
  public Exception(StudentService service) {
    this.service = service;
  }

  @GetMapping("/student/List")
  public List<StudentDetail> getStudentList() throws TestException {
    throw new TestException("エラーが発生しました。");
  }
  @ExceptionHandler(TestException.class)
  public ResponseEntity<String> handleTestException(TestException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
  }

  public StudentService getService() {
    return service;
  }
}
