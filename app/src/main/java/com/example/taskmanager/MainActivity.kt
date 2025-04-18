package com.example.taskmanager

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.taskmanager.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    val viewModel: TaskViewModel by viewModels { TaskViewModel.Factory }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val binding = DataBindingUtil.setContentView<ActivityMainBinding>(this, R.layout.activity_main)

        val taskModel = TaskModel(this)
        viewModel.setTaskModel(taskModel)
        val adapter = TaskListAdapter()
        adapter.setCallBack(viewModel)

        binding.recView.adapter = adapter
        binding.recView.layoutManager = LinearLayoutManager(this)

        binding.addTaskButton.setOnClickListener{
            val text = binding.taskTextEdittext.text.toString()
            if (text.isNotEmpty()) {
                viewModel.addTask(text) {
                    viewModel.getTaskList {
                        Handler(Looper.getMainLooper()).post {
                            adapter.setTaskList(viewModel.taskList.value!!)
                            adapter.notifyItemInserted(adapter.itemCount - 1)
                        }
                    }
                }
            }
            binding.taskTextEdittext.setText("")
        }

        viewModel.getTaskList({
            runOnUiThread {
                adapter.setTaskList(viewModel.taskList.value!!)
                adapter.notifyDataSetChanged()
            }
        })

        val itemTouchCallback = ItemTouchHelperCallBack(adapter)
        val itemTouchHelper = ItemTouchHelper(itemTouchCallback)
        itemTouchHelper.attachToRecyclerView(binding.recView)
    }
}