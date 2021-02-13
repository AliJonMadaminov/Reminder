package com.alimadaminov.reminder

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.alimadaminov.reminder.databinding.ItemReminderBinding
import java.util.ArrayList

class ReminderAdapter(var remainders: ArrayList<Reminder> = ArrayList()) :
    RecyclerView.Adapter<ReminderAdapter.ReminderViewHolder>() {


    class ReminderViewHolder(val binding: ItemReminderBinding) :
        RecyclerView.ViewHolder(binding.root) {
        val title: TextView = binding.txtTitle
        val doneBtn: ImageView = binding.imgDone

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReminderViewHolder {
        return ReminderViewHolder(ItemReminderBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ReminderViewHolder, position: Int) {
        holder.title.text = remainders[position].title
        holder.doneBtn.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return remainders.size
    }

    fun addReminderList(_reminders:ArrayList<Reminder>) {
        remainders = _reminders
        notifyDataSetChanged()
    }
}