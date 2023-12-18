package com.example.todoappandroied.database.daos

import androidx.room.*
import com.example.todoappandroied.database.models.Task

@Dao
interface TasksDao {
    @Insert
    fun insertTask(task:Task)

    @Delete
    fun deleteTask(task:Task)

    @Update
    fun updateTask(task:Task)

    @Query("select * from tasks")
    fun getAllTask(): List<Task>

    @Query("select * from tasks where date =:selectedDate")
    fun getTasksByDate(selectedDate:Long): List<Task>
}