package com.example.opsc7213_goalignite

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7213_goalignite.adapter.ContactAdapter
import com.example.opsc7213_goalignite.model.ContactModel
import com.google.android.material.floatingactionbutton.FloatingActionButton



// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentContact.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentContact : Fragment() {

    private var param1: String? = null
    private var param2: String? = null

    private lateinit var fab2: FloatingActionButton
    private lateinit var contactRv: RecyclerView

    private lateinit var dbHelper: DbHelper
    private lateinit var contactAdapter: ContactAdapter

    private lateinit var deleteCon: ImageView // Delete button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_contact, container, false)

        // Initialize DbHelper
        dbHelper = DbHelper(requireContext()) // Fix: No 'new' keyword in Kotlin

        // Initialize RecyclerView
        contactRv = view.findViewById(R.id.contactRv)
        contactRv.setHasFixedSize(true)


        // Load contact data
        loadData()

        // Initialize FloatingActionButton
        fab2 = view.findViewById(R.id.fab2)

        // Set an OnClickListener for the FloatingActionButton
        fab2.setOnClickListener {
            // Start the AddEditContact Activity when the button is clicked
            val intent = Intent(activity, AddEditContact::class.java)
            startActivity(intent)
        }
        // Initialize deleteCon (your delete button) and set click listener
        deleteCon = view.findViewById(R.id.deleteCon)
        deleteCon.setOnClickListener {
            AlertDialog.Builder(requireContext()).apply {
                setTitle("Delete All Contacts")
                setMessage("Are you sure you want to delete all contacts?")
                setPositiveButton("Yes") { _, _ ->
                    // Call deleteAllContacts method from DbHelper
                    dbHelper.deleteAllContacts()
                    // Refresh the RecyclerView or contact list
                    loadData() // This will reload the data from the database
                }
                setNegativeButton("No", null)
            }.show()
        }


        return view
    }

    private fun loadData() {
        // Fetch the contacts from the database
        val contactList: ArrayList<ContactModel> = dbHelper.getAllData()

        // Initialize the adapter with the fetched data
        contactAdapter = ContactAdapter(requireContext(), contactList)
        contactRv.adapter = contactAdapter
    }
    override fun onResume() {
        super.onResume()
        loadData()  // Call loadData to refresh contact list when fragment resumes
    }
    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment FragmentContact.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            FragmentContact().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}

//we show our SQLite data in RecyclerView
//for recycler view we need a item layout
//add recyclerview in fragment contact
//create a model class for data
//create a adapter class to show data in recyclerView
//create activity for detail of contact
