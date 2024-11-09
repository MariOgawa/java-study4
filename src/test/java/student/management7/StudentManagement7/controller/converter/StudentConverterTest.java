package student.management7.StudentManagement7.controller.converter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentDetail;

class StudentConverterTest {

  private StudentConverter sut;

  @BeforeEach
  void before(){
    sut = new StudentConverter();
  }

  @Test
  void make_list(){
//    void 受講生のリストと受講生コース情報のリストを渡して受講生詳細のリストが作成できること}(){
    Student student = new Student();
    student.setId("1");
    student.setName("田中　太郎");
    student.setKanaName("タナカ　タロウ");
    student.setNickname("たーくん");
    student.setEmail("abc@de.fg");
    student.setArea("奈良県");
    student.setAge(21);
    student.setSex("M");
    student.setRemark("OK");
    student.setDeleted(false);

    StudentCourse studentCourse = new StudentCourse();
    studentCourse.setId("1");
    studentCourse.setStudentId("1");
    studentCourse.setCourseName("JAVA");


// LocalDateTimeをTimestampに変換して設定
    studentCourse.setCourseStartAt(Timestamp.valueOf(LocalDateTime.now()));
    studentCourse.setCourseEndAt(Timestamp.valueOf(LocalDateTime.now().plusYears(1)));

    List<Student> studentList = List.of(student);
    List<StudentCourse> studentCourseList = List.of(studentCourse);

    List<StudentDetail> actual = sut.convertStudentDetails(studentList, studentCourseList);

    assertThat(actual.get(0).getStudent()).isEqualTo(student);
    assertThat(actual.get(0).getStudentCourseList()).isEqualTo(studentCourseList);

  }
}