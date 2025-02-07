package student.management7.StudentManagement7.repository;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
public interface StudentRepository {

    /**
     * 受講生の全件検索を行います。
     *
     * @return 受講生一覧(全件検索)
     */
    List<Student> search();

    /**
     * 受講生の検索を行います。
     *
     * @param id 受講生ID
     * @return 受講生
     */
    Student searchStudent(int id);

    /** * 受講生コース情報の全件検索を行います。 * * @return 受講生コース情報(全件) */
    List<StudentCourse> searchStudentsCourse();

    /**
     * 受講生のコース情報の全件検索を行います。
     *
     * @return 受講生のコース情報(全件)
     */
    List<StudentCourse> searchStudentCourseList();

    /** * 全ての申込状況を検索します。
     * *
     * * @return 申込状況一覧 */
    List<Status> searchAllStatus();
    StudentCourse searchStudentCourseById(int id);

    /**
     * 受講生IDに紐づく受講生コース情報を検索します。
     *
     * @param studentId 受講生ID
     * @return 受講生IDに紐づく受講生コース情報
     */
    List<StudentCourse> searchStudentsCourses(int studentId);

    /**
     * 受講生を新規登録します。IDに関しては自動採番を行う。
     *
     * @param student 受講生
     */
    //@Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
     *
     * @param studentCourse 受講生コース情報
     */
    void registerStudentCourse(StudentCourse studentCourse);

    /**
     * 受講生を更新します。
     *
     * @param student 受講生
     */
    void updateStudent(Student student);

    /**
     *受講生コース情報のコース名を更新します。
     *
     * @param studentCourse 受講生コース情報
     */
    void updateStudentCourse(StudentCourse studentCourse);

    /**
     * 申込状況をコースIDで検索します。
     *
     * @param courseId コースID
     * @return 申込状況
     */
    Status searchStatusByCourseId(int courseId);

    /**
     * StudentCourseのIDをキーにしてStatusを検索します。
     *
     * @param id StudentCourseのID
     * @return 紐づくStatus
     */
    Status searchStatusByStudentCourseId(int id);

    /**
     * 申込状況（Status）を更新します。
     *
     * @param status 更新する申込状況オブジェクト
     */
    void updateStatus(Status status);

}
