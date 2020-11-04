package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.smarttoolfactory.tutorial3_1transitions.R


@Suppress("DEPRECATION")
class Activity2_6FragmentExpandCollapseTransitions : AppCompatActivity() {

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_6_rv_transitions_expand)
        title = getString(R.string.activity2_6)

        val rootView = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)


        hideSystemUI(rootView, true)
        
        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        bottomNavigationView.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)
            insets
        }

        fab = findViewById<FloatingActionButton>(R.id.fab)

        val navController = findNavController(R.id.nav_host_fragment)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.fragment2_6ExpandCollapseList -> {
                    fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
                }

                R.id.fragment2_6ExpandCollapseDetails -> {
                    fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
                }
            }
        }

        fab.setOnClickListener {

            if (navController.currentDestination?.id == R.id.fragment2_6ExpandCollapseList) {
                Toast.makeText(applicationContext, "Compose", Toast.LENGTH_SHORT).show()
            } else if (navController.currentDestination?.id == R.id.fragment2_6ExpandCollapseDetails) {
                onBackPressed()
            }

        }
    }

    private fun hideSystemUI(view: View = window.decorView, isFullScreen: Boolean) {

        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        if (isFullScreen) {
            // hide status bar
            uiOptions = (
                    uiOptions
//                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            // Views can use nav bar space if set
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            // Removes Status bar
//                            or View.SYSTEM_UI_FLAG_FULLSCREEN
//                            // hide nav bar
//                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        }

        view.systemUiVisibility = uiOptions
    }
}