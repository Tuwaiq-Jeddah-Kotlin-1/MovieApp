package com.tuwaiq.movieapp

import android.app.Activity
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.tuwaiq.movieapp.databinding.ActivityMainBinding
import com.tuwaiq.movieapp.notification.MovieNotificationRepo
import com.tuwaiq.movieapp.utils.SettingUtil
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var setting: SettingUtil

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //save and set language
        setting = SettingUtil(this)
        sharedPreferences = getSharedPreferences("Settings", Activity.MODE_PRIVATE)
        val language = sharedPreferences.getString("MY_LANG", "")!!
        setting.saveLang(language)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navController = navHostFragment.findNavController()
        val bottomNavView = findViewById<BottomNavigationView>(R.id.nav_bottom)
        /*val appBarConfiguration = AppBarConfiguration.Builder(
            R.id.nav_movie,
            R.id.nav_favorite,
            R.id.nav_profile,
            R.id.nav_settings
        ).build()*/

        setupActionBarWithNavController(navController)
        binding.apply {
            navBottom.setupWithNavController(navController)
        }
        bottomNavView.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.sign_in -> {
                    bottomNavView.visibility = View.GONE
                }
                R.id.sign_up -> {
                    bottomNavView.visibility = View.GONE
                }
                R.id.forgot_pass -> {
                    bottomNavView.visibility = View.GONE
                }
                else -> {
                    bottomNavView.visibility = View.VISIBLE
                }
            }
        }
        // Notification
        MovieNotificationRepo().myNotification(this)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}