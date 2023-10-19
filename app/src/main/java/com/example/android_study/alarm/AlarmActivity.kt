package com.example.android_study.alarm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.android_study.R

class AlarmActivity : AppCompatActivity() {

    private lateinit var alarmController: AlarmController
    private val setBtn: Button by lazy { findViewById(R.id.set_btn) }
    private val cancelBtn: Button by lazy { findViewById(R.id.cancel_btn) }

    private val requestCode = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_alarm)

        alarmController = AlarmController(this)
    }

    override fun onResume() {
        super.onResume()

        setBtn.setOnClickListener {
            alarmController.setAlarm(requestCode)
        }

        cancelBtn.setOnClickListener {
            alarmController.cancelAlarm(requestCode)
        }
    }
}