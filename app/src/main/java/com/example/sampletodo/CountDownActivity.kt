package com.example.sampletodo

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.sampletodo.ui.login.LoginActivity
import com.example.sampletodo.utils.Utils

class CountDownActivity : AppCompatActivity() {

    private lateinit var timerTextView: TextView
    private lateinit var refreshSessionButton: Button
    private lateinit var countDownTimer: CountDownTimer
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_count_down)
        Utils.log(TAG, "onCreate()")
        timerTextView = findViewById(R.id.timer_count)
        refreshSessionButton = findViewById(R.id.restart_session)

        refreshSessionButton.setOnClickListener {
            setResult(Activity.RESULT_OK)
            countDownTimer.cancel()
            finish()
        }
        setData()
    }

    private fun setData() {
        countDownTimer = object : CountDownTimer(30000, 1000) {
            override fun onTick(millisUntilFinished: Long) {

                val timeLeft = millisUntilFinished / 1000
                val remainingTime = if (timeLeft >= 10) "00:$timeLeft" else "00:0$timeLeft"
                Utils.log(TAG, "onTick() $remainingTime")
                timerTextView.text = remainingTime
                //val progress = (millisUntilFinished / 150).toInt()
            }

            override fun onFinish() {
                Utils.log(TAG, "onFinish()")
                cancel()
                MyApplication.stopTimerIfRunning()
                startActivity(LoginActivity.createIntent(this@CountDownActivity))
                finish()
            }
        }
        countDownTimer.start()
    }

    companion object {
        val TAG: String = CountDownActivity::class.java.simpleName
        fun createIntent(context: Context): Intent {
            Utils.log(TAG, "createIntent()")
            return Intent(context, CountDownActivity::class.java)
        }
    }
}