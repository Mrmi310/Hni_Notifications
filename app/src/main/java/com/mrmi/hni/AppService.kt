package com.mrmi.hni

import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.annotation.RequiresApi


//Used for dealing with app running after ultra power saving mode and similar app killers/blockers
open class AppService : Service()
{
    private val TAG = "MyService"
    override fun onBind(intent: Intent?): IBinder?
    {
        return null
    }

    override fun onDestroy()
    {
        //Toast.makeText(this, "My Service Stopped", Toast.LENGTH_LONG).show()
        Log.d(TAG, "onDestroy")
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate()
    {
        //Schedule next notification
        val useNotificationsClass = UseNotificationsClass()
        useNotificationsClass.useNotifications(this)
    }
}