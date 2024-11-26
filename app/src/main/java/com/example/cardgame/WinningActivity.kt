package com.example.cardgame

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class WinningActivity : AppCompatActivity() {
    lateinit var resultView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_winning)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        resultView = findViewById(R.id.resultView)
        val backButton = findViewById<Button>(R.id.backButton)
        val whoWin = intent.getIntExtra("whoWin", 0)
        if (whoWin == 0) {
            resultView.text = "Noone wins this time"
        }
        if (whoWin == 1) {
            resultView.text = "Player 1 wins!!!"
        }
        if (whoWin == 2) {
            resultView.text = "Player 2 wins!!!"
        }
        backButton.setOnClickListener {
            finish() // goes back to existing activity
        }
    }
}