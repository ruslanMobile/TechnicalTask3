package com.example.technicaltask3

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import kotlin.math.sign

class MainActivity : AppCompatActivity() {
    private val PREFERENCE_COUNT = "count"

    private lateinit var viewPager: ViewPager2
    private lateinit var buttonAdd: Button
    private lateinit var buttonRemove: Button
    private lateinit var textCount: TextView
    private lateinit var viewModel: MainViewModel
    private var sizeList: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        buttonAdd = findViewById(R.id.buttonAdd)
        buttonRemove = findViewById(R.id.buttonRemove)
        textCount = findViewById(R.id.textCount)
        viewPager = findViewById(R.id.viewPager)
        viewModel =
            ViewModelProvider(this, FactoryMainViewModel(this)).get(MainViewModel::class.java)

        val pagerAdapter = SlidePagerAdapter(this, listOf())
        viewPager.adapter = pagerAdapter

        viewModel.liveDataModelResult.observe(this, {
            pagerAdapter.newList(it)
            viewPager.currentItem = it.size.also { it2 -> sizeList = it.size }

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
                textCount.text = (position + 1).toString()
            }
        })


        var bundle: Bundle? = intent.extras
        if (bundle != null) {
            for (i in 1 until bundle.getInt("numberOfPage", 1)) {
                viewModel.addFragment()
            }
        } else {
            val sharedPref =
                getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
            if (sharedPref != null) {
                for (i in 1 until sharedPref.getInt(PREFERENCE_COUNT, 1)) {
                    viewModel.addFragment()
                }
            }
        }
    }

    override fun onStop() {
        Log.d("MyLog", " onStop ")
        val sharedPref =
            getSharedPreferences(getString(R.string.preference_key), Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putInt(PREFERENCE_COUNT, sizeList)
            commit()
        }
        super.onStop()
    }

    override fun onDestroy() {
        Log.d("MyLog", " onDestroy ")
        viewModel.clearLists()
        super.onDestroy()
    }
}