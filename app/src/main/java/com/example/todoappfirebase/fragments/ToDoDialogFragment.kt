package com.example.todoappfirebase.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todoappfirebase.databinding.FragmentAddTodoPopupBinding
import com.example.todoappfirebase.utils.model.ToDoData
import com.google.android.material.textfield.TextInputEditText

class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoData: ToDoData? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopUpFragment"

        @JvmStatic
        fun newInstance(taskId: String, task: String) = ToDoDialogFragment().apply {
            arguments = Bundle().apply {
                putString("taskId", taskId)
                putString("task", task)
            }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAddTodoPopupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        if (arguments != null) {
            /*toDoData = ToDoData(
                arguments?.getString("taskId").toString(),
                arguments?.getString("task").toString(),
                false
            )
            binding.todoEt.setText(toDoData?.task)*/
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.todoNextBtn.setOnClickListener {
            val todoTaskValue = binding.todoEt.text.toString().trim()
            if (todoTaskValue.isNotEmpty()) {

                if (toDoData == null) {
                    val todoTask = ToDoData(
                        id = 0,
                        name = todoTaskValue,
                        quantity = "1",
                        priority = "NORMAL",
                        status = "ACTIVE",
                        date = System.currentTimeMillis().toString()
                    )
                    listener.onSaveTast(todoTask, binding.todoEt)
                } else {
                    //  toDoData?.task = todoTaskValue
                    //  listener.onUpdateTask(toDoData!!, binding.todoEt)
                }
            } else {
                Toast.makeText(context, "Please type some task", Toast.LENGTH_SHORT).show()
            }
        }

        binding.todoClose.setOnClickListener {
            dismiss()
        }
    }

    interface DialogNextBtnClickListener {
        fun onSaveTast(todo: ToDoData, todoEt: TextInputEditText)
        fun onUpdateTask(todoData: ToDoData, todoEt: TextInputEditText)
    }


}
