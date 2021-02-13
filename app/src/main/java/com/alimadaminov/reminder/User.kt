package com.alimadaminov.reminder

data class User(var firstName:String?, var secondName:String?,
                var email:String?, var phoneNumber:String?, var reminders:ArrayList<Reminder>?)