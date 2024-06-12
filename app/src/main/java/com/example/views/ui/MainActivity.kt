package com.example.views.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.views.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<StatsView>(R.id.stats).data = listOf(
            0.25F,
            0.25F,
            0.25F,
            0.25F,
        )
    }
}