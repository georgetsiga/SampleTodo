package com.example.sampletodo

import android.util.Log
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import com.example.sampletodo.session.SessionManagerListener
import com.example.sampletodo.utils.Constants
import com.example.sampletodo.utils.Utils
import kotlin.math.absoluteValue

open class BaseCompatActivity: AppCompatActivity(), SessionManagerListener {

    override fun onResume() {
        super.onResume()
        Utils.log(MyApplication.TAG, "onResume()")
        MyApplication.registerSessionListener(this)
    }

    /*override fun onUserInteraction() {
        super.onUserInteraction()
        Utils.log(TAG, "User onUserInteraction()")
        MyApplication.resetSession(Constants.SESSION_DURATION)
    }*/

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action == MotionEvent.ACTION_DOWN) {
            Utils.log(TAG, "User onUserInteraction()")
            MyApplication.resetSession(Constants.SESSION_DURATION)
        }
        return super.dispatchTouchEvent(ev)
    }

    override fun onSessionLogout() {
        Utils.log(TAG, "User onSessionLogout()")
        this.runOnUiThread {
            startActivity(CountDownActivity.createIntent(this))
        }
    }

    companion object {
        val TAG: String = BaseCompatActivity::class.java.simpleName
    }
}