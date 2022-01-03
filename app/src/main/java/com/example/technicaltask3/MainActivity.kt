package com.example.technicaltask3

import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.core.view.size
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2

class MainActivity : AppCompatActivity() {
    private lateinit var viewPager: ViewPager2
    private lateinit var buttonAdd: Button
    private lateinit var buttonRemove: Button
    private lateinit var textCount: TextView
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonRemove = findViewById(R.id.buttonRemove)
        textCount = findViewById(R.id.textCount)
        viewPager = findViewById(R.id.viewPager)
        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        val pagerAdapter = SlidePagerAdapter(this, listOf())
        viewPager.adapter = pagerAdapter

        viewModel.liveDataModelResult.observe(this, {
            pagerAdapter.newList(it)
            viewPager.currentItem = it.size.also { it2-> textCount.text = it.size.toString() }

            buttonRemove.run {
                if (it.size>1)  this.visibility =  View.VISIBLE
                else this.visibility =  View.GONE
            }
        })

        buttonAdd.setOnClickListener {
            viewModel.addFragment()
            Log.d("MyLog", "setOnClickListener ")
        }
        buttonRemove.setOnClickListener {
            Log.d("MyLog", "setOnClickListener")
            viewModel.removeFragment()
        }
    }
}