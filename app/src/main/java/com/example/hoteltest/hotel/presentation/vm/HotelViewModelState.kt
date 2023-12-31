package com.example.hoteltest.hotel.presentation.vm

import android.graphics.Bitmap

sealed class HotelViewModelState{
    object Initial: HotelViewModelState()
    object Loading: HotelViewModelState()
    data class HotelFragmentContent(
        val id:Long,
        val name:String,
        val adress:String,
        val minimalPrice: String,
        val priceForIt:String,
        val ratingNumName:String,
        val imageList:ArrayList<Bitmap>,
        val description:String,
        val peculiarities:List<String>
    ): HotelViewModelState()
    data class Error(val massage:String): HotelViewModelState()
}
