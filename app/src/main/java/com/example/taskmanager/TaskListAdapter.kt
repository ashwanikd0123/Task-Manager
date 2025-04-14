package com.example.taskmanager

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.taskmanager.databinding.TaskListItemLayoutBinding

class TaskListAdapter(val model: TaskViewModel) : Adapter<TaskListAdapter.TaskViewHolder>() {

    var taskData: List<TaskData> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaskViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = TaskListItemLayoutBinding.inflate(layoutInflater, parent, false)
        return TaskViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TaskViewHolder, position: Int) {
        holder.taskTextView.text = taskData[position].task
        holder.taskStatusSwitch.isChecked = taskData[position].status?:false
        holder.deleteButton.setOnClickListener{
            model.deleteTask(taskData[position])
        }
        holder.taskStatusSwitch.setOnCheckedChangeListener ({button, checked ->
            model.setTaskStatus(taskData[position], checked)
        })
    }

    override fun getItemCount(): Int {
        return taskData.size
    }

    fun setTaskList(data: List<TaskData>) {
        taskData = data.toList()
        notifyDataSetChanged()
    }

    class TaskViewHolder(binding: TaskListItemLayoutBinding) : ViewHolder(binding.root) {
        val taskTextView = binding.taskTextview
        val taskStatusSwitch = binding.doneSwitch
        val deleteButton = binding.deleteButton
    }
}