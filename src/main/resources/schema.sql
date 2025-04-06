CREATE TABLE IF NOT EXISTS students (
    id              int AUTO_INCREMENT     PRIMARY KEY,
    name            VARCHAR(50),
    kana_name       VARCHAR(50),
    nickname        VARCHAR(50),
    email           VARCHAR(50),
    area            VARCHAR(50),
    age             INT,
    sex             VARCHAR(10),
    remark          TEXT,
    is_deleted boolean DEFAULT FALSE
);

CREATE TABLE IF NOT EXISTS students_courses (
    id              int AUTO_INCREMENT     PRIMARY KEY,
    student_id      int,
    course_name     VARCHAR(50),
    course_start_at TIMESTAMP,
    course_end_at   TIMESTAMP
);

CREATE TABLE IF NOT EXISTS courses_status (
    id              INT AUTO_INCREMENT     PRIMARY KEY,
    course_id       INT,
    status          VARCHAR(36)
);