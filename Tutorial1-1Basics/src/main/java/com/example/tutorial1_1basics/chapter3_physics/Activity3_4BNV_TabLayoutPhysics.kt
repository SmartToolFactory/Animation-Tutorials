package com.example.tutorial1_1basics.chapter3_physics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.dynamicanimation.animation.DynamicAnimation
import androidx.dynamicanimation.animation.FloatPropertyCompat
import androidx.dynamicanimation.animation.SpringAnimation
import androidx.dynamicanimation.animation.SpringForce
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.tutorial1_1basics.R
import com.google.android.material.bottomnavigation.BottomNavigationMenuView
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class Activity3_4BNV_TabLayoutPhysics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity3_4_bnv_tablayout_physics)
        title = getString(R.string.activity3_4)

        setUpTabLayout()


        val floatProperty = object : FloatPropertyCompat<View>("") {

            override fun setValue(view: View, property: Float) {
                view.scaleX = property
                view.scaleY = property
            }

            override fun getValue(view: View): Float {
                return view.scaleX
            }
        }

        val swipeRefreshLayout = findViewById<SwipeRefreshLayout>(R.id.swipeRefreshLayout)

        swipeRefreshLayout.setOnRefreshListener {
            swipeRefreshLayout.isRefreshing = false
            setUpBottomNavigationViewSpringAnimation(floatProperty)
            setUpTabLayoutAnimation(floatProperty)
        }


        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)
        bottomNavigationView.post {
            setUpBottomNavigationViewSpringAnimation(floatProperty)
        }

        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        tabLayout.post {
            setUpTabLayoutAnimation(floatProperty)
        }

    }

    private fun setUpTabLayout() {
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)
        val viewPager2: ViewPager2 = findViewById(R.id.viewPager2)
        viewPager2.adapter = MockViewpagerAdapter()

        TabLayoutMediator(tabLayout, viewPager2) { tab, position ->
            tab.text = "Tab $position"

            when (position) {
                0 -> tab.setIcon(R.drawable.ic_baseline_sports_basketball_24)
                1 -> tab.setIcon(R.drawable.ic_baseline_sports_volleyball_24)
                2 -> tab.setIcon(R.drawable.ic_baseline_sports_soccer_24)
                else -> tab.setIcon(R.drawable.ic_baseline_sports_basketball_24)
            }
        }.attach()
    }

    /**
     * Scale animation for BottomNavigationView menu item views
     */
    private fun setUpBottomNavigationViewSpringAnimation(floatProperty: FloatPropertyCompat<View>) {

        val bottomNavigationView =
            findViewById<BottomNavigationView>(R.id.bottomNavigationView)

        // Get reference to Menu View of BottomNavigationView
        val bottomNavigationMenuView =
            (bottomNavigationView.getChildAt(0) as BottomNavigationMenuView)

        for (i in 0 until bottomNavigationMenuView.childCount) {
            val view: View = bottomNavigationMenuView.getChildAt(i)

            val springAnimation = SpringAnimation(view, floatProperty, 1f)
            springAnimation.spring
                .setStiffness(SpringForce.STIFFNESS_LOW)
                .setDampingRatio(0.25f)

            springAnimation
                // This is the threshold for animation to stop, minimum value before animation ends
                .setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE)
                .setStartVelocity(10f)
                .start()
        }

    }

    private fun setUpTabLayoutAnimation(floatProperty: FloatPropertyCompat<View>) {
        val tabLayout: TabLayout = findViewById(R.id.tabLayout)

        val tabViewGroup = (tabLayout.getChildAt(0) as ViewGroup)

        for (i in 0 until tabViewGroup.childCount) {
            val view: View = tabViewGroup.getChildAt(i)

            val springAnimation = SpringAnimation(view, floatProperty, 1f)
            springAnimation.spring
                .setStiffness(SpringForce.STIFFNESS_LOW)
                .setDampingRatio(0.45f)

            springAnimation
                // This is the threshold for animation to stop, minimum value before animation ends
                .setMinimumVisibleChange(DynamicAnimation.MIN_VISIBLE_CHANGE_SCALE)
                .setStartVelocity(10f)
                .start()
        }

    }
}

/**
 * This is a mock class for ViewPager2 adapter, without adapter TabLayoutMediator crashes.
 * Not relevant with this tutorial.
 */
class MockViewpagerAdapter : RecyclerView.Adapter<MockViewpagerAdapter.MockViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MockViewHolder {

        val view: View =
            LayoutInflater.from(parent.context).inflate(R.layout.mock_layout, parent, false)
        return MockViewHolder(view)
    }

    override fun onBindViewHolder(holder: MockViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {
        return 4
    }

    class MockViewHolder internal constructor(view: View) :
        RecyclerView.ViewHolder(view)
}


