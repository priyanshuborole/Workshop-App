package com.application.workshopapp

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.MenuProvider
import androidx.navigation.findNavController
import com.application.workshopapp.data.database.WorkshopSqliteHelper
import com.application.workshopapp.repository.WorkshopRepository
import com.application.workshopapp.utils.Constant
import com.google.firebase.auth.FirebaseAuth

class MainActivity : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var repository: WorkshopRepository
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        firebaseAuth = FirebaseAuth.getInstance()
        val workshopSqliteHelper = WorkshopSqliteHelper(this)
        repository = WorkshopRepository(workshopSqliteHelper)
        sharedPreferences = getSharedPreferences(Constant.SP_NAME, Context.MODE_PRIVATE)
        val actionBar = supportActionBar
        actionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#254e5e")))
        window.statusBarColor = ContextCompat.getColor(this, R.color.green)
        addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.logOut -> {
                        firebaseAuth.signOut()
                        sharedPreferences.edit().putBoolean(Constant.USER_SIGNED_IN,false)?.apply()
                        repository.deleteAppliedWorkshops()
                        Toast.makeText(this@MainActivity,"Logged Out",Toast.LENGTH_SHORT).show()
                        val navController = findNavController(R.id.nav_host_fragment)
                        navController.navigate(R.id.loginFragment)
                        true
                    }
                    else -> {
                        return true
                    }
                }
            }
        })
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu, menu)
//        return true
//    }
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        return when (item.itemId) {
//            R.id.logOut -> {
//                firebaseAuth.signOut()
//                sharedPreferences.edit().putBoolean(Constant.USER_SIGNED_IN,false)?.apply()
//                repository.deleteAppliedWorkshops()
//                Toast.makeText(this,"Logged Out",Toast.LENGTH_SHORT).show()
//                val navController = findNavController(R.id.nav_host_fragment)
//                navController.navigate(R.id.loginFragment)
//
//                true
//            }
//            else -> super.onOptionsItemSelected(item)
//        }
//    }

}