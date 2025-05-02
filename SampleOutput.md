==========================================
UNIVERSITY MANAGEMENT SYSTEM - COMPLETE OUTPUT
==========================================

=== MAIN MENU ===
====== UNIVERSITY MANAGEMENT SYSTEM ======
1. Student Login
2. Faculty Login
3. Admin Login
4. Exit
Enter your choice: 1

=== STUDENT MODULE ===

[Login]
Enter Roll Number: 452020
Enter Password: ********
Login successful!

[Dashboard]
========== Student Dashboard ==========
1. View Profile
2. Academic Details
3. Update Contact Info
4. Change Password
5. Logout
Enter your choice: 1

[Profile View]
--- Student Profile ---
Roll Number: 452020
Name: Rohan Sharma
Branch: Computer Science (CS)
Semester: 4
Email: rohan.s@univ.edu
Contact: 9876543210
Gender: Male

[Academic Menu]
--- Academic Details ---
1. View Attendance
2. View Marks
3. View Result
4. Back
Enter choice: 1

[Attendance]
Attendance for CS401 (Java Programming)
Total Classes: 30 | Attended: 28 | Percentage: 93.33%

[Marks]
Marks for Semester 4:
CS401 (Java Programming): 85/100
CS402 (DBMS): 78/100

[Update Contact]
Enter new phone: 9876543222
Contact updated successfully!

=== FACULTY MODULE ===

[Login]
Enter Employee ID: FAC1001
Enter Password: ********
Login successful!

[Dashboard]
========== Faculty Dashboard ==========
1. View Profile
2. View Subjects
3. View Students
4. Upload Attendance
5. View Attendance
6. Upload Marks
7. View Marks
8. Top 10 Toppers
9. Update Profile
0. Logout
Enter choice: 2

[Assigned Subjects]
1. CS401 - Java Programming
2. CS402 - Database Systems

[Upload Attendance]
Select subject (1-2): 1
Enter date (YYYY-MM-DD): 2023-11-20
Enter attendance for each student:
452020 (Rohan Sharma): P
452021 (Priya Patel): A
Attendance recorded!

[Upload Marks]
Select subject: 1
Enter marks:
452020: 85
452021: 92
Marks uploaded!

[View Toppers]
Top 3 in CS401:
1. 452021 - Priya Patel - 92%
2. 452020 - Rohan Sharma - 85%

=== ADMIN MODULE ===

[Login]
Enter admin username: admin
Enter password: *********
Access granted!

[Dashboard]
======= Admin Dashboard =======
1. Student Management
2. Faculty Management
3. Course Management
4. Subject Management
5. Attendance
6. Logout
Enter choice: 1

[Student Management]
====== Student Management ======
1. Add Student
2. Update Student
3. View Students
4. Back
Enter choice: 1

[Add Student]
Enter Roll: 452025
Name: Amit Singh
Branch: CS
Semester: 3
Email: amit.s@univ.edu
Phone: 9876543255
Student added!

[Faculty Management]
1. Add Faculty
2. View Faculty
3. Update Faculty
4. Delete Faculty
Enter choice: 1

[Add Faculty]
Employee ID: FAC1005
Name: Dr. Neha Verma
Branch: CS
Email: neha.v@univ.edu
Faculty created!

[Course Management]
1. Add Course
2. View Courses
Enter choice: 1

[Add Course]
Course Name: M.Tech CS
Duration: 2 years
Course added!

[Subject Management]
1. Add Subject
2. Assign to Faculty
Enter choice: 1

[Add Subject]
Subject Code: CS505
Name: Machine Learning
Assigned to: FAC1001
Subject registered!

[Attendance Management]
1. View Attendance Reports
2. Generate Summary
Enter choice: 1

[Attendance Report]
CS401 Attendance Summary:
Total Students: 45
Average Attendance: 89%

=== ERROR SCENARIOS ===

[Invalid Login]
Enter Roll: 999999
Enter Password: wrongpass
Error: Invalid credentials!

[Validation Error]
Enter phone: 12345
Error: Phone must be 10 digits!

=== LOGOUT FLOWS ===

[Student Logout]
Logging out... Returning to main menu.

[Faculty Logout]
Session ended for FAC1001.

[Admin Logout]
Admin session terminated.
