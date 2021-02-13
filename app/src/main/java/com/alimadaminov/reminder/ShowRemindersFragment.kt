package com.alimadaminov.reminder

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alimadaminov.reminder.databinding.FragmentShowRemindersBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class ShowRemindersFragment : Fragment() {

    lateinit var binding: FragmentShowRemindersBinding
    val dbRef = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentShowRemindersBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_showRemindersFragment_to_addReminderFragment)
        }
        val reminderListener = object : ValueEventListener {

            override fun onDataChange(snapshot: DataSnapshot) {
                val reminders = snapshot.getValue<ArrayList<Reminder>>()
                if (reminders != null) {
                    ReminderListSize.size = reminders.size

                    var reminderAdapter = ReminderAdapter()
                    reminderAdapter.addReminderList(reminders)

                    binding.recycler.apply {
                        addItemDecoration(ItemDecoration())
                        adapter = reminderAdapter
                        layoutManager =
                            LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        }

        dbRef.child("users").child(FirebaseAuth.getInstance().currentUser?.uid.toString())
            .child("reminders").addValueEventListener(reminderListener)
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShowRemindersFragment().apply {

            }
    }
}