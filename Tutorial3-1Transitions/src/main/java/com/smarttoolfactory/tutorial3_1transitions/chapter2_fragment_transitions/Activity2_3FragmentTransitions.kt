package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.smarttoolfactory.tutorial3_1transitions.R


class Activity2_3FragmentTransitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        supportFragmentManager.fragmentFactory = CustomFragmentFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_1fragment_transitions)
        title = getString(R.string.activity2_2)



//        supportFragmentManager.commit {
//            replace<ListFragment>(R.id.fragmentContainerView)
//        }

//        val fragment = ListFragment()

        val fragment = Fragment2_3RecyclerView()

        supportFragmentManager.beginTransaction()
            .add(R.id.fragmentContainerView, fragment)
            .commit()
    }
}