package com.example.sistemaedu.eventos;

import java.io.Serializable;
import java.time.LocalDateTime;

public class AssignmentEvent implements Serializable {
    private static final long serialVersionUID = 1L;
    private String courseName;
    private Long courseId;
    private String studentName;
    private Long studentId;
    private String professorName;
    private LocalDateTime assignmentDate;

    // Constructor
    public AssignmentEvent(String courseName, Long courseId, String studentName, Long studentId, String professorName, LocalDateTime assignmentDate) {

        this.courseName = courseName;
        this.courseId = courseId;
        this.studentName = studentName;
        this.studentId = studentId;
        this.professorName = professorName;
        this.assignmentDate = assignmentDate;
    }

    // Getters and setters
    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public LocalDateTime getAssignmentDate() {
        return assignmentDate;
    }

    public void setAssignmentDate(LocalDateTime assignmentDate) {
        this.assignmentDate = assignmentDate;
    }

    @Override
    public String toString() {
        return "AssignmentEvent{" +
                "courseName='" + courseName + '\'' +
                ", courseId=" + courseId +
                ", studentName='" + studentName + '\'' +
                ", studentId=" + studentId +
                ", professorName='" + professorName + '\'' +
                ", assignmentDate=" + assignmentDate +
                '}';
    }
}
