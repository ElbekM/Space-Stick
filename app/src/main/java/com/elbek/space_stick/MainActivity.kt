package com.elbek.space_stick

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import com.elbek.space_stick.screens.main.MainFragment
import com.elbek.space_stick.screens.stick.StickFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(FrameLayout(this).apply { fitsSystemWindows = true })

        launchMainScreen()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)

        launchMainScreen()
    }

    private fun launchMainScreen() {
        MainFragment.newInstance().apply {
            supportFragmentManager.beginTransaction()
                .add(this, this::class.java.name)
                .addToBackStack(this::class.java.name)
                .commitAllowingStateLoss()
        }
    }
}
