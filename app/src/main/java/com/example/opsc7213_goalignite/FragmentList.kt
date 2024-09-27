package com.example.opsc7213_goalignite

import android.content.DialogInterface
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7213_goalignite.adapter.ToDoAdapter
import com.example.opsc7213_goalignite.model.ToDoModel
import com.example.opsc7213_goalignite.utilis.DatabaseHandler
import com.google.android.material.floatingactionbutton.FloatingActionButton

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentList.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentList : Fragment(), DialogCloseListener {
    private var param1: String? = null
    private var param2: String? = null

    private lateinit var tasksRecyclerView: RecyclerView
    private lateinit var tasksAdapter: ToDoAdapter
    private lateinit var fab: FloatingActionButton
    private var taskList: MutableList<ToDoModel> = mutableListOf()
    private lateinit var db: DatabaseHandler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        db = DatabaseHandler(requireContext()) // Use requireContext() here
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        // Initialize the RecyclerView
        tasksRecyclerView = view.findViewById(R.id.tasksRecyclerView)
        tasksRecyclerView.layoutManager = LinearLayoutManager(requireContext()) // Use requireContext() here
        tasksAdapter = ToDoAdapter(db, requireActivity()) // Use requireActivity() here
        tasksRecyclerView.adapter = tasksAdapter

        fab = view.findViewById(R.id.fab) // Use view.findViewById here

        val itemTouchHelper = ItemTouchHelper(RecyclerItemTouchHelper(requireContext(), tasksAdapter))
        itemTouchHelper.attachToRecyclerView(tasksRecyclerView)

        // Fetch tasks from the database and update the adapter
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)

        // Handle FloatingActionButton click
        fab.setOnClickListener {
            AddNewTask().show(parentFragmentManager, AddNewTask.TAG) // Use parentFragmentManager
        }

        return view
    }

    override fun handleDialogClose(dialog: DialogInterface) {
        taskList = db.getAllTasks().toMutableList()
        taskList.reverse()
        tasksAdapter.setTasks(taskList)
        tasksAdapter.notifyDataSetChanged()
    }


// use requireContext() instead of 'this' for fragment

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentList().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}