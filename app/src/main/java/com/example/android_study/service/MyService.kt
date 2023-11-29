package com.example.android_study.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.Handler
import android.os.HandlerThread
import android.os.IBinder
import android.os.Looper
import android.os.Message
import android.os.Process
import android.util.Log
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.disposables.Disposable
import java.util.concurrent.TimeUnit

class MyService : Service() {
    private val mIsBound = false
    private val mBinder = MyBinder()
//    private val mObservable = Observable.interval(1, TimeUnit.SECONDS)
//    private var mDisposable: Disposable? = null
    private var mLooper: Looper? = null
    private var mHandler: MyServiceHandler? = null
    private var mIsRun = false

    inner class MyBinder : Binder() {
        fun getService(): MyService = this@MyService
    }

    private inner class MyServiceHandler(looper: Looper) : Handler(looper) {
        override fun handleMessage(msg: Message) {
            when (msg.what) {
                0 -> {
                    while (mIsRun) {
                        Log.d(TAG, "running handler")
                        try {
                            Thread.sleep(1000)
                        } catch (e: InterruptedException) {
                            Thread.currentThread().interrupt()
                        }
                    }
                }
                else -> super.handleMessage(msg)
            }
        }
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(TAG, "onCreate()")
        if (!mIsBound) {
            HandlerThread("ServiceStartArguments", Process.THREAD_PRIORITY_BACKGROUND).apply {
                start()
                mIsRun = true
                mLooper = looper
                mHandler = MyServiceHandler(looper)
            }
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        Log.d(TAG, "onStartCommand()")

//        mDisposable = mObservable.subscribe { value ->
//            Log.d(TAG, "1 second interval : $value")
//        }
        mHandler?.sendEmptyMessage(0)

        return START_STICKY
    }

    fun bound() {
        Log.d(TAG ,"call bound service method on activity")
    }

    override fun onBind(intent: Intent?): IBinder? {
        Log.d(TAG, "onBind()")
        return mBinder
    }

    override fun onUnbind(intent: Intent?): Boolean {
        Log.d(TAG, "onUnbind()")
        return true
    }

    override fun onRebind(intent: Intent) {
        Log.d(TAG, "onRebind()")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy()")
//        mDisposable?.dispose()
        mIsRun = false
    }

    companion object {
        private val TAG = MyService::class.simpleName
    }
}