package com.example.opsc7213_goalignite.adapter



import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7213_goalignite.R
import com.example.opsc7213_goalignite.utilis.DatabaseHandler
import com.example.opsc7213_goalignite.model.ToDoModel
import com.example.opsc7213_goalignite.AddNewTask

class ToDoAdapter(

    private val db: DatabaseHandler,
    private val activity: FragmentActivity // Use FragmentActivity for supportFragmentManager
) : RecyclerView.Adapter<ToDoAdapter.ViewHolder>() {

    private var todoList: List<ToDoModel> = emptyList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.document_layout, parent, false)
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val todoItem = todoList[position]
        holder.bind(todoItem)

        // Set the CheckBox text to the task description
        holder.taskCheckBox.text = todoItem.task

        holder.taskCheckBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                db.updateStatus(todoItem.id, 1)
            } else {
                db.updateStatus(todoItem.id, 0)
            }
        }
    }

    fun setTasks(todoList: List<ToDoModel>) {
        this.todoList = todoList
        notifyDataSetChanged()
    }

    fun deleteItem(position: Int) {
        val item = todoList[position]
        db.deleteTask(item.id)
        (todoList as MutableList).removeAt(position)
        notifyItemRemoved(position)
    }


    fun editItem(position: Int) {
        val item = todoList[position]
        val bundle = Bundle().apply {
            putInt("id", item.id)
            putString("task", item.task)
        }
        val fragment = AddNewTask().apply {
            arguments = bundle
        }
        fragment.show(activity.supportFragmentManager, AddNewTask.TAG)
    }

    override fun getItemCount(): Int = todoList.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val taskCheckBox: CheckBox = itemView.findViewById(R.id.todoCheckbox)

        fun bind(todo: ToDoModel) {
            taskCheckBox.isChecked = toBoolean(todo.status)
        }

        private fun toBoolean(n: Int): Boolean = n != 0
    }
}