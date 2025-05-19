package com.example.fitnessapp

import android.os.Bundle
import android.view.MenuItem
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import com.example.fitpal.HomeFragment
import com.example.fitpal.ProfileFragment
import com.example.fitpal.WorkoutsFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Set up the bottom navigation view
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        // Set the font for the bottom navigation menu
        setBottomNavFont(bottomNav)

        // Set the initial fragment
        val initialFragment = HomeFragment()
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragment_container, initialFragment)
            .commit()

        /** Set the onNavigationItemSelectedListener
        This is done by getting the fragment that corresponds to the selected item and
        replacing the current fragment with it **/
        bottomNav.setOnNavigationItemSelectedListener { item ->
            val selectedFragment = getFragmentForItem(item)
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, selectedFragment)
                .commit()
            true
        }
    }

    private fun setBottomNavFont(bottomNav: BottomNavigationView) {
        // Get the font for the poppins typeface
        val poppinsTypeface = ResourcesCompat.getFont(this, R.font.poppins)

        // Get the menu view from the bottom navigation view
        val menuView = bottomNav.getChildAt(0) as? ViewGroup

        // If the menu view is not null, loop through each item in the menu view
        menuView?.let { menu ->
            for (i in 0 until menu.childCount) {
                // Get the current item
                val item = menu.getChildAt(i)

                // If the item is a ViewGroup (i.e. it has children), loop through those children
                if (item is ViewGroup) {
                    for (j in 0 until item.childCount) {
                        // Get the child
                        val child = item.getChildAt(j)

                        // If the child is a TextView (i.e. it has text), set its typeface to the poppins typeface
                        if (child is TextView) {
                            child.typeface = poppinsTypeface
                        }
                    }
                }
            }
        }
    }

    private fun getFragmentForItem(item: MenuItem): Fragment {
        // Return the fragment that corresponds to the selected item
        return when (item.itemId) {
            R.id.nav_home -> HomeFragment()
            R.id.nav_workouts -> WorkoutsFragment()
            R.id.nav_profile -> ProfileFragment()
            else -> HomeFragment()
        }
    }
}

