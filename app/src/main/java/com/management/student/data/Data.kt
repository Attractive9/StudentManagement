package com.management.student.data


data class Note @JvmOverloads constructor(val name: String = "", val message: String = "", val date: String = "")

data class StudentInfo(val studentId: String, val studentName: String, val studentSymptom: String, val date: String): java.io.Serializable
