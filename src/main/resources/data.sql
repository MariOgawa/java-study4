INSERT INTO students ( name, kana_name, nickname, email, area, age, sex, is_deleted)
VALUES ( '山田太郎', 'ヤマダタロウ', 'タロ', 'taro@example.com', '東京', 25, '男性', false ),
       ( '鈴木一郎', 'スズキイチロウ', 'イチ', 'ichi@example.com', '大阪', 30, '男性', false ),
       ( '田中花子', 'タナカハナコ', 'ハナ', 'hana@example.com', '北海道', 22, '女性', true );

INSERT INTO students_courses (student_id, course_name, course_start_at, course_end_at)
VALUES ( 1, 'Java', '2023-04-01 09:03:00', '2024-04-01 09:03:00' ),
       ( 2, 'デザイン', '2023-06-12 09:14:00', '2024-06-12 09:14:00' ),
       ( 3, 'Java', '2023-07-23 09:55:00', '2024-07-23 09:55:00' );

insert into courses_status (course_id, status)
values ( 1,'仮申込'),
       ( 2,'仮申込'),
       ( 3,'受講中');