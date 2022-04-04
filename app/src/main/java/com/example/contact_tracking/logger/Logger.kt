package com.example.contact_tracking.logger

import android.util.Log


/**
 * This class was created to track the functions
 */

class Logger(simpleName: String) {

    private var classname: String = ""

    // log INFO messages
    fun info(msg: String) {
        val infoMessage = "INFO ==> $msg"
        Log.i(this.classname, infoMessage)
    }

    // log WARN messages
    fun warning(msg: String) {
        val warningMessage = "WARNING ==> $msg"
        Log.wtf(this.classname, warningMessage)
    }

    // log ERROR messages
    fun error(msg: String) {
        val errorMessage = "ERROR ==> $msg"
        Log.e(this.classname, errorMessage)
    }
}