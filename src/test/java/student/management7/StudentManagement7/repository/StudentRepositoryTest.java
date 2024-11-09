package student.management7.StudentManagement7.repository;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mybatis.spring.boot.test.autoconfigure.MybatisTest;
import org.springframework.beans.factory.annotation.Autowired;
import student.management7.StudentManagement7.data.Student;

@MybatisTest
class StudentRepositoryTest {

  @Autowired
  private StudentRepository sut;

  @Test
  void 受講生の全件検索が行えること(){
    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(3);
  }
  @Test
  void 受講生の登録が行えること(){
    Student student = new Student();

    student.setName("田中　太郎");
    student.setKanaName("タナカ　タロウ");
    student.setNickname("たーくん");
    student.setEmail("abc@de.fg");
    student.setArea("奈良県");
    student.setAge(21);
    student.setSex("M");
    student.setRemark("OK");
    student.setDeleted(false);

    sut.registerStudent(student);

    List<Student> actual = sut.search();
    assertThat(actual.size()).isEqualTo(4);
  }

}