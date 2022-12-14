package com.example.roomex

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface MyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertStudent(student: Student)

    @Delete
    suspend fun deleteStudent(student: Student)

    @Query("SELECT * from student_table")
    fun getAllStudents(): LiveData<List<Student>>
}
