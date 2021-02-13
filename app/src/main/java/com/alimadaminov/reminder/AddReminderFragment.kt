package com.alimadaminov.reminder

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.alimadaminov.reminder.databinding.FragmentAddReminderBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class AddReminderFragment : Fragment() {

    lateinit var binding: FragmentAddReminderBinding
    val dbRef = Firebase.database.reference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAddReminderBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnAdd.setOnClickListener {
            if (isNotEmptyEdt(binding.edtTitle)
                && isNotEmptyEdt(binding.edtDescription)
            ) {

                dbRef.child(getString(R.string.db_child_users))
                    .child(FirebaseAuth.getInstance().currentUser?.uid!!)
                    .child(getString(R.string.db_child_reminders))
                    .child("${ReminderListSize.size}")
                    .setValue(
                        Reminder(
                            binding.edtTitle.text.toString(),
                            binding.edtDescription.text.toString()
                        )
                    )
                findNavController().navigate(R.id.action_addReminderFragment_to_showRemindersFragment)
                hideKeyboard(activity)
            }
        }
    }

    fun isNotEmptyEdt(edt: EditText) = edt.text.toString().isNotEmpty()

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            AddReminderFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }

    fun hideKeyboard(activity: FragmentActivity?) {
        val imm = activity?.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        //Find the currently focused view, so we can grab the correct window token from it.
        var view = activity.currentFocus
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}