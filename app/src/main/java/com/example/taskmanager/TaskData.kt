package com.example.taskmanager

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TaskData (
    @PrimaryKey(autoGenerate = true)
    val id: Int?,
    @ColumnInfo(name = "task")
    val task: String?,
    @ColumnInfo(name = "status", defaultValue = false.toString())
    var status: Boolean?
)