# Student Management System - Quick Start Guide

## Prerequisites

1. **Java JDK 17 or higher**
   - Download: https://www.oracle.com/java/technologies/downloads/
   - Verify installation: `java -version`

2. **MySQL Database**
   - The system connects to a remote MySQL database
   - Connection details are pre-configured in `src/DBConfig.java`

3. **MySQL Connector**
   - Already included in `lib/mysql-connector-j-9.5.0.jar`

## Quick Start (Windows)

### Step 1: Open PowerShell/Terminal
Navigate to the project folder:
```powershell
cd "d:\git repo\nethmi-new"
```

### Step 2: Setup Package Structure (First time only)

The Student module uses Java packages. Run these commands once to create the proper structure:

**PowerShell (Windows):**
```powershell
cd src
mkdir -p sms\model, sms\ui, sms\db
copy Student\Student.java sms\model\
copy Student\Dashboard.java sms\ui\
copy Student\DB.java sms\db\
copy Student\DBConnection.java sms\db\
```

**Git Bash / Linux / Mac:**
```bash
cd src
mkdir -p sms/model sms/ui sms/db
cp Student/Student.java sms/model/
cp Student/Dashboard.java sms/ui/
cp Student/DB.java sms/db/
cp Student/DBConnection.java sms/db/
```

### Step 3: Compile the Project

**PowerShell / Command Prompt (Windows):**
```powershell
javac -cp ".;..\lib\mysql-connector-j-9.5.0.jar" DBConfig.java sms\model\Student.java sms\db\DB.java sms\db\DBConnection.java sms\ui\Dashboard.java Register\Login.java Register\Register.java Register\DBUtil.java Admin\AdminDashboard.java LectureDashboard.java
```

**Git Bash / Linux / Mac:**
```bash
javac -cp ".:../lib/mysql-connector-j-9.5.0.jar" DBConfig.java sms/model/Student.java sms/db/DB.java sms/db/DBConnection.java sms/ui/Dashboard.java Register/*.java Admin/*.java LectureDashboard.java
```

### Step 4: Run the Application

**PowerShell / Command Prompt (Windows):**
```powershell
java -cp ".;Register;Admin;..\lib\mysql-connector-j-9.5.0.jar" Login
```

**Git Bash / Linux / Mac:**
```bash
java -cp ".:Register:Admin:../lib/mysql-connector-j-9.5.0.jar" Login
```

## Test Credentials

| Role     | Email                      | Password    |
|----------|----------------------------|-------------|
| Student  | student1@university.edu    | password123 |
| Lecturer | lecturer1@university.edu   | password123 |
| Admin    | admin@university.edu       | admin123    |

## Features

### Student Dashboard
- View enrolled courses
- Check attendance
- View grades and GPA
- Payment history
- Profile management

### Lecturer Dashboard
- Manage lecture slides
- View student averages
- Timetable management
- Student search

### Admin Dashboard
- Manage students
- Manage teachers
- System administration

## Project Structure

```
nethmi-new/
├── lib/
│   └── mysql-connector-j-9.5.0.jar
├── src/
│   ├── Register/           # Login & Registration
│   │   ├── Login.java      # Main entry point
│   │   ├── Register.java
│   │   └── DBUtil.java
│   ├── Student/            # Student module
│   │   ├── Dashboard.java
│   │   ├── Student.java
│   │   ├── DB.java
│   │   └── DBConnection.java
│   ├── Admin/              # Admin module
│   │   └── AdminDashboard.java
│   ├── LectureDashboard.java
│   └── DBConfig.java       # Database configuration
└── START.md
```

## Database Configuration

To connect to a different database, edit `src/DBConfig.java`:

```java
private static final String DB_URL = "jdbc:mysql://YOUR_HOST:3306/YOUR_DATABASE";
private static final String DB_USER = "YOUR_USERNAME";
private static final String DB_PASSWORD = "YOUR_PASSWORD";
```

## Troubleshooting

### "Class not found" error
Make sure you're in the `src` folder and using the correct classpath with the MySQL connector.

### "Cannot connect to database" error
1. Check your internet connection
2. Verify database credentials in `DBConfig.java`
3. Ensure the MySQL server is running

### "Invalid Login" message
Use the test credentials provided above, or register a new account.
