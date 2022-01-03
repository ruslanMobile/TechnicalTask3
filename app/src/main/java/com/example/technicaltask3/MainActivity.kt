package com.example.technicaltask3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    lateinit var viewPager: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        viewPager = findViewById(R.id.viewPager)
        var list = mutableListOf(
            SlidePageFragment(), SlidePageFragment(), SlidePageFragment(),
            SlidePageFragment(), SlidePageFragment(), SlidePageFragment()
        )

        val pagerAdapter = SlidePagerAdapter(this, list)
        viewPager.adapter = pagerAdapter
    }
}