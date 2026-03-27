package com.example.nav

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.example.nav.databinding.MainActivityBinding
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        setSupportActionBar(binding.toolbar)
        setupNavHost()
        setupDestinationChangedListener()
    }

    //Configuracion NavHost
    private fun setupNavHost() {
        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setupActionBarWithNavController(navController)
    }

    private fun setupDestinationChangedListener() {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            handleDestinationChange(destination.id)
            invalidateOptionsMenu()
        }
    }

    private fun handleDestinationChange(destinationId: Int) {
        val destinationConfig = mapOf(
            R.id.loginFragment to Pair("Iniciar Sesión", false),
            R.id.homeFragment to Pair("Home", false),
            R.id.registerFragment to Pair("Registro", true),
            R.id.successFragment to Pair("", false),
        )
        val config = destinationConfig[destinationId]

        supportActionBar?.title = config?.first ?: getString(R.string.app_name)
        supportActionBar?.setDisplayHomeAsUpEnabled(config?.second == true)
    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actions_search->{
                true
            }
            R.id.actions_settings->{
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.actions_logout->{
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun logout() {
        // 1. Cerrar sesión en Firebase
        FirebaseAuth.getInstance().signOut()
        
        // 2. Navegar al Login y limpiar el stack
        navController.navigate(R.id.action_homeFragment_to_loginFragment)
        
        Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show()
    }
}
