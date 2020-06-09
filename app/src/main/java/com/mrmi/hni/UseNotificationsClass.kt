package com.mrmi.hni

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class UseNotificationsClass
{
    @RequiresApi(Build.VERSION_CODES.M)
    fun useNotifications(context: Context)
    {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager //Alarm manager
        val myIntent = Intent(context, AlarmNotificationReceiver::class.java)

        //Launch AlarmNotificationReceiver via an intent
        val pendingIntent: PendingIntent = PendingIntent.getBroadcast(context, 2822020, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        //Hours and minutes in milliseconds to be added to the current system time which the alarm will be scheduled at
        val hours = (3600000)*((0..12).random())
        val minutes = (60000)*((0..59).random())

        //Schedule alarm which can activate even when device is in doze (battery saving) or idle mode
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis()+hours+minutes,
            pendingIntent)

        //Toast.makeText(context,"Set notification",Toast.LENGTH_SHORT).show();
    }

    //This function overrides the currently scheduled notification with a new pending intent and then cancels it resulting in no notification being scheduled.
    fun cancelNotifications(context: Context)
    {
        val myIntent = Intent(context, AlarmNotificationReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, 2822020, myIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)
    }
}