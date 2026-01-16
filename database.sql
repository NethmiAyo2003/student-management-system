-- Create Database
CREATE DATABASE student_management_system;
USE student_management_system;

-- Users table (for authentication)
CREATE TABLE users (
    user_id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    email VARCHAR(100),
    role ENUM('student', 'lecturer', 'admin') NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Lecturers table
CREATE TABLE lecturers (
    lecturer_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    department VARCHAR(100),
    phone VARCHAR(20),
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Students table
CREATE TABLE students (
    student_id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    registration_number VARCHAR(20) UNIQUE NOT NULL,
    email VARCHAR(100),
    phone VARCHAR(20),
    department VARCHAR(100),
    year_of_study INT,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE
);

-- Courses table
CREATE TABLE courses (
    course_id INT PRIMARY KEY AUTO_INCREMENT,
    course_code VARCHAR(20) UNIQUE NOT NULL,
    course_name VARCHAR(100) NOT NULL,
    lecturer_id INT,
    credits INT,
    semester VARCHAR(20),
    FOREIGN KEY (lecturer_id) REFERENCES lecturers(lecturer_id)
);

-- Enrollments table
CREATE TABLE enrollments (
    enrollment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    enrollment_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Grades table
CREATE TABLE grades (
    grade_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    assignment_name VARCHAR(100),
    marks DECIMAL(5,2),
    max_marks DECIMAL(5,2),
    grade_date DATE,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Timetable table
CREATE TABLE timetable (
    timetable_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    day_of_week ENUM('Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday', 'Sunday'),
    start_time TIME,
    end_time TIME,
    room_number VARCHAR(20),
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Lecture Slides table
CREATE TABLE lecture_slides (
    slide_id INT PRIMARY KEY AUTO_INCREMENT,
    course_id INT,
    title VARCHAR(200) NOT NULL,
    file_path VARCHAR(500),
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    description TEXT,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Attendance table
CREATE TABLE attendance (
    attendance_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    course_id INT,
    percentage INT,
    last_updated TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE,
    FOREIGN KEY (course_id) REFERENCES courses(course_id) ON DELETE CASCADE
);

-- Payments table
CREATE TABLE payments (
    payment_id INT PRIMARY KEY AUTO_INCREMENT,
    student_id INT,
    amount DECIMAL(10,2),
    payment_date DATE,
    description VARCHAR(200),
    FOREIGN KEY (student_id) REFERENCES students(student_id) ON DELETE CASCADE
);
-- Insert sample users
INSERT INTO users (username, password, email, role) VALUES
('admin', 'admin123', 'admin@university.edu', 'admin'),
('lecturer1', 'password123', 'lecturer1@university.edu', 'lecturer'),
('student1', 'password123', 'student1@university.edu', 'student'),
('student2', 'password123', 'student2@university.edu', 'student'),
('student3', 'password123', 'student3@university.edu', 'student');

-- Insert lecturer
INSERT INTO lecturers (user_id, first_name, last_name, department, phone) VALUES
(2, 'Kamal', 'Athauda', 'Computer Science', '0771234567');

-- Insert students
INSERT INTO students (user_id, first_name, last_name, registration_number, email, phone, department, year_of_study) VALUES
(3, 'Suhas', 'Nilgala', 'CS2021001', 'alice@university.edu', '0771111111', 'Computer Science', 2),
(4, 'Kavi', 'Williams', 'CS2021002', 'bob@university.edu', '0772222222', 'Computer Science', 2),
(5, 'Nishi', 'Perera', 'CS2021003', 'carol@university.edu', '0773333333', 'Computer Science', 2);

-- Insert courses
INSERT INTO courses (course_code, course_name, lecturer_id, credits, semester) VALUES
('PUSL2024', 'Software Engineering 2', 1, 20, 'Semester 2'),
('CS2023', 'Database Management', 1, 15, 'Semester 2');

-- Insert enrollments
INSERT INTO enrollments (student_id, course_id, enrollment_date) VALUES
(1, 1, '2024-01-15'),
(2, 1, '2024-01-15'),
(3, 1, '2024-01-15'),
(1, 2, '2024-01-15'),
(2, 2, '2024-01-15');

-- Insert sample grades
INSERT INTO grades (student_id, course_id, assignment_name, marks, max_marks, grade_date) VALUES
(1, 1, 'Assignment 1', 85.00, 100.00, '2024-02-20'),
(1, 1, 'Assignment 2', 78.00, 100.00, '2024-03-15'),
(2, 1, 'Assignment 1', 92.00, 100.00, '2024-02-20'),
(2, 1, 'Assignment 2', 88.00, 100.00, '2024-03-15'),
(3, 1, 'Assignment 1', 70.00, 100.00, '2024-02-20'),
(3, 1, 'Assignment 2', 75.00, 100.00, '2024-03-15');

-- Insert timetable
INSERT INTO timetable (course_id, day_of_week, start_time, end_time, room_number) VALUES
(1, 'Monday', '09:00:00', '11:00:00', 'Room 101'),
(1, 'Wednesday', '14:00:00', '16:00:00', 'Room 101'),
(2, 'Tuesday', '10:00:00', '12:00:00', 'Lab 203');

-- Insert lecture slides
INSERT INTO lecture_slides (course_id, title, file_path, description) VALUES
(1, 'Introduction to Software Engineering', '/slides/se_intro.pdf', 'Week 1 - Course Introduction'),
(1, 'OOP Principles', '/slides/oop_principles.pdf', 'Week 2 - Object Oriented Programming'),
(1, 'Design Patterns', '/slides/design_patterns.pdf', 'Week 3 - Common Design Patterns');
SELECT * FROM student_management_system.enrollments;