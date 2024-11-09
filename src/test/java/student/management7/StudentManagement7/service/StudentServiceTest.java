package student.management7.StudentManagement7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentDetail;
import student.management7.StudentManagement7.repository.StudentRepository;

@ExtendWith(MockitoExtension.class)
class StudentServiceTest {

  @Mock
  private StudentRepository repository;

  @Mock
  private StudentConverter converter;

  private StudentService sut;

  @BeforeEach
  void before(){
    sut = new StudentService(repository, converter);
  }

  @Test
  void 受講生詳細の一覧検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    // 事前準備
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);

    //実行
    List<StudentDetail> actual = sut.searchStudentList();

    //sut.searchStudentList();

    // 検証
    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(converter, times(1)).convertStudentDetails(studentList,studentCourseList);
  }

  @Test
  void 受講生詳細の検索_リポジトリとコンバーターの処理が適切に呼び出せていること() {
    // 事前準備
    String studentId = "55";
    Student student = new Student();
    student.setId(studentId);

    List<StudentCourse> studentCourses = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentsCourses(studentId)).thenReturn(studentCourses);

    // 実行
    StudentDetail actual = sut.searchStudent(studentId);

    // 検証
    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentsCourses(studentId);
    assertEquals(student, actual.getStudent());
    assertEquals(studentCourses, actual.getStudentCourseList());
  }

  @Test
  void 受講生詳細の登録_受講生とコース情報が正しく登録されること() {
    // 事前準備
    String id = "999";
    Student student = new Student();
    student.setId(id);

    StudentCourse course1 = new StudentCourse();

    List<StudentCourse> studentCourseList = Arrays.asList(course1);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    // 実行
    StudentDetail actual = sut.registerStudent(studentDetail);

    // 検証
    verify(repository, times(1)).registerStudent(student);  // 受講生が正しく登録されるか
    verify(repository, times(1)).registerStudentCourse(course1);  // 各コースが正しく登録されるか

    // コース情報の初期化が行われたかどうかを確認
    assertEquals(id, course1.getStudentId());

    assertNotNull(course1.getCourseStartAt());  // コース開始日が設定されているか
    assertNotNull(course1.getCourseEndAt());    // コース終了日が設定されているか
  }

  @Test
  void 受講生詳細の更新_受講生とコース情報が正しく更新されること() {
    // 事前準備
    Student student = new Student();
    student.setId("55");

    StudentCourse course1 = new StudentCourse();

    List<StudentCourse> studentCourseList = Arrays.asList(course1);

    StudentDetail studentDetail = new StudentDetail();
    studentDetail.setStudent(student);
    studentDetail.setStudentCourseList(studentCourseList);

    // 実行
    sut.updateStudent(studentDetail);

    // 検証
    // 受講生情報の更新がリポジトリで1回呼び出されていること
    verify(repository, times(1)).updateStudent(student);

    // 各コース情報の更新がリポジトリで1回ずつ呼び出されていること
    verify(repository, times(1)).updateStudentCourse(course1);
  }

}