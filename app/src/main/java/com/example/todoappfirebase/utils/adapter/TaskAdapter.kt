package com.example.todoappfirebase.utils.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappfirebase.databinding.EachTodoItemBinding
import com.example.todoappfirebase.utils.model.ToDoData

class TaskAdapter(private val list: MutableList<ToDoData>) : RecyclerView.Adapter<TaskAdapter.ToDoViewHolder>() {
    private var listener: TodoAdapterClicksInterface? = null
    fun setListener(listener: TodoAdapterClicksInterface) {
        this.listener = listener
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ToDoViewHolder {
        val binding = EachTodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: ToDoViewHolder,
        position: Int
    ) {
        with(holder) {
            //  Log.i("ListSize:", list.size.toString())

            with(list[position]) {
                binding.todoTask.text = this.name
                binding.deleteTask.setOnClickListener {
                    listener?.onDeleteTaskBtnClicked(this)
                }

                binding.editTask.setOnClickListener {
                    listener?.onEditTaskBtnClicked(this)
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    inner class ToDoViewHolder(val binding: EachTodoItemBinding) : RecyclerView.ViewHolder(binding.root) {

    }

    interface TodoAdapterClicksInterface {
        fun onDeleteTaskBtnClicked(toDoData: ToDoData)
        fun onEditTaskBtnClicked(toDoData: ToDoData)
    }
}
