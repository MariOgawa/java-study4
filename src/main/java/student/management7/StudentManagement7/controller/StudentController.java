package student.management7.StudentManagement7.controller;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;
import student.management7.StudentManagement7.service.StudentService;
import student.management7.StudentManagement7.domain.StudentDetail;

//@Controller
@RestController
public class StudentController {

  @Autowired
  private StudentService service;
  private StudentConverter converter;

  @Autowired
  public StudentController(StudentService service, StudentConverter converter){
    this.service = service;
    this.converter = converter;
  };

  @GetMapping("/studentList")
  //public String getStudentList(Model model){
  public List<StudentDetail> getStudentList(){
    List<Student> students = service.searchStudentList();
    List<StudentsCourses> studentsCourses =service.searchStudentsCourseList();

  //  model.addAttribute("studentList", converter.convertStudentDetails(students,studentsCourses));
  //  return "studentList";
    return converter.convertStudentDetails(students, studentsCourses);
  }


  @GetMapping("/student/{id}")
  public String getStudent(@PathVariable String id, Model model){
    StudentDetail studentDetail = service.searchStudent(id);
    model.addAttribute("studentDetail", studentDetail);
    return "updateStudent";
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> searchStudentsCourseList(){
    return service.searchStudentsCourseList();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model){
    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudentsCourses(Arrays.asList(new StudentsCourses()));
    model.addAttribute("studentDetail", studentDetail);
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "registerStudent";
    }
    // サービスを利用してデータベースに登録
    service.registerStudent(studentDetail);
    return "redirect:/studentList";
  }

  @PostMapping("/updateStudent")
  //public String updateStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
  public ResponseEntity<String> updateStudent(@RequestBody StudentDetail studentDetail){
    service.updateStudent(studentDetail);
  //  return "redirect:/studentList";
    return ResponseEntity.ok("更新処理が成功しました。");
  }
}
