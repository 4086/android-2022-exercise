package com.example.roomex

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "student_table")
data class Student (
    @PrimaryKey val id: Int,
    val name: String
    )
