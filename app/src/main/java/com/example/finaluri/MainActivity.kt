package com.example.finaluri

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var bottomNav : BottomNavigationView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadFragment(Fragment_login())
        bottomNav = findViewById(R.id.bottomNavigationView)
        bottomNav.setOnItemSelectedListener {
            when (it.itemId) {
                R.id.Account -> {
                    loadFragment(Fragment_login())
                    true
                }
                R.id.Game -> {
                    loadFragment(Fragment_game())
                    true
                }
                R.id.SignUp -> {
                    loadFragment(Fragment_SignUp())
                    true
                }
                else -> {throw IllegalStateException("Fragment position is not correct")}
            }
        }
    }
    private  fun loadFragment(fragment: Fragment){
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container,fragment)
        transaction.commit()
    }

}
