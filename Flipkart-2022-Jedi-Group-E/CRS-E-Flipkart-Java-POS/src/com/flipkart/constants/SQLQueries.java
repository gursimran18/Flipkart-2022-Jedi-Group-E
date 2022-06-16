/**
 * 
 */
package com.crs.flipkart.constants;


public class SQLQueries {
	
	public static final String GET_USER_EMAIL_PASSWORD = "SELECT * FROM user JOIN role on user.userId=role.userId WHERE email = ? AND password = ?";
	
	public static final String VERIFY_EMAIL= "SELECT * FROM user where email = ?";
	
	public static final String ALL_USER_ID= "SELECT userId FROM user";
	
	public static final String ADD_USER_QUERY = "insert into user(userId,email,password) values (?,?, ?)";
	
	public static final String ADD_USER_ROLE = "insert into role(userId,role) values (?,?)";
	
	public static final String ADD_STUDENT = "insert into student(studentId,name,contactNumber,branch,batch,verificationStatus,feeStatus) values (?, ?, ?, ?, ?, ?, ?)";
	
	public static final String UPDATE_PASSWORD = "UPDATE user SET password = ? WHERE email = ? and password = ?";
	
	public static final String FETCH_COURSES = "select * from course";
	
	public static final String ADD_COURSE = "insert into course(courseId, courseName, courseFee, department, professorId, studentCount) values (?, ?, ?, ?,null,0)";


	public static final String DROP_COURSE = "delete from Course where courseId = ?";
	
	public static final String GET_PENDING_STUDENTS = "SELECT name,studentId from student where student.verificationStatus=0";
	
	public static final String APPROVE_ADDMISSION_REQUEST = "UPDATE student SET verificationStatus = 1 where studentId = ?";
	
	public static final String INSERT_REGISTRATION = "INSERT INTO semesterregistration values (?,?,0)";
	
	public static final String ADD_PROFESSOR = "insert into professor values (?,?,?,?,?,?)";
	
	public static final String LIST_PROFESSORS = "SELECT professorId, name, department FROM professor";
	
	public static final String REMOVE_USER = "delete from user where userId = ?";
	
	public static final String DROP_PROFESSOR = "delete from professor where professorId=?";
	
	public static final String GET_PENDING_GRADE_STUDENTS= "SELECT * FROM student where studentId not in (SELECT studentId from gradecard)";
	
	public static final String GET_GRADES = "SELECT * from grade WHERE studentId = ? AND semester = ?";
	
	public static final String GENERATE_GRADES = "insert into gradeCard(studentId, semester, gpa) values (?,?,?)";
	
	public static final String CHECK_VACANT_COURSE = "SELECT professorId from course WHERE courseId = ?";
	
	public static final String SELECT_COURSE_FOR_PROF = "UPDATE course SET professorId = ? WHERE courseId = ?";
	
	public static final String VIEW_ENROLLED_STUDENTS = "SELECT student.studentId, student.name from student INNER JOIN optedCourses ON student.studentId = optedCourses.studentId WHERE optedCourses.isAlloted = 1 AND optedCourses.courseId = ?"; 
	
	public static final String VIEW_UNGRADED_STUDENTS = "SELECT student.studentId as studentId, name from optedCourses JOIN student on student.studentId = optedCourses.studentId WHERE optedCourses.isAlloted = 1 AND optedCourses.courseId = ? AND optedCourses.studentId not in (SELECT studentId from grade where courseId = ?)"; 
	
	public static final String ADD_GRADE = "INSERT INTO grade(studentId, courseId, gpa, semester) values(?, ?, ?, ?)";
	
	public static final String GET_STUDENT= "SELECT * FROM student where studentId= ?";
	
	public static final String GET_PROFESSOR= "SELECT * FROM professor where professorId= ?";
	
	public static final String SUBMITTED_COURSES= "SELECT * FROM semesterregistration where studentId= ?";
	
	public static final String FETCH_OPTED_COURSES = "Select course.courseId as courseId,courseName,courseFee,department,professorId,studentCount from course join optedcourses where course.courseId = optedcourses.courseId and studentId = ? order by optedcourses.optedNumber";
	
	public static final String ADD_OPTED_COURSE = "insert into optedcourses (studentId,isAlloted,courseId) values ( ?, 0, ?)";
	
	public static final String DROP_OPTED_COURSE = "delete from optedcourses where studentId=? AND courseId = ?";
	
	public static final String SUBMIT_OPTED_COURSES = "UPDATE optedcourses SET isAlloted = 1 WHERE studentId = ?";
	
	public static final String INCREMENT_STUDENT_COUNT = "UPDATE course SET studentCount=? where courseId=?";
	
	public static final String UPDATE_REGISTRATION_STATUS = "UPDATE semesterregistration set registrationStatus=1 where studentId=?";
	
	public static final String ADD_PAYMENT = "insert into payment(studentId,referenceId,amount,paymentMode) values (?, ?, ?, ?)";
	
	public static final String UPDATE_PAYMENT_STATUS = "UPDATE student set feeStatus=1 where studentId=?";
	
	public static final String ADD_NOTIFICATION = "insert into notification values (?, ?, ?)";
	
	public static final String FETCH_GRADECARD = "SELECT * FROM gradecard WHERE studentId = ? ";
	
	public static final String FETCH_GRADE= "SELECT * FROM grade WHERE studentId = ? ";
	
	public static final String FETCH_NOTIFICATIONS = "select * from notification where studentId = ?";
	
	
	

}
