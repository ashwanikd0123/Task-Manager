package com.example.taskmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.TaskListItemLayoutBinding

class TaskListAdapter : Adapter<TaskListAdapter.TaskViewHolder>() {

    var taskData: MutableList<TaskData> = mutableListOf()
    var changeCallBack: ChangeCallBack? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskTextView.text = taskData[position].task
        holder.taskStatusSwitch.isChecked = taskData[position].status?:false

        holder.deleteButton.setOnClickListener{
            val layoutPos = holder.layoutPosition
            removeItem(layoutPos)
        }

        holder.taskStatusSwitch.setOnCheckedChangeListener ({button, checked ->
            val layoutPos = holder.layoutPosition
            taskData[layoutPos].status = checked
            changeCallBack?.taskStatesChanged(taskData[layoutPos])
        })
    }

    fun removeItem(itemIdx : Int) {
        val data = taskData.removeAt(itemIdx)
        notifyItemRemoved(itemIdx)
        changeCallBack?.taskDeleted(data)
    }

    override fun getItemCount(): Int {
        return taskData.size
    }

    fun setTaskList(data: List<TaskData>) {
        taskData = data.toMutableList()
    }

    fun setCallBack(callBack: ChangeCallBack) {
        this.changeCallBack = callBack
    }

    class TaskViewHolder(binding: TaskListItemLayoutBinding) : ViewHolder(binding.root) {
        val taskTextView = binding.taskTextview
        val taskStatusSwitch = binding.doneSwitch
        val deleteButton = binding.deleteButton
    }

    interface ChangeCallBack {
        fun taskDeleted(data: TaskData)
        fun taskStatesChanged(data: TaskData)
    }
}