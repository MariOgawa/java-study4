import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.domain.StudentCourseDetail;
import student.management7.StudentManagement7.repository.StudentRepository;
import student.management7.StudentManagement7.service.StudentService;

class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @InjectMocks
  private StudentService studentService;

  @BeforeEach
  void setUp() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void testSearchStudentList() {
    // テストデータの準備
    Student student = new Student();
    student.setId(1);
    student.setName("Test Student");

    List<Student> studentList = List.of(student);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);

    List<StudentCourse> studentCourseList = List.of(studentCourse);

    Status status = new Status();
    status.setCourseId(1);
    status.setStatus("受講中");

    List<Status> statusList = List.of(status);

    // リポジトリのモック設定
    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchAllStatus()).thenReturn(statusList);

    // メソッド実行
    List<StudentDetail> result = studentService.searchStudentList();

    // 検証
    assertEquals(1, result.size());
    assertEquals("Test Student", result.get(0).getStudent().getName());
    assertEquals(1, result.get(0).getStudentCourseDetailList().size());
    assertEquals("受講中", result.get(0).getStudentCourseDetailList().get(0).getStatus().getStatus());
  }

  @Test
  void testGetStudentCourseDetails() {
    // テストデータの準備
    Student student = new Student();
    student.setId(1);
    student.setName("Test Student");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);

    Status status = new Status();
    status.setCourseId(1);
    status.setStatus("受講中");

    // リポジトリのモック設定
    when(repository.searchStudentCourseById(1)).thenReturn(studentCourse);
    when(repository.searchStudent(1)).thenReturn(student);
    when(repository.searchStatusByStudentCourseId(1)).thenReturn(status);

    // メソッド実行
    StudentDetail result = studentService.getStudentCourseDetails(1);

    // 検証
    assertEquals("Test Student", result.getStudent().getName());
    assertEquals(1, result.getStudentCourseDetailList().size());
    assertEquals("受講中", result.getStudentCourseDetailList().get(0).getStatus().getStatus());
  }

  @Test
  void testRegisterStudent() {
    // テストデータの準備
    Student student = new Student();
    student.setId(1);
    student.setName("Test Student");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);

    Status status = new Status();
    status.setCourseId(1);
    status.setStatus("受講中");

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, status);

    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourseDetail));

    // リポジトリのモック設定
    doNothing().when(repository).registerStudent(student);
    doNothing().when(repository).registerStudentCourse(studentCourse);
    doNothing().when(repository).registerStatus(status);
    when(repository.searchStatusByStudentCourseId(1)).thenReturn(status);

    // メソッド実行
    StudentDetail result = studentService.registerStudent(studentDetail);

    // 検証
    assertEquals("Test Student", result.getStudent().getName());
    assertEquals(1, result.getStudentCourseDetailList().size());
    assertEquals("受講中", result.getStudentCourseDetailList().get(0).getStatus().getStatus());
  }

  @Test
  void testUpdateStudent() {
    // テストデータの準備
    Student student = new Student();
    student.setId(1);
    student.setName("Test Student");

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId(1);
    studentCourse.setStudentId(1);

    Status status = new Status();
    status.setCourseId(1);
    status.setStatus("受講中");

    StudentCourseDetail studentCourseDetail = new StudentCourseDetail(studentCourse, status);

    StudentDetail studentDetail = new StudentDetail(student, List.of(studentCourseDetail));

    // リポジトリのモック設定
    doNothing().when(repository).updateStudent(student);
    doNothing().when(repository).updateStudentCourse(studentCourse);
    doNothing().when(repository).updateStatus(status);

    // メソッド実行
    studentService.updateStudent(studentDetail);

    // 検証
    verify(repository, times(1)).updateStudent(student);
    verify(repository, times(1)).updateStudentCourse(studentCourse);
    verify(repository, times(1)).updateStatus(status);
  }
}
