package com.example.technicaltask3

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.adapter.FragmentViewHolder

class SlidePagerAdapter(fa: FragmentActivity, private var list: List<SlidePageFragment>) : FragmentStateAdapter(fa) {

    fun newList(newList:List<SlidePageFragment>){
        list = newList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    override fun createFragment(position: Int): Fragment {
        Log.d("MyLog","Hello")
        return list[position]
    }

    override fun onBindViewHolder(
        holder: FragmentViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun getItemId(position: Int): Long {
        return super.getItemId(position)
    }

    override fun containsItem(itemId: Long): Boolean {
        return super.containsItem(itemId)
    }
}