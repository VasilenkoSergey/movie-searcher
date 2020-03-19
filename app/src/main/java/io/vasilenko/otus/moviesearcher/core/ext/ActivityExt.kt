package io.vasilenko.otus.moviesearcher.core.ext

import android.app.Activity
import com.google.android.material.snackbar.Snackbar
import io.vasilenko.otus.moviesearcher.presentation.common.MessageBundle
import kotlinx.android.synthetic.main.activity_movies.*

fun Activity.showSnack(messageBundle: MessageBundle) {
    val snackbar = Snackbar.make(
        moviesContainer,
        messageBundle.text,
        Snackbar.LENGTH_SHORT
    )
    messageBundle.action?.let {
        snackbar.setAction(messageBundle.action.name, messageBundle.action.listener).show()
    }
    snackbar.show()
}