package com.example.opsc7213_goalignite

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ListView
import androidx.fragment.app.Fragment
import com.google.android.material.floatingactionbutton.FloatingActionButton

class FragmentGrades : Fragment() {

    private lateinit var listView: ListView
    private lateinit var addButton: FloatingActionButton
    private lateinit var editText: EditText
    private lateinit var addGradeDialog: android.widget.LinearLayout
    private lateinit var marksEditText: EditText
    private lateinit var adapter: ArrayAdapter<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_grades, container, false)

        listView = view.findViewById(R.id.listView)
        addButton = view.findViewById(R.id.addGradeFab)
        editText = view.findViewById(R.id.editText)
        addGradeDialog = view.findViewById(R.id.addGradeDialog)
        marksEditText = view.findViewById(R.id.marksEditText)

        adapter = ArrayAdapter(requireContext(), android.R.layout.simple_list_item_1)
        listView.adapter = adapter

        addButton.setOnClickListener {
            addGradeDialog.visibility = View.VISIBLE
        }

        marksEditText.setOnEditorActionListener { textView, i, _ ->
            if (i == EditorInfo.IME_ACTION_DONE) {
                val text = textView.text.toString()
                if (text.isNotEmpty()) {
                    adapter.add(text)
                    addGradeDialog.visibility = View.GONE
                }
                true
            } else {
                false
            }
        }

        return view
        }
}
