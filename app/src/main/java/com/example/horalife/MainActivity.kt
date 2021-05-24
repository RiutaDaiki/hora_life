package com.example.horalife

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView

//rgb(210, 183, 58)

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.nav_example, R.id.nav_diary, R.id.nav_you))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onBackPressed() {
        if (!findNavController(R.id.nav_host_fragment).popBackStack()){
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean =
            findNavController(R.id.nav_host_fragment).navigateUp()

}


