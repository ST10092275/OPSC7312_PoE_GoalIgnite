package com.example.opsc7213_goalignite.adapter


import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.opsc7213_goalignite.ContactDetails
import com.example.opsc7213_goalignite.R
import com.example.opsc7213_goalignite.model.ContactModel

class ContactAdapter(
    private val context: Context,
    private val contactList: ArrayList<ContactModel>,
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): ContactViewHolder {
        // Inflate the custom row layout
        val view = LayoutInflater.from(context).inflate(R.layout.row_contact_item, parent, false)
        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(@NonNull holder: ContactViewHolder, position: Int) {
        // Get the current contact model
        val contactModel = contactList[position]

        // Set the contact name
        holder.contactName.text = contactModel.getName()

        // Always set the baseline_person image as the contact image
        holder.contactImage.setImageResource(R.drawable.baseline_person)



        holder.itemView.setOnClickListener {
            val intent = Intent(context, ContactDetails::class.java)
            intent.putExtra("contactId", contactModel.getId())
            context.startActivity(intent)
        }


    }
    override fun getItemCount(): Int {
        return contactList.size
    }
    // Function to refresh the contact list
    fun refreshContactList(newContactList: ArrayList<ContactModel>) {
        // Update the contact list with the new data
        contactList.clear()
        contactList.addAll(newContactList)

        // Notify the adapter that the data has changed
        notifyDataSetChanged()
    }

    // ViewHolder class to hold the view for each contact item
    class ContactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val contactImage: ImageView = itemView.findViewById(R.id.contact_image)
        val contactName: TextView = itemView.findViewById(R.id.contact_name)
        val contactHorizontal: ImageView = itemView.findViewById(R.id.contact_horizontal)
    }
}