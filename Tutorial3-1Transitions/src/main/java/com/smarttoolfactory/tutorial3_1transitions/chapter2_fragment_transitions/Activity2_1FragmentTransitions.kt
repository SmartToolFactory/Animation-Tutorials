package com.smarttoolfactory.tutorial3_1transitions.chapter2_fragment_transitions

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import com.smarttoolfactory.tutorial3_1transitions.R

const val KEY_AVATAR = "key-avatar"

class Activity2_1FragmentTransitions : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {

//        supportFragmentManager.fragmentFactory = CustomFragmentFactory()

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity2_1fragment_transitions)
        title = getString(R.string.activity2_1)

        supportFragmentManager.commit {
            replace<Fragment2_1RecyclerView>(R.id.fragmentContainerView)
        }
    }
}