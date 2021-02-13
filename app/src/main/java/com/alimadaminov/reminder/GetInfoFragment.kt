package com.alimadaminov.reminder

import android.app.Activity
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.fragment.findNavController
import com.alimadaminov.reminder.databinding.FragmentGetInfoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class GetInfoFragment : Fragment() {

    lateinit var binding: FragmentGetInfoBinding
    val auth = FirebaseAuth.getInstance()
    val dbRef = Firebase.database.reference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGetInfoBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnSubmit.setOnClickListener {
            if (isNotEmptyEdt(binding.edtFirstName)
                && isNotEmptyEdt(binding.edtSecondName)
                && isNotEmptyEdt(binding.edtEmail)
            ) {
                var user: User = User(
                    binding.edtFirstName.text.toString(),
                    binding.edtSecondName.text.toString(),
                    binding.edtEmail.text.toString(),
                    auth.currentUser?.phoneNumber,
                    arrayListOf<Reminder>()
                )

                dbRef.child(getString(R.string.db_child_users))
                    .child(auth.currentUser?.uid!!).setValue(user)

                hideKeyboard(activity)
                findNavController().navigate(R.id.action_getInfoFragment_to_showRemindersFragment)
            } else {
                Toast.makeText(context, "You haven't entered something", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun isNotEmptyEdt(edt: EditText) = edt.text.toString().isNotEmpty()

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GetInfoFragment().apply {

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