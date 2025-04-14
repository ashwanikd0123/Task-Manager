package com.example.taskmanager

import android.content.Context
import androidx.room.Room

class TaskModel(context: Context) {
    val db = Room.databaseBuilder(context, TaskDataBase::class.java, "tasks").build()
    val taskDao = db.getTaskDao()

    fun getAllTasks() : List<TaskData> {
        return taskDao.getTaskList()
    }

    fun addTask(task: String) {
        taskDao.addTask(TaskData(null, task, false))
    }

    fun deleteTask(task: String) {
        taskDao.deleteTask(taskDao.findTask(task))
    }

    fun updateTaskStatus(task: TaskData, value: Boolean) {
        task.status = value
        taskDao.updateTaskStatus(task)
    }

    fun updateTaskStatus(task: String, value: Boolean) {
        val tasks: List<TaskData> = taskDao.findTask(task)
        for (t in tasks) {
            t.status = value
            taskDao.updateTaskStatus(t)
        }
    }
}