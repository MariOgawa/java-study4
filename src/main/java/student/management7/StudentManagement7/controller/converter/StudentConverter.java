package student.management7.StudentManagement7.controller.converter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.stereotype.Component;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;
import student.management7.StudentManagement7.domain.StudentCourseDetail;
import student.management7.StudentManagement7.domain.StudentDetail;

@Component
public class StudentConverter {

  /**
   * 受講生に紐づく受講生コース情報と申込状況をマッピングする。
   *
   * @param studentList 受講生一覧
   * @param studentCourseList 受講生コース情報のリスト
   * @param statusList 申込状況のリスト
   * @return 受講生詳細情報のリスト
   */
  public List<StudentDetail> convertStudentDetails(List<Student> studentList,
      List<StudentCourse> studentCourseList, List<Status> statusList) {

    Map<Integer, Status> statusMap = statusList.stream()
        .collect(Collectors.toMap(Status::getCourseId, Function.identity()));

    List<StudentDetail> studentDetails = new ArrayList<>();

    studentList.forEach(student -> {

      List<StudentCourseDetail> courseDetails = studentCourseList.stream()
          .filter(studentCourse -> student.getId() == studentCourse.getStudentId())
          .map(studentCourse -> new StudentCourseDetail(studentCourse, statusMap.get(studentCourse.getId())))
          .collect(Collectors.toList());

      StudentDetail studentDetail = new StudentDetail(student, courseDetails);
      studentDetails.add(studentDetail);
    });
    return studentDetails;
  }
}
