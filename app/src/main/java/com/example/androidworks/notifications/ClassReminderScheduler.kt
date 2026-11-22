package com.example.androidworks.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import java.util.Calendar

object ClassReminderScheduler {
    fun schedule(context: Context, time: String, name: String) {
        val (hour, minute) = time.split(":").map { it.toInt() }
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, ClassReminderReceiver::class.java).apply {
            putExtra("profile_name", name)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context, 1007, intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)
        val date = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, hour)
            set(Calendar.MINUTE, minute)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }
        // Если выбранное время уже прошло, напоминаем на следующий день.
        if (date.timeInMillis <= System.currentTimeMillis()) date.add(Calendar.DAY_OF_YEAR, 1)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, date.timeInMillis, pendingIntent)
        } else {
            alarmManager.set(AlarmManager.RTC_WAKEUP, date.timeInMillis, pendingIntent)
        }
    }
}
