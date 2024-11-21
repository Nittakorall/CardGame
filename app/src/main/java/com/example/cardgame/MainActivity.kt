package com.example.cardgame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.FrameLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

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
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.winner)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val pullCard = findViewById<Button>(R.id.pullCard)
        var pulledCardp1 = findViewById<TextView>(R.id.pulledCardp1)
        var pulledCardp2 = findViewById<TextView>(R.id.pulledCardp2)
        val pleaseWait = findViewById<FrameLayout>(R.id.pleaseWait)
        var currentDeck = arrayListOf<Card>()
        var fullDeck = decksCreate(currentDeck) //What's that?
        val rulesButton = findViewById<Button>(R.id.rulesButton)
        rulesButton.setOnClickListener {
            if (rulesButton.text == "Rules") {
                showRules(rulesButton, pullCard)
            } else {
                hideRules(rulesButton, pullCard)
            }
        }


        pullCard.setOnClickListener {
            pullCard(currentDeck, pulledCardp1, pulledCardp2, pullCard, pleaseWait)

        }
    }

    //need main method that starts other methods when playing one more time and put in in onResume()29:16
    fun showRules(rulesButton: Button, pullCard: Button) {
        pullCard.isEnabled = false
        val rulesFragment = RulesFragment()
        val transaction = supportFragmentManager.beginTransaction()
        transaction.add(R.id.rulesFragment, rulesFragment, "rules")
        transaction.commit()
        rulesButton.text = "Hide"
    }

    fun hideRules(rulesButton: Button, pullCard: Button) {
        pullCard.isEnabled = true
        val rulesFragment = supportFragmentManager.findFragmentByTag("rules")
        val transaction = supportFragmentManager.beginTransaction()
        if (rulesFragment != null) {
            transaction.remove(rulesFragment)
            transaction.commit()
            rulesButton.text = "Rules"
        }
    }

    fun pullCard(
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        pullCard: Button,
        pleaseWait: FrameLayout

    ) {
        var i = (0..<currentDeck.size).random()
        if (firstPlayerTurn) {
            player1PullCard(currentDeck, pulledCardp1, pulledCardp2, i, pullCard, pleaseWait)
        }
    }

    fun player2PullCard(
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        i: Int, pullCard: Button,
        pleaseWait: FrameLayout
    ) {
        val player2Status = findViewById<TextView>(R.id.player2Status)
        pullCard.isEnabled = false
        player2Status.text = "Thinking..."
        Snackbar.make(pleaseWait, "Computer is thinking!", Snackbar.LENGTH_SHORT).show()
        Handler(Looper.getMainLooper()).postDelayed({
            player2Status.text = "Waiting for you"
            Snackbar.make(pleaseWait, "Computer is done thinking!", Snackbar.LENGTH_SHORT).show()
            pullCard.isEnabled = true

        }, 3000)





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
        pulledCardp1.text =
            "Clubs: $cardsOfClubsP1,\nSpades:  $cardsOfSpadesP1,\nDiamonds: $cardsOfDiamondsP1, \nHearts: $cardsOfHeartsP1"

        Log.d(
            "!!!!",
            "Clubs: ${cardsOfClubsP2}, Spades: ${cardsOfSpadesP2},Diamonds: ${cardsOfDiamondsP2},Hearts:  ${cardsOfHeartsP2}"
        )
            .toString()

    //   Snackbar.make(pleaseWait, "Computer is done thinking!", Snackbar.LENGTH_SHORT).show()

        pulledCardp2.text =
            "Has ${cardsOfHeartsP2 + cardsOfClubsP2 + cardsOfDiamondsP2 + cardsOfSpadesP2} card(s)"
        checkWin(currentDeck)

    }

    fun player1PullCard(
        currentDeck: ArrayList<Card>,
        pulledCardp1: TextView,
        pulledCardp2: TextView,
        i: Int, pullCard: Button,
        pleaseWait: FrameLayout
    ) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Time to pull a card?")
        builder.setMessage("Rank of the card is ${currentDeck[i].numberOfCard} ")

        builder.setPositiveButton("Yes") { dialog, which ->
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
            Log.d("))))", currentDeck[i].number)
            checkWin(currentDeck)
            pulledCardp1.text =
                "Clubs: $cardsOfClubsP1,\nSpades:  $cardsOfSpadesP1,\nDiamonds: $cardsOfDiamondsP1, \nHearts: $cardsOfHeartsP1"
            Log.d(
                "!!!!",
                "Clubs: ${cardsOfClubsP2}, Spades: ${cardsOfSpadesP2},Diamonds: ${cardsOfDiamondsP2},Hearts:  ${cardsOfHeartsP2}"
            )
                .toString()

            val builder = AlertDialog.Builder(this)
            builder.setTitle("You got ${currentDeck[i].suit}  ${currentDeck[i].numberOfCard}")

            builder.setPositiveButton("Nice") { dialog, which ->
                player2PullCard(
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
            player2PullCard(currentDeck, pulledCardp1, pulledCardp2, i, pullCard, pleaseWait)
        }
        val dialog = builder.create()
        dialog.show()
    }


    fun decksCreate(currentDeck: ArrayList<Card>): ArrayList<Card> {

        val hearts1 = Card("hearts", "1", 1)
        val hearts2 = Card("hearts", "2", 2)
        val hearts3 = Card("hearts", "3", 3)
        val hearts4 = Card("hearts", "4", 4)
        val hearts5 = Card("hearts", "5", 5)
        val hearts6 = Card("hearts", "6", 6)
        val hearts7 = Card("hearts", "7", 7)
        val hearts8 = Card("hearts", "8", 8)
        val hearts9 = Card("hearts", "9", 9)
        val hearts10 = Card("hearts", "10", 10)

        currentDeck.add(hearts1)
        currentDeck.add(hearts2)
        currentDeck.add(hearts3)
        currentDeck.add(hearts4)
        currentDeck.add(hearts5)
        currentDeck.add(hearts6)
        currentDeck.add(hearts7)
        currentDeck.add(hearts8)
        currentDeck.add(hearts9)
        currentDeck.add(hearts10)

        val diamonds1 = Card("diamonds", "1", 1)
        val diamonds2 = Card("diamonds", "2", 2)
        val diamonds3 = Card("diamonds", "3", 3)
        val diamonds4 = Card("diamonds", "4", 4)
        val diamonds5 = Card("diamonds", "5", 5)
        val diamonds6 = Card("diamonds", "6", 6)
        val diamonds7 = Card("diamonds", "7", 7)
        val diamonds8 = Card("diamonds", "8", 8)
        val diamonds9 = Card("diamonds", "9", 9)
        val diamonds10 = Card("diamonds", "10", 10)
        currentDeck.add(diamonds1)
        currentDeck.add(diamonds2)
        currentDeck.add(diamonds3)
        currentDeck.add(diamonds4)
        currentDeck.add(diamonds5)
        currentDeck.add(diamonds6)
        currentDeck.add(diamonds7)
        currentDeck.add(diamonds8)
        currentDeck.add(diamonds9)
        currentDeck.add(diamonds10)

        val spades1 = Card("spades", "1", 1)
        val spades2 = Card("spades", "2", 2)
        val spades3 = Card("spades", "3", 3)
        val spades4 = Card("spades", "4", 4)
        val spades5 = Card("spades", "5", 5)
        val spades6 = Card("spades", "6", 6)
        val spades7 = Card("spades", "7", 7)
        val spades8 = Card("spades", "8", 8)
        val spades9 = Card("spades", "9", 9)
        val spades10 = Card("spades", "10", 10)
        currentDeck.add(spades1)
        currentDeck.add(spades2)
        currentDeck.add(spades3)
        currentDeck.add(spades4)
        currentDeck.add(spades5)
        currentDeck.add(spades6)
        currentDeck.add(spades7)
        currentDeck.add(spades8)
        currentDeck.add(spades9)
        currentDeck.add(spades10)

        val clubs1 = Card("clubs", "1", 1)
        val clubs2 = Card("clubs", "2", 2)
        val clubs3 = Card("clubs", "3", 3)
        val clubs4 = Card("clubs", "4", 4)
        val clubs5 = Card("clubs", "5", 5)
        val clubs6 = Card("clubs", "6", 6)
        val clubs7 = Card("clubs", "7", 7)
        val clubs8 = Card("clubs", "8", 8)
        val clubs9 = Card("clubs", "9", 9)
        val clubs10 = Card("clubs", "10", 10)
        currentDeck.add(clubs1)
        currentDeck.add(clubs2)
        currentDeck.add(clubs3)
        currentDeck.add(clubs4)
        currentDeck.add(clubs5)
        currentDeck.add(clubs6)
        currentDeck.add(clubs7)
        currentDeck.add(clubs8)
        currentDeck.add(clubs9)
        currentDeck.add(clubs10)

        return currentDeck
    }


    fun checkWin(
        // should start another activity
        currentDeck: ArrayList<Card>,


        ) {
        var i: Int
        if (currentDeck.size == 0) {
            //sout "noone wins"
            var intent = Intent(this, WinningActivity::class.java)
            i = 0
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }
        if (cardsOfClubsP1 == 3 || cardsOfHeartsP1 == 3 || cardsOfSpadesP1 == 3 || cardsOfDiamondsP1 == 3) {

            i = 1
            var intent = Intent(this, WinningActivity::class.java)
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }
        if (cardsOfClubsP2 == 3 || cardsOfHeartsP2 == 3 || cardsOfSpadesP2 == 3 || cardsOfDiamondsP2 == 3) {

            i = 2
            var intent = Intent(this, WinningActivity::class.java)
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }

    }
}