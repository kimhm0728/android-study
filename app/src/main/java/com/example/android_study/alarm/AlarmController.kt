package com.example.android_study.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.icu.util.Calendar
import android.util.Log

class AlarmController(private val context: Context) {
    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setAlarm(requestCode: Int) {
        Log.d(TAG, "setAlarm()")

        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 15)
            set(Calendar.MINUTE, 30)
        }

        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
            1000 * 60,
            alarmIntent
        )
    }

    fun cancelAlarm(requestCode: Int) {
        Log.d(TAG, "cancelAlarm()")

        val intent = Intent(context, AlarmReceiver::class.java)
        val alarmIntent = PendingIntent.getBroadcast(context, requestCode, intent, 0)

        alarmManager.cancel(alarmIntent)
    }

    companion object {
        private val TAG = AlarmController::class.simpleName
    }
}