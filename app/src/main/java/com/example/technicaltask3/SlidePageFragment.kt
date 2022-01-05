package com.example.technicaltask3

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

class SlidePageFragment(private var number: Int): Fragment() {

    private lateinit var buttonNotification:Button
    private lateinit var mainViewModel:MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_slide_page, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        buttonNotification = view.findViewById(R.id.buttonCreateNotification)
        buttonNotification.setOnClickListener {
            mainViewModel.createNotificationChannel()
            mainViewModel.createNotification(number)
        }
    }

}