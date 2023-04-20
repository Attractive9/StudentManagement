package com.management.student.data


data class Note(val name: String, val message: String, val date: Int)

data class Student(val studentName: String)

data class StudentInfo(val studentId: String, val studentName: String, val studentSymptom: String, val date: String)
