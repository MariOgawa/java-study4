package student.management7.StudentManagement7.service;


import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.repository.StudentRepository;

@Service
public class StudentService {

    private StudentRepository repository;

  @Autowired
  public StudentService(StudentRepository repository) {
    this.repository = repository;
  }


  public List<StudentDetail> searchStudentList() {

    return List.of();
  }

  public StudentDetail searchStudent(String id) {

    return null;
  }

  public StudentDetail registerStudent(StudentDetail studentDetail) {

    return studentDetail;
  }

  public void updateStudent(StudentDetail studentDetail) {

  }
}
