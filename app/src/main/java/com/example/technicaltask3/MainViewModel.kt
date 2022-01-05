package com.example.technicaltask3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.random.Random

class MainViewModel(private val context: Context) : ViewModel() {
    private val CHANNEL_ID = "notify_001"
    private val CHANNEL_NAME = "My main channel"
    private val NUMBER_OF_PAGE = "numberOfPage"

    private val mutableLiveDataModelResult: MutableLiveData<MutableList<SlidePageFragment>> =
        MutableLiveData(mutableListOf(SlidePageFragment(1)))
    val liveDataModelResult: LiveData<MutableList<SlidePageFragment>> = mutableLiveDataModelResult

    private val mutableLiveDataNotification: HashMap<Int, MutableList<Int>> = HashMap()

    fun clearLists(){
        mutableLiveDataNotification.clear()
        mutableLiveDataModelResult.value?.clear()
    }
    fun addFragment() {
        mutableLiveDataModelResult.run {
            var list = mutableLiveDataModelResult.value
            list?.add(SlidePageFragment(list.size + 1))
            this.value = list
        }
    }

    fun removeFragment() {
        mutableLiveDataModelResult.run {
            var list = mutableLiveDataModelResult.value
            list?.removeLast()
            this.value = list
        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )
            val mNotificationManager = context.getSystemService(
                NotificationManager::class.java
            )
            mNotificationManager.createNotificationChannel(channel)
        }
    }

    fun createNotification(number: Int) {
        val intent = Intent(context, MainActivity::class.java).apply {
            //flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            putExtra(NUMBER_OF_PAGE, number)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            Random.nextInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )


        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setContentTitle(context.resources.getString(R.string.youCreateNotification))
            .setContentText(context.resources.getString(R.string.notification) + " $number")
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            var randomInt: Int = Random.nextInt()
            notify(randomInt, builder.build())
            if (mutableLiveDataNotification.containsKey(number)) {
                mutableLiveDataNotification[number]?.add(randomInt)
            } else mutableLiveDataNotification[number] = mutableListOf(randomInt)
        }
    }

    fun removeNotifications() {
        val ns: String = Context.NOTIFICATION_SERVICE
        val nMgr = context.getSystemService(ns) as NotificationManager
        var sizeList = liveDataModelResult.value?.size

        if (mutableLiveDataNotification.containsKey(sizeList) && mutableLiveDataNotification[sizeList] != null) {
            for (i in mutableLiveDataNotification[sizeList]!!) {
                nMgr.cancel(i)
            }
        }
        mutableLiveDataNotification.remove(sizeList)
    }
}