package com.badger.familyorgbe.service.products

import com.badger.familyorgbe.infoLog
import java.util.TimerTask

class ExpirationTimer : TimerTask() {


    override fun run() {
        infoLog("RUN")
    }
}