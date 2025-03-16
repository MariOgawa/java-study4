package student.management7.StudentManagement7.repository;

import java.util.List;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import student.management7.StudentManagement7.data.Status;
import student.management7.StudentManagement7.data.Student;
import student.management7.StudentManagement7.data.StudentCourse;

/**
 * 受講生テーブルと受講生コース情報テーブルと紐づくRepositoryです。
 */
@Mapper
@Repository
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
     * 複数のcourseIdに対して一括でStatusを取得するため、IN句を使ったメソッドを追加します。
     *
     * @param courseIds 受講生コースID
     * @return 複数のcourseIdに紐づくStatus情報
     */
    @Select({
        "<script>",
        "SELECT * FROM courses_status",
        "WHERE course_id IN",
        "<foreach item='id' collection='courseIds' open='(' separator=',' close=')'>",
        "#{id}",
        "</foreach>",
        "</script>"
    })
    List<Status> findStatusesByCourseIds(@Param("courseIds") List<Integer> courseIds);

    /**
     * 受講生を新規登録します。IDに関しては自動採番を行う。
     *
     * @param student 受講生
     */
    @Options(useGeneratedKeys = true, keyProperty = "id")
    void registerStudent(Student student);

    /**
     * 受講生コース情報を新規登録します。IDに関しては自動採番を行う。
     *
     * @param studentCourse 受講生コース情報
     */
    void registerStudentCourse(StudentCourse studentCourse);

    //2025.02.02STR
    // courses_status にステータスを登録するメソッドを追加
    @Insert("INSERT INTO courses_status (course_id, status) VALUES (#{courseId}, #{status})")
    void registerStatus(Status status);
    //2025.02.02END

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

    /**
     * 受講生IDに基づいて受講生コース情報のリストを取得
     *
     * @param studentId 受講生ID
     * @return 受講生IDに紐づく受講生コース情報のリスト
     */
    List<StudentCourse> searchStudentCourseListByStudentId(int studentId);

}
