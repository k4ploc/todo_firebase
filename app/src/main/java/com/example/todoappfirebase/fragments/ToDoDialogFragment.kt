package com.example.todoappfirebase.fragments

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RadioButton
import android.widget.Toast
import androidx.fragment.app.DialogFragment
import com.example.todoappfirebase.databinding.FragmentAddTodoPopupBinding
import com.example.todoappfirebase.utils.model.PriorityTodo
import com.example.todoappfirebase.utils.model.ToDoData
import com.example.todoappfirebase.utils.model.TodoUpdateRequest
import com.example.todoappfirebase.utils.model.getPriority
import com.google.android.material.textfield.TextInputEditText

class ToDoDialogFragment : DialogFragment() {

    private lateinit var binding: FragmentAddTodoPopupBinding
    private lateinit var listener: DialogNextBtnClickListener
    private var toDoUpdateRequest: TodoUpdateRequest? = null
    private var prioridadSeleccionada: PriorityTodo? = null

    fun setListener(listener: DialogNextBtnClickListener) {
        this.listener = listener
    }

    companion object {
        const val TAG = "AddTodoPopUpFragment"

        @JvmStatic
        fun newInstance(id: Int, name: String, quantity: String, priority: String) = ToDoDialogFragment().apply {
            Log.i(TAG, id.toString() + " " + name)
            arguments = Bundle().apply {
                putInt("id", id)
                putString("name", name)
                putString("quantity", quantity)
                putString("priority", priority)
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
            Log.e(TAG, arguments?.getString("priority").toString())
            toDoUpdateRequest = TodoUpdateRequest(
                arguments?.getInt("id")!!,
                arguments?.getString("name").toString(),
                arguments?.getString("quantity").toString(),
                getPriority(arguments?.getString("priority").toString())!!
            )
            Log.i(TAG, toDoUpdateRequest.toString())
            binding.todoEt.setText(toDoUpdateRequest?.name)
            binding.todoQuantityEt.setText(toDoUpdateRequest?.quantity)

            val selectedValue = getPriority(
                arguments?.getString("priority").toString()
            )!! // Selecciona el RadioButton correspondiente al valor
            when (selectedValue) {
                PriorityTodo.LOW -> binding.radioGroupPrioridad.check(binding.radioBaja.id)
                PriorityTodo.NORMAL -> binding.radioGroupPrioridad.check(binding.radioNormal.id)
                PriorityTodo.HIGH -> binding.radioGroupPrioridad.check(binding.radioAlta.id)
            }
        }
        registerEvents()
    }

    private fun registerEvents() {
        binding.radioGroupPrioridad.setOnCheckedChangeListener { group, checkedId ->
            val radioButton = group.findViewById<RadioButton>(checkedId)
            // Maneja el evento de selección aquí
            prioridadSeleccionada = getPriority(radioButton.text.toString())
            Log.e("ERROR ", prioridadSeleccionada?.name.toString())

        }
        binding.todoNextBtn.setOnClickListener {
            val todoTaskValue = binding.todoEt.text.toString().trim()
            // También puedes agregar un listener si necesitas manejar los cambios en la selección

            if (todoTaskValue.isNotEmpty()) {

                if (toDoUpdateRequest == null) {
                    try {
                        val todoTask = ToDoData(
                            id = 0,
                            name = todoTaskValue,
                            quantity = "1",
                            priority = prioridadSeleccionada!!,
                            status = "ACTIVE",
                            date = System.currentTimeMillis().toString()
                        )
                        listener.onSaveTast(todoTask, binding.todoEt)
                    } catch (e: Exception) {
                        Log.e("ERROR ", e.toString())
                    }
                } else {
                    toDoUpdateRequest?.name = todoTaskValue
                    toDoUpdateRequest?.priority = prioridadSeleccionada!!
                    listener.onUpdateTask(toDoUpdateRequest!!, binding.todoEt)
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
        fun onUpdateTask(todoData: TodoUpdateRequest, todoEt: TextInputEditText)
    }


}
