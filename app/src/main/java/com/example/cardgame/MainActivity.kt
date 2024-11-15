package com.example.cardgame

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    var player1Suits = arrayListOf<Int>()

    val player2Suits = arrayListOf<Int>()

    var cardsOfHeartsP1: Int = 0
    var cardsOfDiamondsP1: Int = 0
    var cardsOfSpadesP1: Int = 0
    var cardsOfClubsP1: Int = 0
    var cardsOfHeartsP2: Int = 0
    var cardsOfDiamondsP2: Int = 0
    var cardsOfSpadesP2: Int = 0
    var cardsOfClubsP2: Int = 0


    var firstPlayerTurn: Boolean = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

//        player1Suits.add(cardsOfClubsP1)
//        player1Suits.add(cardsOfHeartsP1)
//        player1Suits.add(cardsOfSpadesP1)
//        player1Suits.add(cardsOfDiamondsP1)
//
//        player2Suits.add(cardsOfClubsP2)
//        player2Suits.add(cardsOfHeartsP2)
//        player2Suits.add(cardsOfSpadesP2)
//        player2Suits.add(cardsOfDiamondsP2)
    }

    fun checkWin(
        currentDeck: ArrayList<Card>,
        player1Deck: ArrayList<Card>,
        player2Deck: ArrayList<Card>
    ) {
        if (currentDeck.size == 0) {
            //sout "noone wins"

        }


    }

    fun pullCard(
        currentDeck: ArrayList<Card>,
        player1Deck: ArrayList<Card>,
        player2Deck: ArrayList<Card>
    ) {
        var i = (0..<currentDeck.size).random()
        if (firstPlayerTurn) {
            player1Deck.add(currentDeck[i])
            currentDeck.remove(currentDeck[i])
            if (currentDeck[i].suit == "hearts") {
                cardsOfHeartsP1++
            }
            if (currentDeck[i].suit == "diamonds") {
                cardsOfDiamondsP1++
            }
            if (currentDeck[i].suit == "spades") {
                cardsOfSpadesP1++
            }
            if (currentDeck[i].suit == "clubs") {
                cardsOfClubsP1++
            }
           firstPlayerTurn = false
//show popup with players new card
        }
        else if (!firstPlayerTurn) {
            player2Deck.add(currentDeck[i])
            currentDeck.remove(currentDeck[i])
            if (currentDeck[i].suit == "hearts") {
                cardsOfHeartsP2++
            }
            if (currentDeck[i].suit == "diamonds") {
                cardsOfDiamondsP2++
            }
            if (currentDeck[i].suit == "spades") {
                cardsOfSpadesP2++
            }
            if (currentDeck[i].suit == "clubs") {
                cardsOfClubsP2++
            }
            firstPlayerTurn = true
        }
    }
}