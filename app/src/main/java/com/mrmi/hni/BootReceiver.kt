package com.mrmi.hni

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi

class BootReceiver : BroadcastReceiver()
{
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onReceive(context: Context, intent: Intent)
    {
        //Start app service which schedules a new alarm and always runs in the background
        val intent = Intent(context, AppService::class.java)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            context.startForegroundService(intent)
        else
            context.startService(intent)
    }
}