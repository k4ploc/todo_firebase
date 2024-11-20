package com.example.todoappfirebase.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappfirebase.databinding.FragmentHomeBinding
import com.example.todoappfirebase.fragments.ToDoDialogFragment.DialogNextBtnClickListener
import com.example.todoappfirebase.ui.viewmodel.TodoViewModel
import com.example.todoappfirebase.utils.adapter.TaskAdapter
import com.example.todoappfirebase.utils.adapter.TaskAdapter.TodoAdapterClicksInterface
import com.example.todoappfirebase.utils.model.ToDoData
import com.example.todoappfirebase.utils.model.TodoUpdateRequest
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import org.koin.androidx.viewmodel.ext.android.viewModel


class HomeFragment : Fragment(), DialogNextBtnClickListener, TodoAdapterClicksInterface {

    private val todoViewModel: TodoViewModel by viewModel()
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private lateinit var navController: NavController
    private lateinit var binding: FragmentHomeBinding
    private var popUpFragment: ToDoDialogFragment? = null
    private lateinit var adapter: TaskAdapter
    private lateinit var mList: MutableList<ToDoData>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init(view)
        //getDataFromFirebase()
        //getDataFromServer()
        observeTodos()
        todoViewModel.fetchTodos()
        registerEvents()
    }

    private fun observeTodos() {
        mList.clear()
        todoViewModel.todos.observe(viewLifecycleOwner) { todos ->
            Log.i("OBSERVED_TODOS", todos.toString())
            mList.addAll(todos)
            adapter.notifyDataSetChanged()
        }

    }

    private fun getDataFromFirebase() {
        databaseRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                mList.clear()
                println("DATA: " + snapshot.children)
                for (taskSnaphot in snapshot.children) {
                    println("TODO: " + taskSnaphot.key)
                    val todoTask = taskSnaphot.key?.let {
                        val task: String = taskSnaphot.child("task").value.toString()
                        val completed: Boolean = taskSnaphot.child("completed").value as Boolean

                        //   ToDoData(it, task, completed)
                    }
                    if (todoTask != null) {
                        //  mList.add(todoTask)
                    }
                }
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(context, error.message, Toast.LENGTH_SHORT).show()

            }

        })
    }


    private fun init(view: View) {
        navController = Navigation.findNavController(view)
        auth = FirebaseAuth.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Task")
            .child(auth.currentUser?.uid.toString())
        binding.mainRecyclerView.setHasFixedSize(true)
        binding.mainRecyclerView.layoutManager = LinearLayoutManager(context)
        mList = mutableListOf()
        adapter = TaskAdapter(mList)
        adapter.setListener(this)
        binding.mainRecyclerView.adapter = adapter
    }

    private fun registerEvents() {

        binding.addTaskBtn.setOnClickListener {
            if (popUpFragment != null)
                childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()
            popUpFragment = ToDoDialogFragment()
            popUpFragment!!.setListener(this)
            popUpFragment!!.show(
                childFragmentManager,
                "AddTodoPopUpFragment"
            )
        }

    }

    override fun onSaveTast(todo: ToDoData, todoEt: TextInputEditText) {
        /*databaseRef.push().setValue(todo).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "Todo saved successfully", Toast.LENGTH_SHORT).show()
                todoEt.text = null

            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            popUpFragment!!.dismiss()
        }*/
        mList.clear()
        todoViewModel.addTodo(todo)
        Toast.makeText(context, "Todo saved successfully", Toast.LENGTH_SHORT).show()
        todoEt.text = null

        popUpFragment!!.dismiss()
    }

    override fun onUpdateTask(
        todoUpdateRequest: TodoUpdateRequest,
        todoEt: TextInputEditText
    ) {
        /*val map = HashMap<String, Any>()
        val taskUpdated = HashMap<String, Any>()
        taskUpdated.put("task", todoData.task)
        taskUpdated.put("completed", true);
        map[todoData.taskId.toString()] = taskUpdated

        //map[todoData.taskId.toString()] = todoData.task
        databaseRef.updateChildren(map).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(context, "task updated successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()
            }
            todoEt.text = null
            popUpFragment!!.dismiss()
        }*/
        mList.clear()

        todoViewModel.updateTodo(todoUpdateRequest)
        Toast.makeText(context, "Todo updated successfully", Toast.LENGTH_SHORT).show()

        todoEt.text = null
        popUpFragment!!.dismiss()
    }

    override fun onDeleteTaskBtnClicked(toDoData: ToDoData) {
        /* databaseRef.child(toDoData.taskId.toString()).removeValue().addOnCompleteListener {
             if (it.isSuccessful) {
                 Toast.makeText(context, "Deleted Successfully", Toast.LENGTH_SHORT).show()

             } else {
                 Toast.makeText(context, it.exception?.message, Toast.LENGTH_SHORT).show()

             }
         }*/
    }

    override fun onEditTaskBtnClicked(toDoData: ToDoData) {
        if (popUpFragment != null)
            childFragmentManager.beginTransaction().remove(popUpFragment!!).commit()

        popUpFragment = ToDoDialogFragment.newInstance(toDoData.id, toDoData.name)
        popUpFragment!!.setListener(this)
        popUpFragment!!.show(
            childFragmentManager,
            ToDoDialogFragment.TAG
        )
    }


}
