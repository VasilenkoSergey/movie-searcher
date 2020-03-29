package io.vasilenko.otus.moviesearcher.presentation.common

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import io.vasilenko.otus.moviesearcher.R
import kotlinx.android.synthetic.main.dialog_quit.*

class QuitDialog(context: Context) : Dialog(context) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setCancelable(false)
        setContentView(R.layout.dialog_quit)
        quitCancel.setOnClickListener { dismiss() }
        quitOk.setOnClickListener { cancel() }
    }
}