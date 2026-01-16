# Student Management System

A comprehensive Java-based Student Management System for universities with separate dashboards for Students, Lecturers, and Administrators.

## Features

- **Login & Registration System** - Secure authentication for students, lecturers, and admins
- **Student Dashboard** - View courses, grades, attendance, and personal information
- **Lecturer Dashboard** - Manage courses, upload lecture slides, track student performance
- **Admin Dashboard** - Manage students, lecturers, and system settings
- **Course Management** - Create and manage courses, enrollments, and timetables
- **Grade Tracking** - Record and view student grades and assignments
- **Attendance System** - Track student attendance by course
- **Payment Records** - Manage student fee payments

## Project Structure

```
nethmi-new/
├── src/                    # Source code
│   ├── Admin/             # Admin dashboard components
│   ├── Register/          # Login and registration modules
│   ├── Student/           # Student dashboard components
│   ├── DBConfig.java      # Database configuration (UPDATE THIS!)
│   ├── *DAO.java          # Data Access Objects
│   └── *.java             # Other UI and model classes
├── lib/                   # Libraries
│   └── mysql-connector-j-9.5.0.jar
└── database.sql          # Complete database schema
```

## Setup Instructions

### 1. Database Setup

1. Import the database schema:
   ```bash
   mysql -u your_username -p < database.sql
   ```

2. This will create:
   - Database: `student_management_system`
   - Tables: users, students, lecturers, courses, enrollments, grades, timetable, lecture_slides, attendance, payments
   - Sample data for testing

### 2. Configure Database Connection

**IMPORTANT:** Update your database credentials in `src/DBConfig.java`

```java
// Update these lines with your VPS MySQL credentials:
private static final String DB_URL = "jdbc:mysql://YOUR_VPS_IP:3306/student_management_system";
private static final String DB_USER = "your_mysql_username";
private static final String DB_PASSWORD = "your_mysql_password";
```

For remote VPS connection:
```java
private static final String DB_URL = "jdbc:mysql://192.168.1.100:3306/student_management_system";
```

For localhost:
```java
private static final String DB_URL = "jdbc:mysql://localhost:3306/student_management_system";
```

### 3. Test Database Connection

```bash
# Compile and run the DBConfig test
javac -cp "lib/mysql-connector-j-9.5.0.jar:." src/DBConfig.java
java -cp "lib/mysql-connector-j-9.5.0.jar:src" DBConfig
```

You should see: `✓ Database connected successfully!`

### 4. Compile and Run

#### Compile all Java files:
```bash
javac -cp "lib/mysql-connector-j-9.5.0.jar:src" src/**/*.java src/*.java
```

#### Run the Login/Register interface:
```bash
java -cp "lib/mysql-connector-j-9.5.0.jar:src" Register.Login
```

#### Or run specific dashboards:
```bash
# Lecturer Dashboard
java -cp "lib/mysql-connector-j-9.5.0.jar:src" LectureDashboard

# Admin Dashboard
java -cp "lib/mysql-connector-j-9.5.0.jar:src" AdminDashboard

# Student Dashboard
java -cp "lib/mysql-connector-j-9.5.0.jar:src" Student.Dashboard
```

## Default Login Credentials

The database comes with sample users for testing:

- **Admin:**
  - Username: `admin`
  - Password: `admin123`

- **Lecturer:**
  - Username: `lecturer1`
  - Password: `password123`

- **Students:**
  - Username: `student1`, `student2`, `student3`
  - Password: `password123`

**IMPORTANT:** Change these passwords in production!

## Database Schema

The system uses a normalized database structure with the following main tables:

- `users` - Authentication (username, password, role)
- `students` - Student information
- `lecturers` - Lecturer information
- `courses` - Course details
- `enrollments` - Student-course relationships
- `grades` - Student grades and assignments
- `timetable` - Class schedules
- `lecture_slides` - Course materials
- `attendance` - Attendance tracking
- `payments` - Payment records

## VPS Deployment Notes

When deploying to a VPS:

1. Ensure MySQL is running and accessible
2. Update firewall rules to allow MySQL port (default: 3306)
3. Update `DBConfig.java` with your VPS IP address
4. Ensure the MySQL user has proper permissions:
   ```sql
   GRANT ALL PRIVILEGES ON student_management_system.* TO 'your_user'@'%';
   FLUSH PRIVILEGES;
   ```

## Troubleshooting

### Connection Failed
- Check if MySQL is running
- Verify credentials in `DBConfig.java`
- Ensure database exists: `SHOW DATABASES;`
- Check MySQL user permissions

### JDBC Driver Not Found
- Ensure `mysql-connector-j-9.5.0.jar` is in the `lib/` folder
- Include it in classpath when compiling/running

## Contributors

University Assessment Project - Team Members

## License

This is a university assessment project.
