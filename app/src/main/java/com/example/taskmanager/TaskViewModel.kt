package com.example.taskmanager

import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel(val model: TaskModel) : TaskListAdapter.ChangeCallBack {
    val taskList = MutableLiveData<List<TaskData>>(listOf())

    fun getTaskList(callBack: TaskAddCallBack){
        CoroutineScope(Dispatchers.Default).launch {
            taskList.postValue(model.getAllTasks())
            callBack.notifyCallers()
        }
    }

    fun deleteTask(data: TaskData) {
        CoroutineScope(Dispatchers.Default).launch {
            model.deleteTask(data.task!!)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun setTaskStatus(task:TaskData, status: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            model.updateTaskStatus(task, status)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun addTask(task : String, callBack: TaskAddCallBack) {
        CoroutineScope(Dispatchers.Default).launch {
            model.addTask(task)
            taskList.postValue(model.getAllTasks())
            callBack.notifyCallers()
        }
    }

    override fun taskDeleted(data: TaskData) {
        deleteTask(data)
    }

    override fun taskStatesChanged(data: TaskData) {
        setTaskStatus(data, data.status!!)
    }

    fun interface TaskAddCallBack {
        fun notifyCallers()
    }
}