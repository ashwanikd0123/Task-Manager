package com.example.taskmanager

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.createSavedStateHandle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskViewModel() : ViewModel(), TaskListAdapter.ChangeCallBack {
    val taskList = MutableLiveData<List<TaskData>>(listOf())

    lateinit var model: TaskModel

    fun setTaskModel(model: TaskModel) {
        this.model = model
    }

    fun getTaskList(callBack: TaskAddCallBack){
        viewModelScope.launch(Dispatchers.Default) {
            taskList.postValue(model.getAllTasks())
            callBack.notifyCallers()
        }
    }

    fun deleteTask(data: TaskData) {
        viewModelScope.launch(Dispatchers.Default) {
            model.deleteTask(data.task!!)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun setTaskStatus(task:TaskData, status: Boolean) {
        viewModelScope.launch(Dispatchers.Default) {
            model.updateTaskStatus(task, status)
            taskList.postValue(model.getAllTasks())
        }
    }

    fun addTask(task : String, callBack: TaskAddCallBack) {
        viewModelScope.launch(Dispatchers.Default) {
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

    companion object {

        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(
                modelClass: Class<T>,
                extras: CreationExtras
            ): T {
                return TaskViewModel() as T
            }
        }
    }
}