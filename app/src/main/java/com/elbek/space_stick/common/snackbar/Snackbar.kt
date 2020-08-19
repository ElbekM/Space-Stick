package com.elbek.space_stick.common.snackbar

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.elbek.space_stick.R
import com.google.android.material.snackbar.Snackbar

class Snackbar {

    fun showMessageWithTitleAndAction(view: View, context: Context, message: Int, actionTitle: String, action: () -> Unit) {
        val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_INDEFINITE)
        snackBar.view.setBackgroundColor(
            ContextCompat.getColor(context, R.color.snackbar_background)
        )
        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(context, R.color.white))

        snackBar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .maxLines = 5
        snackBar.setAction(actionTitle) { action() }
        snackBar.show()
    }

    fun showMessage(view: View, context: Context, message: String) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(context, R.color.snackbar_background)
        )
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(context, R.color.white))

        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .maxLines = 5
        snackbar.setAction("Ok") { snackbar.dismiss() }
        snackbar.show()
    }

    fun showMessageWithAction(view: View, context: Context, message: String, action: () -> Unit) {
        val snackbar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(
            ContextCompat.getColor(context, R.color.snackbar_background)
        )
        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .setTextColor(ContextCompat.getColor(context, R.color.white))

        snackbar.view.findViewById<TextView>(com.google.android.material.R.id.snackbar_text)
            .maxLines = 5
        snackbar.setAction("Retry") { action() }
        snackbar.show()
    }
}
