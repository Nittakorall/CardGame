package com.example.cardgame

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    var realPlayer = Player(this, this, "Player1", 0, 0)
    var currentDeck = arrayListOf<Card>() // available cards
    lateinit var pullCard: Button
    lateinit var pulledCardp1: TextView
    lateinit var pulledCardp2: TextView
    lateinit var rulesButton: Button
    lateinit var pleaseWait: FrameLayout
    lateinit var player2Status: TextView
    lateinit var player1Name: TextView
    lateinit var bothPlayersCardsInMain: ArrayList<Int> // arrayList of 8 ints, each for every suit
    lateinit var startOver : Button

    override fun onCreate(savedInstanceState: Bundle?) {


        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.winner)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        pullCard = findViewById<Button>(R.id.pullCard)
        pulledCardp1 = findViewById<TextView>(R.id.pulledCardp1)
        pulledCardp2 = findViewById<TextView>(R.id.pulledCardp2)
        pleaseWait = findViewById<FrameLayout>(R.id.pleaseWait)
        rulesButton = findViewById<Button>(R.id.rulesButton)
        player2Status = findViewById<TextView>(R.id.player2Status)
        player1Name = findViewById<TextView>(R.id.Player1Name)// get name before creating
startOver = findViewById(R.id.startOver)

        nameGetter { name ->
            realPlayer.name = name
            player1Name.text = name///functional
        }

        initializeGame()
    }

    fun initializeGame() {
        decksCreate(currentDeck) // adds all cards in the game
        bothPlayersCardsInMain = realPlayer.bothPlayersCardsFunction() // should be an array with eight zeros
Log.d("SOUT!", bothPlayersCardsInMain.toString())
        pulledCardp1.text =
        "Clubs: " + bothPlayersCardsInMain[3] + " ,\nSpades: " + bothPlayersCardsInMain[2] + ",\nDiamonds: "+bothPlayersCardsInMain[1] +" \nHearts: "+bothPlayersCardsInMain[0]
        pulledCardp2.text =
            "Has ${bothPlayersCardsInMain[7] + bothPlayersCardsInMain[6] + bothPlayersCardsInMain[5]+ bothPlayersCardsInMain[4]} card(s)"
        pullCard.setOnClickListener {
            realPlayer.pullCard(
                player2Status,
                currentDeck,
                pulledCardp1,
                pulledCardp2,
                pullCard,
                pleaseWait, bothPlayersCardsInMain
            )
        }
        rulesButton.setOnClickListener {  //functional
            if (rulesButton.text == "Rules") {
                showRules(rulesButton, pullCard) //disable button under
            } else {
                hideRules(rulesButton, pullCard)
            }
        }
startOver.setOnClickListener {
    startOver()
}
    }

fun startOver() {
    val builder = AlertDialog.Builder(this)
    builder.setTitle("Do you want to start over?")
    builder.setPositiveButton("Yes") { dialog, _ ->
        initializeGame()
    }
    builder.setNegativeButton("No") {dialog, _ ->
       dialog.dismiss()
    }
    builder.show()

}
    fun nameGetter(callback: (String) -> Unit) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Hello there! What's your name?")
        val input = EditText(this)
        builder.setView(input)
        builder.setPositiveButton("There you go") { dialog, _ ->
            val name = input.text.toString()
            callback(name)
        }
        builder.show()

    }

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
        currentDeck: ArrayList<Card>,
        bothPlayersCardsInMain: ArrayList<Int>

    ) {
        var i: Int
        if (currentDeck.size == 0) {
            //sout "noone wins"
            var intent = Intent(this, WinningActivity::class.java)
            i = 0
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }
        if (bothPlayersCardsInMain[3] == 3 || bothPlayersCardsInMain[0] == 3 || bothPlayersCardsInMain[2] == 3 || bothPlayersCardsInMain[1] == 3) {

            i = 1
            var intent = Intent(this, WinningActivity::class.java)
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }
        if (bothPlayersCardsInMain[7] == 3 || bothPlayersCardsInMain[4] == 3 || bothPlayersCardsInMain[6] == 3 || bothPlayersCardsInMain[5] == 3) {

            i = 2
            var intent = Intent(this, WinningActivity::class.java)
            intent.putExtra("whoWin", i)
            startActivity(intent)
        }

    }

    override fun onResume() {
        super.onResume()
        Log.d("SOUT)", "Henlo")
        initializeGame()
    }
}

