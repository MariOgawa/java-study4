package student.management7.StudentManagement7.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentsCourses;
import student.management7.StudentManagement7.service.StudentService;
import student.management7.StudentManagement7.domain.StudentDetail;

@Controller
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
  public String getStudentList(Model model){
    List<Student> students = service.searchStudent();
    List<StudentsCourses> studentsCourses =service.searchStudentsCourse();

    model.addAttribute("studentList", converter.convertStudentDetails(students,studentsCourses));
    return "studentList";
  }

  @GetMapping("/studentsCourseList")
  public List<StudentsCourses> searchStudentsCourseList(){
    return service.searchStudentsCourse();
  }

  @GetMapping("/studentsIn30s")
  public List<Student> searchStudentsIn30s() {
    return service.searchStudentsIn30s();
  }

  @GetMapping("/javaCourses")
  public List<StudentsCourses> searchJavaCourses() {
    return service.searchJavaCourses();
  }

  @GetMapping("/newStudent")
  public String newStudent(Model model){
    model.addAttribute("studentDetail", new StudentDetail());
    return "registerStudent";
  }

  @PostMapping("/registerStudent")
  public String registerStudent(@ModelAttribute StudentDetail studentDetail, BindingResult result){
    if(result.hasErrors()){
      return "registerStudent";
    }
    // サービスを利用してデータベースに登録
    service.registerStudent(studentDetail);

    System.out.println(
        studentDetail.getStudent().getName() + "さんが新規受講生として登録されました。");
    return "redirect:/studentList";
  }
}
