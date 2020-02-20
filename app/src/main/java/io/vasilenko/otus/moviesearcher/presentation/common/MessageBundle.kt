package io.vasilenko.otus.moviesearcher.presentation.common

import android.view.View

class MessageBundle(val text: String, val action: Action? = null) {

    class Action(val name: String, val listener: View.OnClickListener)
}