package com.mrmi.hni

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi

class AlarmNotificationReceiver : BroadcastReceiver()
{
    private val channelID = "com.mrmi.hni"
    private val description = "Hni app notification"
    //Corny messages
    private val notificationMessages = listOf("Воли себе, заслужујеш то :)", "Толико сам срећан што те имам :)", "Волим што сам твој :)",
        "Не морам тражити срећу, нашао сам је у теби :)", "Да могу да вратим време, упознао бих те раније и волео те још дуже :)",
        "Ако знам шта је љубав, знам због тебе :)", "Волим те. Волим те због тога што јеси, и због онога што сам ја када сам са тобом :)",
        "Волим те и никада не бих желео да те изгубим :)", "Не постоји нико кога сада желим више од тебе :)",
        "Волим те колико и мрзим мањине : ^)", "Волим те чак и кад ставиш кревет код кучића :)", "Волим те и кад омашиш хук :)",
        "Не могу више да те волим него сада, а знам да ћу те волети још више сутра :)",
        "Заљубила би се у себе када би се видела као што ја тебе видим :)", "Цео живот сам чекао за некога попут тебе :)",
        "Будим се ујутру само зато што знам да си моја :)", "Знам да Бог постоји јер ми је послао тебе :)",
        "Сваки пут кад те видим поново се заљубим :)", "Твој осмех ме покреће :)", "Успомене са тобом су ми омиљене успомене :)",
        "Желим да будем разлог иза твог осмеха, јер си ти онај иза мог :)", "Не постоји нико на свету кога бих волео више од тебе :)",
        "Неко цео живот трага за оним што сам ја нашао у теби :)", "Ти ниси део мог живота, ти си ми цео живот :)")

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onReceive(context: Context, intent: Intent)
    {
        //Launch app on notification press
        val myIntent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(context, 2822020, myIntent, PendingIntent.FLAG_ONE_SHOT)

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationChannel : NotificationChannel //Used in android O and above
        val builder : Notification.Builder

        //Get notification layout for custom notification
        val notificationContentView = RemoteViews(context.packageName, R.layout.notification_layout)
        //Set notification title text
        notificationContentView.setTextViewText(R.id.notificationTitle, "ХНИ <3")

        //Get selected UI mode (dark/light) and change notification layout's text color and icon accordingly
        when (context.resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK)
        {
            Configuration.UI_MODE_NIGHT_YES ->
            {
                notificationContentView.setTextColor(R.id.notificationText, Color.WHITE)
                notificationContentView.setTextColor(R.id.notificationTitle, Color.WHITE)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    notificationContentView.setImageViewResource(R.id.notificationIcon, R.drawable.light_icon)
            }
            Configuration.UI_MODE_NIGHT_NO ->
            {
                notificationContentView.setTextColor(R.id.notificationText, Color.BLACK)
                notificationContentView.setTextColor(R.id.notificationTitle, Color.BLACK)

                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
                    notificationContentView.setImageViewResource(R.id.notificationIcon, R.drawable.dark_icon)
            }
        }

        val notificationMessage = notificationMessages[(notificationMessages.indices).random()] //Get random element from list of messages
        notificationContentView.setTextViewText(R.id.notificationText, notificationMessage) //Set text in content view

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
        {
            //Using notificationChannel in android versions after and including Oreo
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
            {
                notificationChannel = NotificationChannel(channelID, description, NotificationManager.IMPORTANCE_HIGH)
                notificationChannel.enableVibration(false)
                notificationManager.createNotificationChannel(notificationChannel)

                //Build notification
                builder = Notification.Builder(context, channelID)
                    .setAutoCancel(true)
                    .setContent(notificationContentView)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentIntent(pendingIntent)
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .setPriority(Notification.PRIORITY_MAX)
            }
            else
            {
                //Build notification
                builder = Notification.Builder(context)
                    .setAutoCancel(true)
                    .setContent(notificationContentView)
                    .setSmallIcon(R.drawable.notification_icon)
                    .setContentIntent(pendingIntent)
                    .setVisibility(Notification.VISIBILITY_PRIVATE)
                    .setPriority(Notification.PRIORITY_MAX)
            }

            //Notify user with built notification
            notificationManager.notify(2822020, builder.build())
        }

        //Schedule next notification alarm once notification is sent
        println("[JA]: Starting next notification from notification receiver")
        val useNotificationsClass = UseNotificationsClass()
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
            useNotificationsClass.useNotifications(context)
    }
}