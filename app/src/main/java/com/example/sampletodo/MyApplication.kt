package com.example.sampletodo

import android.app.Application
import android.content.Context
import android.os.CountDownTimer
import android.util.Log
import com.example.sampletodo.session.SessionManagerListener
import com.example.sampletodo.utils.Constants.SESSION_DURATION
import com.example.sampletodo.utils.Utils
import dagger.hilt.android.HiltAndroidApp
import java.util.*

@HiltAndroidApp
class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        myApplication = this
    }

    companion object {
        val TAG: String = MyApplication::class.java.simpleName
        private lateinit var myApplication: MyApplication
        private lateinit var sessionManagerListener: SessionManagerListener
        private lateinit var countDownTimer: CountDownTimer

        fun getContext(): Context {
            return myApplication.applicationContext
        }

        fun resetSession(duration: Long) {
            Utils.log(TAG, "resetSession()")
            if (duration != 0L) {
                userSessionStart(duration)
            }
        }

        fun registerSessionListener(listener: SessionManagerListener) {
            Utils.log(TAG, "registerSessionListener()")
            sessionManagerListener = listener
            userSessionStart(SESSION_DURATION)
        }

        fun stopTimerIfRunning() {
            Utils.log(TAG, "stopTimerIfRunning()")

            if (this::countDownTimer.isInitialized) {
                countDownTimer.cancel()
            }
        }

        private fun userSessionStart(duration: Long) {
            Utils.log(TAG, "userSessionStart()")
            stopTimerIfRunning()

            countDownTimer = object : CountDownTimer(duration, 1000) {
                override fun onTick(millisUntilFinished: Long) {
                    val timeLeft = millisUntilFinished / 1000
                    val remainingTime = if (timeLeft >= 10) "00:$timeLeft" else "00:0$timeLeft"
                    Utils.log(TAG, "onTick() $remainingTime")
                }

                override fun onFinish() {
                    Utils.log(TAG, "onFinish()")
                    cancel()
                    sessionManagerListener.onSessionLogout()
                    Utils.log(TAG, "Login out")
                    stopTimerIfRunning()
                }
            }
            countDownTimer.start()

        }
    }
}