package com.example.taskmanager

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(val model: TaskModel) {
    val taskList = MutableLiveData<List<TaskData>>()

    init {
        CoroutineScope(Dispatchers.Default).launch {
            taskList.postValue(model.getAllTasks())
        }
    }

    fun getTaskList() : List<TaskData> {
        return taskList.value!!.toList()
    }

    fun deleteTask(data: TaskData) {
        CoroutineScope(Dispatchers.Default).launch {
            model.deleteTask(data.task!!)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun setTaskStatus(task:String, status: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            model.updateTaskStatus(task, status)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun setTaskStatus(task:TaskData, status: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            model.updateTaskStatus(task, status)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun addTask(task : String) {
        CoroutineScope(Dispatchers.Default).launch {
            model.addTask(task)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun getTaskAt(idx: Int) : TaskData {
        return taskList.value!![idx]
    }
}