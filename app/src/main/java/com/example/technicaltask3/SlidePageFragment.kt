package com.example.technicaltask3

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import kotlin.random.Random

class SlidePageFragment(private var number: Int): Fragment() {

    private lateinit var buttonNotification:Button
    private var counts:Int = 0
    private lateinit var vm:MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MyLog", "onViewCreated $counts")

        vm = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        vm.liveDataModelResult.observe(requireActivity(), {
            counts = it.size
        })

        buttonNotification = view.findViewById(R.id.buttonCreateNotification)
        buttonNotification.setOnClickListener {
            Log.d("MyLog", "Notification " + vm.mutableLiveDataNotification[number]?.size)


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(
                    "notify_001",
                    "Channel for student",
                    NotificationManager.IMPORTANCE_HIGH
                )
                val mNotificationManager = view.context.getSystemService(
                    NotificationManager::class.java
                )
                mNotificationManager.createNotificationChannel(channel)
            }

            val intent = Intent(view.context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP
                putExtra("countPages", counts)
                putExtra("numberOfPage", number)
            }

            val pendingIntent: PendingIntent = PendingIntent.getActivity(
                view.context,
                0,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )


            val builder = NotificationCompat.Builder(view.context, "notify_001")
                .setSmallIcon(R.drawable.ic_launcher_background)
                .setContentTitle("You create a notification")
                .setContentText("Notification $number")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)

            with(NotificationManagerCompat.from(view.context)) {
                var randomInt:Int = Random.nextInt()
                notify(randomInt, builder.build())
                if (vm.mutableLiveDataNotification.containsKey(number)){
                    vm.mutableLiveDataNotification[number]?.add(randomInt)
                }else vm.mutableLiveDataNotification[number] = mutableListOf(randomInt)
            }
        }
    }

}