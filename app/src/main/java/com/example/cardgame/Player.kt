package com.example.cardgame

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import com.google.android.material.snackbar.Snackbar


class Player(val mainActivity: MainActivity,
    val myContext: Context, val name: String, val winTimes: Int, val lostTimes: Int
) {

    var firstPlayerTurn: Boolean = true

    val bothPlayersCards = arrayListOf<Int>()
    fun bothPlayersCards() {
        var cardsOfHeartsP1: Int = 0 //0
        var cardsOfDiamondsP1: Int = 0//1
        var cardsOfSpadesP1: Int = 0//2
        var cardsOfClubsP1: Int = 0//3
        var cardsOfHeartsP2: Int = 0//4
        var cardsOfDiamondsP2: Int = 0//5
        var cardsOfSpadesP2: Int = 0//6
        var cardsOfClubsP2: Int = 0//7
        bothPlayersCards.add(cardsOfHeartsP1)
        bothPlayersCards.add(cardsOfDiamondsP1)
        bothPlayersCards.add(cardsOfSpadesP1)
        bothPlayersCards.add(cardsOfClubsP1)
        bothPlayersCards.add(cardsOfHeartsP2)
        bothPlayersCards.add(cardsOfDiamondsP2)
        bothPlayersCards.add(cardsOfSpadesP2)
        bothPlayersCards.add(cardsOfClubsP2)
Log.d("SOUT", bothPlayersCards.toString())
    }
    fun pullCard(
        player2Status: TextView,
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        pullCard: Button,
        pleaseWait: FrameLayout

    ) {

        var i = (0..<currentDeck.size).random()
        if (firstPlayerTurn) {

            player1PullCard(
                player2Status,
                currentDeck,
                pulledCardp1,
                pulledCardp2,
                i,
                pullCard,
                pleaseWait
            )
        }
    }

    fun player1PullCard(
        player2Status: TextView,
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        i: Int, pullCard: Button,
        pleaseWait: FrameLayout
    ) {

        val builder = AlertDialog.Builder(myContext)
        builder.setTitle("Time to pull a card?")
        builder.setMessage("Rank of the card is ${currentDeck[i].numberOfCard} ")

        builder.setPositiveButton("Yes") { dialog, which ->
            if (currentDeck[i].suit == "hearts") {
                bothPlayersCards[0]++
            }
            if (currentDeck[i].suit == "diamonds") {
                bothPlayersCards[1]++
            }
            if (currentDeck[i].suit == "spades") {
                bothPlayersCards[2]++
            }
            if (currentDeck[i].suit == "clubs") {
                bothPlayersCards[3]++
            }
            Log.d("))))", bothPlayersCards.toString())
            mainActivity.checkWin(currentDeck, bothPlayersCards)
            pulledCardp1.text =
                "Clubs: $bothPlayersCards[3],\nSpades:  $ bothPlayersCards[2],\nDiamonds: $bothPlayersCards[1], \nHearts: $bothPlayersCards[0]"
            Log.d(
                "!!!!",
                "Clubs: $bothPlayersCards[7], Spades: $bothPlayersCards[6],Diamonds: $bothPlayersCards[5],Hearts:  $bothPlayersCards[4]"
            )
                .toString()

            val builder = AlertDialog.Builder(myContext)
            builder.setTitle("You got ${currentDeck[i].suit}  ${currentDeck[i].numberOfCard}")

            builder.setPositiveButton("Nice") { dialog, which ->
                player2PullCard(
                    player2Status,
                    currentDeck,
                    pulledCardp1,
                    pulledCardp2,
                    i,
                    pullCard, pleaseWait
                )
            }

            builder.show()
            currentDeck.remove(currentDeck[i])


        }
        builder.setNegativeButton("Pass") { dialog, which ->
            player2PullCard(
                player2Status,
                currentDeck,
                pulledCardp1,
                pulledCardp2,
                i,
                pullCard,
                pleaseWait
            )
        }
        val dialog = builder.create()
        dialog.show()
    }

    fun player2PullCard(
        player2Status: TextView,
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        i: Int, pullCard: Button,
        pleaseWait: FrameLayout
    ) {
        // val player2Status = findViewById<TextView>(R.id.player2Status)
        pullCard.isEnabled = false
        player2Status.text = "Thinking..."
        val snackbar = Snackbar.make(pleaseWait, "Computer is thinking!", Snackbar.LENGTH_SHORT)

        //app keeps crashing with code below
//        val snackbarView = snackbar.view
//        val params = snackbarView.layoutParams as CoordinatorLayout.LayoutParams
//        params.gravity = Gravity.CENTER
//        snackbarView.layoutParams = params
        snackbar.show()
        Handler(Looper.getMainLooper()).postDelayed({
            player2Status.text = "Waiting for you"
            Snackbar.make(pleaseWait, "Computer is done thinking!", Snackbar.LENGTH_SHORT).show()
            pullCard.isEnabled = true

        }, 1000)





        currentDeck.remove(currentDeck[i])
        if (currentDeck[i].suit == "hearts") {
            bothPlayersCards[4]++
        }
        if (currentDeck[i].suit == "diamonds") {
            bothPlayersCards[5]++
        }
        if (currentDeck[i].suit == "spades") {
            bothPlayersCards[6]++
        }
        if (currentDeck[i].suit == "clubs") {
            bothPlayersCards[7]++
        }
        firstPlayerTurn = true
        pulledCardp1.text =
            "Clubs: $bothPlayersCards[3],\nSpades:  $ bothPlayersCards[2],\nDiamonds: $bothPlayersCards[1], \nHearts: $bothPlayersCards[0]"
        Log.d(
            "!!!!",
            "Clubs: $bothPlayersCards[7], Spades: $bothPlayersCards[6],Diamonds: $bothPlayersCards[5],Hearts:  $bothPlayersCards[4]"
        )
            .toString()

        //   Snackbar.make(pleaseWait, "Computer is done thinking!", Snackbar.LENGTH_SHORT).show()

        pulledCardp2.text =
            "Has ${bothPlayersCards[7] + bothPlayersCards[6] + bothPlayersCards[5]+ bothPlayersCards[4]} card(s)"
        mainActivity.checkWin(currentDeck, bothPlayersCards)

    }
}