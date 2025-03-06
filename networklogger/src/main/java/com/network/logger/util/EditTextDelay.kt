package com.network.logger.util

import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.FragmentActivity
import java.util.Timer
import java.util.TimerTask

class EditTextDelay(private val context: FragmentActivity?) : TextWatcher {
    private var timer = Timer()

    var listener: Listener? = null

    interface Listener {
        fun onTextChanged(data: String?)
    }

    fun setListener(listener: Listener?): TextWatcher {
        this.listener = listener
        return this
    }

    override fun beforeTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun onTextChanged(charSequence: CharSequence, i: Int, i1: Int, i2: Int) {
    }

    override fun afterTextChanged(editable: Editable) {
        timer.cancel()
        timer = Timer()
        timer.schedule(
            object : TimerTask() {
                override fun run() {
                    if (context == null) return
                    context.runOnUiThread(Runnable {
                        listener?.onTextChanged(
                            editable.toString().trim { it <= ' ' })
                    })
                }
            }, 1000
        )
    }
}
