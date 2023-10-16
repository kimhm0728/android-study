package com.example.android_study.fcm

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.example.android_study.R
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch
import java.lang.Exception

class FcmActivity : AppCompatActivity() {
    private lateinit var button: Button
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.e(TAG, "Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            token = task.result ?: return@OnCompleteListener

            Log.e(TAG, "token: $token")
            Toast.makeText(this, token, Toast.LENGTH_SHORT).show()
        })

        button.setOnClickListener {
            sendNotification(PushNotification(NotificationData("title", "string"), token))
        }
    }

    private fun sendNotification(notification: PushNotification) = lifecycleScope.launch {
        try {
            val response = RetrofitBuilder.api.postNotification(notification)
            if (response.isSuccessful) {
                Log.e(TAG, "Response: ${response.body()?.string()}")
            } else {
                Log.e(TAG, "Error: ${response.errorBody()?.string()}")
            }
        } catch (e: Exception) {
            Log.e(TAG, e.toString())
        }
    }

    companion object {
        private val TAG = FcmActivity::class.simpleName
    }
}