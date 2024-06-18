package com.example.views.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.views.R


class MainActivity : AppCompatActivity(R.layout.activity_main) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        findViewById<StatsView>(R.id.stats).data = listOf(
            25.00F,
            25.00F,
            25.00F,
            25.00F,
        )

    }
}