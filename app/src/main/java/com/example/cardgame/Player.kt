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


class Player(
    val mainActivity: MainActivity,
    val myContext: Context, var name: String, val winTimes: Int, val lostTimes: Int
) {

    var firstPlayerTurn: Boolean = true
    var bothPlayersCardsArray = bothPlayersCardsFunction()

    fun bothPlayersCardsFunction(): ArrayList<Int> {// how does it get info??
        val bothPlayersCards = arrayListOf<Int>()
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

        return bothPlayersCards // returns array with all 0
    }

    fun pullCard(
        player2Status: TextView,
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        pullCard: Button,
        pleaseWait: FrameLayout,
        bothPlayersCardsInMain: ArrayList<Int>
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
                pleaseWait, bothPlayersCardsInMain
            )
        }
    }

    fun player1PullCard(
        player2Status: TextView,
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        i: Int, pullCard: Button,
        pleaseWait: FrameLayout,
        bothPlayersCardsInMain: ArrayList<Int>
    ) {

        val builder = AlertDialog.Builder(myContext)
        builder.setTitle("Time to pull a card?")
        builder.setMessage("Rank of the card is ${currentDeck[i].numberOfCard} ")

        builder.setPositiveButton("Yes") { dialog, which ->
            if (currentDeck[i].suit == "hearts") {
                bothPlayersCardsInMain[0]++
            }
            if (currentDeck[i].suit == "diamonds") {
                bothPlayersCardsInMain[1]++
            }
            if (currentDeck[i].suit == "spades") {
                bothPlayersCardsInMain[2]++
            }
            if (currentDeck[i].suit == "clubs") {
                bothPlayersCardsInMain[3]++
            }


            val builder = AlertDialog.Builder(myContext)
            builder.setTitle("You got ${currentDeck[i].suit}  ${currentDeck[i].numberOfCard}")

            builder.setPositiveButton("Nice") { dialog, which ->
                mainActivity.checkWin(currentDeck, bothPlayersCardsInMain)
                pulledCardp1.text =
                    "Clubs: " + bothPlayersCardsInMain[3] + " ,\nSpades: " + bothPlayersCardsInMain[2] + ",\nDiamonds: " + bothPlayersCardsInMain[1] + " \nHearts: " + bothPlayersCardsInMain[0]//shows 0 hearts
              //  TODO("mistake"), shows 0 hearts, and checkwin is bit weird
                player2PullCard(
                    player2Status,
                    currentDeck,
                    pulledCardp1,
                    pulledCardp2,
                    i,
                    pullCard, pleaseWait, bothPlayersCardsInMain

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
                pleaseWait, bothPlayersCardsInMain
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

        pleaseWait: FrameLayout, bothPlayersCardsInMain: ArrayList<Int>

    ) {

        pullCard.isEnabled = false
        player2Status.text = "Thinking..."
        val snackbar = Snackbar.make(pleaseWait, "Computer is thinking!", Snackbar.LENGTH_SHORT)


        snackbar.show()
        Handler(Looper.getMainLooper()).postDelayed({
            player2Status.text = "Waiting for you"
            Snackbar.make(pleaseWait, "Computer is done thinking!", Snackbar.LENGTH_SHORT).show()
            pullCard.isEnabled = true

        }, 1000)



        currentDeck.remove(currentDeck[i])
        if (currentDeck[i].suit == "hearts") {
            bothPlayersCardsInMain[4]++
        }
        if (currentDeck[i].suit == "diamonds") {
            bothPlayersCardsInMain[5]++
        }
        if (currentDeck[i].suit == "spades") {
            bothPlayersCardsInMain[6]++
        }
        if (currentDeck[i].suit == "clubs") {
            bothPlayersCardsInMain[7]++
        }
        firstPlayerTurn = true
        pulledCardp1.text =
            "Clubs: " + bothPlayersCardsInMain[3] + " ,\nSpades: " + bothPlayersCardsInMain[2] + ",\nDiamonds: " + bothPlayersCardsInMain[1] + " \nHearts: " + bothPlayersCardsInMain[0]


        pulledCardp2.text =
            "Has ${bothPlayersCardsInMain[7] + bothPlayersCardsInMain[6] + bothPlayersCardsInMain[5] + bothPlayersCardsInMain[4]} card(s)"
        mainActivity.checkWin(currentDeck, bothPlayersCardsInMain)
        //cardsLeft.text = "Cards left: " + currentDeck.size
    }
}