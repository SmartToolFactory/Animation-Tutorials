package com.example.animationtutorials.chapter1_basics

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.animationtutorials.R

class Activity1Basics : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity1_basics)
    }
}