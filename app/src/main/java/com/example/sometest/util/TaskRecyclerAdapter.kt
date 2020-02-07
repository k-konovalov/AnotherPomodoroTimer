package com.example.sometest.util

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sometest.R

class TaskRecyclerAdapter : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    private val taskList = mutableListOf<TaskItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val someText: TextView = itemView.findViewById(R.id.txtTaskName)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.some_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]
        holder.someText.text = item.text
    }

    fun replaceTasks(inputData: List<TaskDTO>) {
        taskList.clear()
        for (elem in inputData) {
            taskList.add(TaskItem(elem.taskName))
        }

        notifyDataSetChanged()
    }
}