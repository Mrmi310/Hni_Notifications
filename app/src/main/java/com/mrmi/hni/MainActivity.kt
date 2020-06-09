package com.mrmi.hni

import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Build
import android.os.Bundle
import android.os.SystemClock
import android.widget.ImageButton
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import java.util.*

class MainActivity : AppCompatActivity()
{
    @SuppressLint("SourceLockedOrientationActivity")
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Lock app to portrait mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        val sharedPreferences = getSharedPreferences("SP_INFO", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //Edit shared preferences to store data

        val notificationsButton : ImageButton = findViewById(R.id.notificationsButton)
        var lastButtonClickTime = 0 //Used to prevent rapid button clicking

        //Set notify user to true if notifyUser doesn't exist in shared preferences
        if(!sharedPreferences.contains("notifyUser"))
        {
            editor.putBoolean("notifyUser", true)
            val useNotificationsClass = UseNotificationsClass()
            useNotificationsClass.useNotifications(this)
            editor.apply()
        }

        displayDaysText()
        displayButtonText()
        //val useNotificationsClass = UseNotificationsClass()
        //useNotificationsClass.useNotifications(this)

        notificationsButton.setOnClickListener()
        {
            //Prevent rapid clicking using 1 second cooldown between clicks
            if (SystemClock.elapsedRealtime() - lastButtonClickTime >= 1000)
            {
                lastButtonClickTime = SystemClock.elapsedRealtime().toInt()

                val notify = sharedPreferences.getBoolean("notifyUser", true)
                //If the current state of notifyUser is false, set notifyUser to true and schedule next notification
                if(!notify)
                {
                    val useNotificationsClass = UseNotificationsClass()
                    useNotificationsClass.useNotifications(this)

                    editor.putBoolean("notifyUser", true)
                    editor.apply()
                }
                //If notifyUser is true then set it to false (turn off notifications)
                else
                {
                    val useNotificationsClass = UseNotificationsClass()
                    useNotificationsClass.cancelNotifications(this)

                    editor.putBoolean("notifyUser", false)
                    editor.apply()
                }
                displayButtonText()
            }
        }
    }

    override fun onResume()
    {
        super.onResume()
        displayDaysText()
        displayButtonText()
    }

    private fun displayDaysText()
    {
        val daysTogetherText : TextView = findViewById(R.id.daysTogetherText)
        //Holds 28.2.2020
        val cal1 : Calendar = Calendar.getInstance().apply{
            set(Calendar.DAY_OF_MONTH, 27)
            set(Calendar.MONTH, Calendar.FEBRUARY)
            set(Calendar.YEAR, 2020)
        }
        //Holds current date and time
        val cal2 : Calendar = Calendar.getInstance().apply {
            timeInMillis=System.currentTimeMillis()
        }

        //Display days passed since 28.2.2020.
        var numberOfDays = ((cal2.timeInMillis-cal1.timeInMillis)/86400000).toString()
        daysTogetherText.text = ("Заједно " + numberOfDays)
        //Serbian spelling
        if(numberOfDays[numberOfDays.length-1]=='1')
            daysTogetherText.text = (daysTogetherText.text as String + " дан :)")
        else
            daysTogetherText.text = (daysTogetherText.text as String + " дана :)")

    }

    private fun displayButtonText()
    {
        val sharedPreferences = getSharedPreferences("SP_INFO", Context.MODE_PRIVATE)

        val notificationsButton : ImageButton = findViewById(R.id.notificationsButton)

        val notify = sharedPreferences.getBoolean("notifyUser", true)
        if(notify)
            notificationsButton.setImageResource(R.drawable.notifications_on)
        else
            notificationsButton.setImageResource(R.drawable.notifications_off)
    }
}
