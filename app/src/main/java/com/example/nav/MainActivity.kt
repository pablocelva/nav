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

class MainActivity : AppCompatActivity() {
    private lateinit var binding: MainActivityBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setSupportActionBar(binding.toolbar)
        setupNavHost()
        setupDestinationChangedListener()
        setContentView(binding.root)
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
            R.id.homeFragment to Pair("Home", true),
            R.id.registerFragment to Pair("Registro", true)
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
        //todo add funtions search
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.actions_search->{
                //todo actions button
                true
            }
            R.id.actions_settings->{
                //implementar toast toast
                Toast.makeText(this, "Settings", Toast.LENGTH_SHORT).show()

                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}