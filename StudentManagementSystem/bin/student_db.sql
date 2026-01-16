CREATE DATABASE IF NOT EXISTS student_db;
USE student_db;

-- ================= STUDENTS =================
CREATE TABLE students (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100),
    age INT,
    grade VARCHAR(10),
    username VARCHAR(50),
    password VARCHAR(50),
    email VARCHAR(100),
    phone VARCHAR(15),
    dob DATE,
    gender VARCHAR(10),
    degree VARCHAR(100),
    faculty VARCHAR(100),
    enrollment_year INT,
    address VARCHAR(255)
);

INSERT INTO students 
(name, age, grade, username, password, email, phone, dob, gender, degree, faculty, enrollment_year, address)
VALUES
(
    "John Peter",
    21,
    "Y2S1",
    "john",
    "1234",
    "john.peter@student.edu",
    "0771234567",
    "2003-04-15",
    "Male",
    "BSc (Hons) Computer Networks",
    "Faculty of Computing",
    2023,
    "Colombo, Sri Lanka"
);

-- ================= GPA & FINAL RESULT =================
CREATE TABLE grades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_name VARCHAR(100),
    grade VARCHAR(5),
    gpa DOUBLE,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO grades (student_id, course_name, grade, gpa)
VALUES
(1, "Programming", "A", 3.72),
(1, "Database Systems", "A-", 3.72),
(1, "Computer Networks", "B+", 3.72);

-- ================= PAYMENTS =================
CREATE TABLE payments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    amount DOUBLE,
    date DATE,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO payments (student_id, amount, date)
VALUES
(1, 25000, "2024-02-15"),
(1, 15000, "2024-05-10"),
(1, 32000, "2024-08-20");

-- ================= ENROLLED COURSES =================
CREATE TABLE enrollments (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_name VARCHAR(100),
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO enrollments (student_id, course_name)
VALUES
(1, "Programming"),
(1, "Database Systems"),
(1, "Computer Networks"),
(1, "Web Development");

-- ================= ATTENDANCE =================
CREATE TABLE attendance (
    id INT AUTO_INCREMENT PRIMARY KEY,
    student_id INT,
    course_name VARCHAR(100),
    percentage INT,
    FOREIGN KEY (student_id) REFERENCES students(id)
);

INSERT INTO attendance (student_id, course_name, percentage)
VALUES
(1, "Programming", 92),
(1, "Database Systems", 88),
(1, "Computer Networks", 90);
