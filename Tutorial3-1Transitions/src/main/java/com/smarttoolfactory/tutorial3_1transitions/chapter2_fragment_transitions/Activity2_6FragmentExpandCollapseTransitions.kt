package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.core.view.updatePadding
import androidx.navigation.findNavController
import androidx.transition.Transition
import androidx.transition.TransitionListenerAdapter
import androidx.transition.TransitionManager
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.transition.MaterialElevationScale
import com.google.android.material.transition.MaterialFadeThrough
import com.google.android.material.transition.MaterialSharedAxis
import com.smarttoolfactory.tutorial3_1transitions.R


@Suppress("DEPRECATION")
class Activity2_6FragmentExpandCollapseTransitions : AppCompatActivity() {

    val viewModel: ExpandCollapseViewModel by viewModels<ExpandCollapseViewModel>()

    private lateinit var fab: FloatingActionButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_6_rv_transitions_expand)
        title = getString(R.string.activity2_6)


        val rootView = findViewById<CoordinatorLayout>(R.id.coordinatorLayout)

        /*
            🔥 Use root View of your Activity, using decorView prevents
            android:windowLightStatusBar from changing icon tint(black) for true flag
         */
        hideSystemUI(rootView, true)

        val bottomAppbar = findViewById<BottomAppBar>(R.id.bottom_app_bar)

        val bottomNavigationView = findViewById<BottomNavigationView>(R.id.bottom_navigation)

        val navHostFragment = findViewById<View>(R.id.nav_host_fragment)

        bottomNavigationView.setOnNavigationItemSelectedListener {

            navHostFragment.visibility = View.INVISIBLE

            val transition: Transition = when (it.itemId) {
                R.id.item_home_fragment -> {
                    MaterialFadeThrough()
                }
                R.id.item_dashboard -> {
                    MaterialSharedAxis(MaterialSharedAxis.X, true)
                }
                R.id.item_notification -> {
                    MaterialSharedAxis(MaterialSharedAxis.Y, true)
                }
                else -> {
                    MaterialElevationScale(true)
                }
            }

            /*
                🔥 This is deliberately slow to show how Material Transitions work.
                MaterialFadeThrough, MaterialElevationScale, and MaterialSharedAxis with Z
                almost look like each other
             */

            transition.apply {
                duration = 900
            }
                .addListener(object : TransitionListenerAdapter() {

                    override fun onTransitionEnd(transition: Transition) {
                        super.onTransitionEnd(transition)
                        Toast.makeText(
                            applicationContext,
                            "Activity ${transition::class.java.simpleName} transition end",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                })

            TransitionManager.beginDelayedTransition(rootView, transition)
            navHostFragment.visibility = View.VISIBLE
            true
        }

        bottomNavigationView.setOnApplyWindowInsetsListener { view, insets ->
            view.updatePadding(bottom = 0)

            insets
        }

        fab = findViewById(R.id.fab)

        val navController = findNavController(R.id.nav_host_fragment)

        val handler = Handler()
        handler.postDelayed({

        }, 1000)


        navController.addOnDestinationChangedListener { controller, destination, arguments ->

            when (destination.id) {
                R.id.fragment2_6ExpandCollapseList -> {
                    fab.setImageState(intArrayOf(-android.R.attr.state_activated), true)
                    fab.show()
                    bottomAppbar.performShow()
                }

                R.id.fragment2_6ExpandCollapseDetails -> {
                    fab.setImageState(intArrayOf(android.R.attr.state_activated), true)
                }

                R.id.fragment2_6Compose -> {


                }
            }
        }

        fab.setOnClickListener {
            if (navController.currentDestination?.id == R.id.fragment2_6ExpandCollapseList) {
                Toast.makeText(applicationContext, "Compose", Toast.LENGTH_SHORT).show()


                // Set a custom animation for showing and hiding the FAB
                fab.setShowMotionSpecResource(R.animator.fab_show)
                fab.setHideMotionSpecResource(R.animator.fab_hide)
                bottomAppbar.performHide()
                fab.hide()

                findNavController(R.id.nav_host_fragment).navigate(R.id.action_fragment2_6ExpandCollapseList_to_fragment2_6Compose)
            } else if (navController.currentDestination?.id == R.id.fragment2_6ExpandCollapseDetails) {
                onBackPressed()

            }
        }
    }

    private fun hideSystemUI(view: View = window.decorView, isFullScreen: Boolean) {

        var uiOptions = View.SYSTEM_UI_FLAG_LAYOUT_STABLE

        if (isFullScreen) {
            uiOptions = (
                    uiOptions
//                            or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                            // Views can use nav bar space if set
                            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                            // Removes Status bar
//                            or View.SYSTEM_UI_FLAG_FULLSCREEN
//                            // Removes nav bar
//                            or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    )
        }

        view.systemUiVisibility = uiOptions
    }
}