package com.salesianostriana.apidexandroid


import android.content.Context
import android.content.Intent
import android.content.res.ColorStateList
import android.content.res.Resources
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.salesianostriana.apidexandroid.ui.login.LoginActivity
import com.salesianostriana.apidexandroid.ui.pokedex.FilterActivity


class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)


        val drawerLayout: DrawerLayout = findViewById(R.id.drawer_layout)
        val navView: NavigationView = findViewById(R.id.nav_view)
        navView.itemIconTintList = null
        navView.itemTextColor = getColorStateList(R.color.nav_item_text_color)
        /*navView.itemTextColor = resources.getColorStateList(R.drawable.nav_item_text_color)*/
        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_pokedex, R.id.nav_favoritos, R.id.nav_capturados, R.id.nav_username, R.id.nav_equipo
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        val sharedPref = getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
        token = sharedPref.getString("token", "")!!

    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_filter -> {
                val intent = Intent(this, FilterActivity::class.java)
                this.startActivity(intent)
                true
            }

            R.id.action_logout -> {
                logout()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun logout(){
        if (token.isEmpty()) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        } else {
            val sharedPref = getSharedPreferences("FILE_PREFERENCES", Context.MODE_PRIVATE)
            with(sharedPref.edit()){
                remove("token")
                commit()
            }
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

}