package com.example.android_study.service

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.databinding.DataBindingUtil
import com.example.android_study.R
import com.example.android_study.databinding.ActivityServiceBinding

class ServiceActivity : AppCompatActivity() {
    private var _binding: ActivityServiceBinding? = null
    private val binding
        get() = _binding!!

    private lateinit var mService: MyService
    private var mBound = false
    private var mIsBound = false
    private var mTextViewValue = 0

    private val connection = object : ServiceConnection {
        override fun onServiceConnected(className: ComponentName, service: IBinder) {
            mService = (service as MyService.MyBinder).getService()
            mBound = true
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
            mBound = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = DataBindingUtil.setContentView(this, R.layout.activity_service)
    }

    override fun onResume() {
        super.onResume()

        with(binding) {
            startButton.setOnClickListener {
                startService()
            }

            stopButton.setOnClickListener {
                stopService()
            }

            addButton.setOnClickListener {
                progressService()
            }
        }
    }

    private fun startService() {
        Log.d(TAG, "start service")
        Intent(this@ServiceActivity, MyService::class.java).also { intent ->
            if (mIsBound) {
                bindService(intent, connection, Context.BIND_AUTO_CREATE)
            } else {
                startService(intent)
            }
        }
    }

    private fun stopService() {
        Log.d(TAG, "stop service")
        if (mIsBound) {
            if (mBound) {
                unbindService(connection)
                mBound = false
            }
        } else {
            Intent(this@ServiceActivity, MyService::class.java).also { intent ->
                stopService(intent)
            }
        }
    }

    private fun progressService() {
        Log.d(TAG, "progress service")
        if (mIsBound) {
            mService.bound()
        } else {
            binding.textView.text = (++mTextViewValue).toString()
        }
    }

    override fun onStop() {
        super.onStop()
        stopService()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {
        private val TAG = ServiceActivity::class.simpleName
    }
}