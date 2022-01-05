package com.example.technicaltask3

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
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
        viewModel = ViewModelProvider(this,FactoryMainViewModel(this)).get(MainViewModel::class.java)

        val pagerAdapter = SlidePagerAdapter(this, listOf())
        viewPager.adapter = pagerAdapter

        viewModel.liveDataModelResult.observe(this, {
            pagerAdapter.newList(it)
            viewPager.currentItem = it.size

            buttonRemove.run {
                if (it.size > 1) this.visibility = View.VISIBLE
                else this.visibility = View.GONE
            }
        })

        buttonAdd.setOnClickListener { viewModel.addFragment() }

        buttonRemove.setOnClickListener {
            viewModel.removeNotifications()
            viewModel.removeFragment()
        }

        viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                textCount.text = (position+1).toString()
            }
        })

        var bundle: Bundle? = intent.extras
        Log.d(
            "MyLog",
            "onCreate numberOfPage: " + bundle?.getInt(
                "numberOfPage",
                0
            ) + " countPages: " + bundle?.getInt("countPages", 0)
        )
    }
}