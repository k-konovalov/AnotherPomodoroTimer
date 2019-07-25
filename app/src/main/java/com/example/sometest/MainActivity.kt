package com.example.sometest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

var cycle: Int = 0
//Специально для тебя комменчу. чтобы знал что к чему
//1)Тут у нас константы для ключей, т.к. в преференсах хранится все в структуре ключ -> значение
const val APP_PREFERENCES ="app_settings"
const val REST_TIME = "rest_time"
const val WORK_TIME = "work_time"
const val WORK_CYCLES = "work_cycles"
const val BIG_BREAK = "big_break"

const val CHANNEL_ID = "Simple Pomodoro"
const val notificationId = 1
//2)Тут объявляю префы и эдитор, чтобы пользоваться ими во всем проекте. Лэйт инитом, потому что другого варианта не нашел.
//Кажется инициализировать его можно только в рантайме, но я могу и крупно ошибаться по этому поводу
lateinit var pref: SharedPreferences
lateinit var editor: SharedPreferences.Editor
//notification setup
lateinit private var builder: NotificationCompat.Builder
lateinit private var context:Context
class MainActivity : AppCompatActivity() {
    companion object {
        fun updateCurrentNotification(max:Int,progress:Int,currentStatus:String) {
            NotificationManagerCompat.from(context).apply {
                builder.setProgress(max, progress,false)
                    .setOnlyAlertOnce(true)
                    .setContentTitle(currentStatus)
                    //.setStyle(NotificationCompat.BigTextStyle)
                notify(notificationId, builder.build())
            }
        }

        fun removeNotificationProgressBar(){
            with(NotificationManagerCompat.from(context)) {
                // notificationId is a unique int for each notification that you must define
                builder.setProgress(0, 0, false)
                    .setOnlyAlertOnce(false)
                notify(notificationId, builder.build())
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //Так мы это дело получаем. Контекст мод прайват - по дефолту, до него имя для файлика
        pref = getSharedPreferences(APP_PREFERENCES, Context.MODE_PRIVATE)
        editor = pref.edit()

        //notification setup
        context = applicationContext
        builder = NotificationCompat.Builder(this,CHANNEL_ID )
            .setSmallIcon(R.drawable.baseline_settings_black_24)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setOngoing(true)
        //run it
        createNotificationChannel()
    }
    //fun for notifications
    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(CHANNEL_ID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun onDestroy() {
        builder.setOngoing(false)
        super.onDestroy()

    }
}