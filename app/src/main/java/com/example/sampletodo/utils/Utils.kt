package com.example.sampletodo.utils

import android.util.Log

object Utils {
    fun log(tag: String, message: String) {
        Log.d("App->$tag", message)
    }
}