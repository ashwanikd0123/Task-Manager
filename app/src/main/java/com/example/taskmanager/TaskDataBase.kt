package com.example.taskmanager

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [TaskData::class], version = 1)
abstract class TaskDataBase : RoomDatabase() {
    abstract fun getTaskDao() : TaskDao
}