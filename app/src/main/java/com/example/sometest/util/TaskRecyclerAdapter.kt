package com.example.sometest.util

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.sometest.MainActivity
import com.example.sometest.R
import com.example.sometest.network.IssueDTO
import okio.blackholeSink

class TaskRecyclerAdapter : RecyclerView.Adapter<TaskRecyclerAdapter.ViewHolder>() {
    private val taskList = mutableListOf<TaskItem>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val someLayout:LinearLayout = itemView.findViewById(R.id.itemLayout)
        val someId: TextView = itemView.findViewById(R.id.txtId)
        val someKey:TextView = itemView.findViewById(R.id.txtKey)
        val someSummary: TextView = itemView.findViewById(R.id.txtTaskName)
    }

    override fun getItemCount(): Int = taskList.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.some_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = taskList[position]

        holder.someId.text = item.id.toString()
        holder.someKey.text = item.key
        holder.someSummary.text = item.summary

        holder.someLayout.setOnClickListener {
            Toast.makeText(MainActivity.context,"${holder.someSummary.text}",Toast.LENGTH_SHORT).show()
        }
    }

    fun replaceTasks(inputData: List<IssueDTO>) {
        taskList.clear()
        for (elem in inputData) {
            taskList.add(TaskItem(elem.id,elem.key,elem.fields.summary))
        }
        notifyDataSetChanged()
    }
}