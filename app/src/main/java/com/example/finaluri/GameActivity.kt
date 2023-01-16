package com.example.finaluri

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.finaluri.databinding.ActivityGameBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase


class GameActivity : AppCompatActivity() {

    enum class Turn { CROSS, NOUGHT }


    private var turnFirst = Turn.CROSS
    private var turnCurrent  = Turn.CROSS
    private var scoreCrosses = 0
    private var scoreNought = 0
    private var buttonsList = mutableListOf<Button>()

    private lateinit var binding: ActivityGameBinding
    private lateinit var dbRef: DatabaseReference
    private lateinit var btn: Button
    private lateinit var emailTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGameBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initBoard()

        dbRef = FirebaseDatabase.getInstance("https://finaluri-4a804-default-rtdb.europe-west1.firebasedatabase.app").getReference("users")

        btn = binding.saveBtn


        //receiving email from login fragment
        val email = intent.extras?.getString("email")
        emailTV = binding.emailTV
        emailTV.text = email.toString()
        val userid = intent.extras?.getString("userid")

        btn.setOnClickListener {
            saveData()
        }

    }

    private fun saveData() {
        //getting the values of score textViews
        val crossScoreText = binding.crossScore.text.toString()
        val noughtScoreText = binding.noughtScore.text.toString()
        //checking the scores
        if (crossScoreText == "X: 0" && noughtScoreText == "0: 0" ) {
            Toast.makeText(this, "both scores are zero. Can't save!",
                Toast.LENGTH_SHORT).show()
        }else if (emailTV.text.toString() == "null"){

            Toast.makeText(this, "not signed in", Toast.LENGTH_SHORT).show()

        }else{
            //removing "@" and "." from email for database path
            val email = intent.extras?.getString("email").toString()
            val email1 = email.split("@",".").joinToString(separator = "")

            val user = DataModel(email1, crossScoreText, noughtScoreText)

            dbRef.child(email1).setValue(user).addOnCompleteListener{
                Toast.makeText(this, "saved successfully", Toast.LENGTH_SHORT).show()
            }

        }
    }

    //board initialization
    private fun initBoard()
    {
        buttonsList.add(binding.square11)
        buttonsList.add(binding.square12)
        buttonsList.add(binding.square13)
        buttonsList.add(binding.square21)
        buttonsList.add(binding.square22)
        buttonsList.add(binding.square23)
        buttonsList.add(binding.square31)
        buttonsList.add(binding.square32)
        buttonsList.add(binding.square33)
    }


    @SuppressLint("SetTextI18n")
    fun boardClicked(view: View)
    {
        if(view !is Button)
            return
        addToBoard(view)

        if(checkForVictory(NOUGHT))
        {
            scoreNought++
            binding.noughtScore.text = ("0: $scoreNought")
            result("0 Won!")
        }
        else if(checkForVictory(CROSS))
        {
            scoreCrosses++
            result("X Won!")
            binding.crossScore.text = ("X: $scoreCrosses")

        }

        else if(fullBoard())
        {
            result("Draw")
        }

    }

    private fun checkForVictory(s: String): Boolean
    {
        //Victory for horizontal line
        //top line
        if(match(binding.square11,s) && match(binding.square12,s) && match(binding.square13,s))
            return true
        //middle line
        if(match(binding.square21,s) && match(binding.square22,s) && match(binding.square23,s))
            return true
        //bottom line
        if(match(binding.square31,s) && match(binding.square32,s) && match(binding.square33,s))
            return true

        //victory for vertical line
        //leftmost line
        if(match(binding.square11,s) && match(binding.square21,s) && match(binding.square31,s))
            return true
        //middle line
        if(match(binding.square12,s) && match(binding.square22,s) && match(binding.square32,s))
            return true
        //rightmost line
        if(match(binding.square13,s) && match(binding.square23,s) && match(binding.square33,s))
            return true

        //Victory for diagonal line

        //top left to bottom right
        if(match(binding.square11,s) && match(binding.square22,s) && match(binding.square33,s))
            return true
        //top right to bottom left
        if(match(binding.square13,s) && match(binding.square22,s) && match(binding.square31,s))
            return true

        return false
    }

    private fun match(button: Button, symbol : String): Boolean = button.text == symbol

    //result message
    private fun result(title: String)
    {
        val message = "\nX: $scoreCrosses\n\n0: $scoreNought"
        AlertDialog.Builder(this).setTitle(title).setMessage(message)
            .setPositiveButton("Reset")
            { _,_ ->
                resetBoard()
            }.setCancelable(false).show()

    }

    private fun resetBoard()
    {
        for(button in buttonsList)
        {
            button.text = ""
        }

        if(turnFirst == Turn.NOUGHT)
            turnFirst = Turn.CROSS
        else if(turnFirst == Turn.CROSS)
            turnFirst = Turn.NOUGHT

        turnCurrent = turnFirst
        setTurnLabel()
    }

    //returns true when the board is full, otherwise returns false
    private fun fullBoard(): Boolean
    {
        for(button in buttonsList)
        {
            if(button.text == "")
                return false
        }
        return true
    }

    //checks if the clicked button has anything in it and adds "X" or "0" depending on the turn
    private fun addToBoard(button: Button)
    {
        if(button.text != "")
            return

        if(turnCurrent == Turn.NOUGHT)
        {
            button.text = NOUGHT
            turnCurrent = Turn.CROSS
        }
        else if(turnCurrent == Turn.CROSS)
        {
            button.text = CROSS
            turnCurrent = Turn.NOUGHT
        }
        setTurnLabel()
    }

    //changes the TextView and displays whose turn it is
    private fun setTurnLabel()
    {
        var turnText = ""
        if(turnCurrent == Turn.CROSS)
            turnText = "Turn $CROSS"
        else if(turnCurrent == Turn.NOUGHT)
            turnText = "Turn $NOUGHT"

        binding.turnTV.text = turnText
    }

    companion object
    {
        const val NOUGHT = "O"
        const val CROSS = "X"
    }

}