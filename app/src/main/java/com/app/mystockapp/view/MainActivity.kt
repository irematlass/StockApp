package com.app.mystockapp.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.app.mystockapp.R
import com.google.android.material.navigation.NavigationView
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_main.view.*
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() , NavigationView.OnNavigationItemSelectedListener{

    @Inject
    lateinit var fragmentFactory: StockFragmentFactory

    private lateinit var navController :NavController

override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        supportFragmentManager.fragmentFactory = fragmentFactory
        setContentView(R.layout.activity_main)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment) as NavHostFragment
         navController = navHostFragment.navController

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if(destination.id == R.id.homeFragment) {
                actionBar?.hide()

            } else {
                actionBar?.show()
            }
        }
        setupDrawerLayout()
    }
    private fun setupDrawerLayout() {

        nav_view.setNavigationItemSelectedListener(this)
         val appBarConfiguration= AppBarConfiguration(setOf(R.id.stockListFragment),drawer_layout)
        NavigationUI.setupActionBarWithNavController(this, navController,appBarConfiguration)

    }
    override fun onSupportNavigateUp(): Boolean {
        val appBarConfiguration= AppBarConfiguration(setOf(R.id.stockListFragment),drawer_layout)
        return NavigationUI.navigateUp(navController, appBarConfiguration)
    }
    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val fragment =StockListFragment(fragmentFactory.stockRecyclerAdapter)

        when (item.itemId) {

            R.id.nav_all -> {
                fragment.arguments = bundleOf("period" to "all")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()

            }

            R.id.nav_rising -> {
                fragment.arguments = bundleOf("period" to "increasing")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()

            }

            R.id.nav_falling -> {
                fragment.arguments = bundleOf("period" to "decreasing")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()
            }

            R.id.nav_100 -> {
                fragment.arguments = bundleOf("period" to "volume30")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()
            }

            R.id.nav_50 -> {
                fragment.arguments = bundleOf("period" to "volume50")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()
            }

            R.id.nav_30 -> {
                fragment.arguments = bundleOf("period" to "volume100")
                supportFragmentManager.beginTransaction().replace(R.id.fragment, fragment)
                    .commit()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}