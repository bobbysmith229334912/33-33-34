package com.hardcoreamature.a33_33_34

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        // Check if user is not signed in
        if (auth.currentUser == null) {
            // User not signed in, redirect to AuthActivity
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
            finish() // Finish MainActivity to prevent returning to it
            return
        }

        setContentView(R.layout.activity_main)
        setupTechLinkButton()
        setupAuthActivityButton()
        setupMyDreamLifeButton()
    }

    private fun setupTechLinkButton() {
        val buttonTechLink: Button = findViewById(R.id.buttonTechLink)
        buttonTechLink.setOnClickListener {
            animateButton(buttonTechLink)
            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.tradelinemastermind.com"))
            startActivity(browserIntent)
        }
    }

    private fun setupAuthActivityButton() {
        val buttonAuthActivity: Button = findViewById(R.id.buttonAuthActivity)
        buttonAuthActivity.setOnClickListener {
            animateButton(buttonAuthActivity)
            val intent = Intent(this, AuthActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupMyDreamLifeButton() {
        val buttonMyDreamLife: Button = findViewById(R.id.buttonMyDreamLife)
        buttonMyDreamLife.setOnClickListener {
            animateButton(buttonMyDreamLife)
            val intent = Intent(this, BudgetFormActivity::class.java)
            startActivity(intent)
        }
    }

    private fun animateButton(button: Button) {
        val animation = AnimationUtils.loadAnimation(this, R.anim.button_click)
        button.startAnimation(animation)
    }
}
