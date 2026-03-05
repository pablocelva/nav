package com.example.nav

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nav.databinding.MainActivityBinding
import androidx.navigation.ui.setupActionBarWithNavController

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        //setSupportActionBar(binding.toolbar)
        setupNavHost()
        setContentView(binding.root)
    }

    //Configuracion NavHost
    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }
    //configurar el actionbar
//    private fun configurateActionBar(title: String,displayHomeAsUpEnabled: Boolean) {
//        supportActionBar?.title = title
//        supportActionBar?.setDisplayHomeAsUpEnabled(displayHomeAsUpEnabled)
//        supportActionBar?.setDisplayShowHomeEnabled(displayHomeAsUpEnabled)
//
//    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}