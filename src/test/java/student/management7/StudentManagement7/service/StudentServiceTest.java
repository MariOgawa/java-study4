package student.management7.StudentManagement7.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import student.management7.StudentManagement7.controller.converter.StudentConverter;
import student.management7.StudentManagement7.data.Jyoukyou;
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

  @InjectMocks
  private StudentService sut;

  @BeforeEach
  void setUp() {
    sut = new StudentService(repository, converter);
  }

  @Test
  void testSearchStudentList() {
    List<Student> studentList = new ArrayList<>();
    List<StudentCourse> studentCourseList = new ArrayList<>();
    List<Jyoukyou> jyoukyouList = new ArrayList<>();

    when(repository.search()).thenReturn(studentList);
    when(repository.searchStudentCourseList()).thenReturn(studentCourseList);
    when(repository.searchAllJyoukyou()).thenReturn(jyoukyouList);

    List<StudentDetail> actual = sut.searchStudentList();
    List<StudentDetail> expected = new ArrayList<>();

    verify(repository, times(1)).search();
    verify(repository, times(1)).searchStudentCourseList();
    verify(repository, times(1)).searchAllJyoukyou();
    assertEquals(expected, actual);
  }

  @Test
  void testSearchStudent() {
    int studentId = 1;
    Student student = new Student();
    student.setId(studentId);

    List<StudentCourse> studentCourses = new ArrayList<>();
    List<Jyoukyou> jyoukyouList = new ArrayList<>();

    when(repository.searchStudent(studentId)).thenReturn(student);
    when(repository.searchStudentsCourses(studentId)).thenReturn(studentCourses);
    when(repository.searchJyoukyouByCourseId(anyInt())).thenReturn(new Jyoukyou());

    StudentDetail actual = sut.searchStudent(studentId);

    verify(repository, times(1)).searchStudent(studentId);
    verify(repository, times(1)).searchStudentsCourses(studentId);
    assertEquals(student, actual.getStudent());
    assertEquals(studentCourses, actual.getStudentCourseList());
    assertEquals(jyoukyouList, actual.getJyoukyouList());
  }


}
