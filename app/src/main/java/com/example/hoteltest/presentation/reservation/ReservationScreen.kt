package com.example.hoteltest.presentation.reservation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoteltest.R
import com.example.hoteltest.databinding.FragmentHotelScreenBinding
import com.example.hoteltest.databinding.FragmentReservationScreenBinding
import com.example.hoteltest.presentation.rooms.RoomsInformationAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReservationScreen : Fragment() {

    private var binding:FragmentReservationScreenBinding? = null
    private val viewModel by viewModels<ReservationViewModel>()

    private val recycler: ReservationRecyclerAdapter by lazy{ ReservationRecyclerAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentReservationScreenBinding.inflate(inflater, container, false)
        binding?.recyclerPersons?.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding?.recyclerPersons?.adapter = recycler
        return binding?.root
    }
}