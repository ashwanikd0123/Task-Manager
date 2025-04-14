package com.example.taskmanager

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface TaskDao {

    @Query("SELECT * FROM taskdata WHERE task = :task")
    fun findTask(task: String): List<TaskData>

    @Query("SELECT * FROM taskdata")
    fun getTaskList() : List<TaskData>

    @Insert()
    fun addTask(task: TaskData)

    @Delete
    fun deleteTask(task: TaskData)

    @Delete
    fun deleteTask(task: List<TaskData>)

    @Update
    fun updateTaskStatus(task: TaskData)
}