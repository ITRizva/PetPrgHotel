package com.example.hoteltest.navigation

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.hoteltest.R
import com.example.hoteltest.order.presentation.ui.OrderFragment
import com.example.hoteltest.reservation.presentation.ui.ReservationFragment
import com.example.hoteltest.rooms.presentation.ui.RoomsFragment
import java.io.Serializable

class NavigatorImp: NavigatorInterface{

    private var fragmentManager:FragmentManager? = null
    fun attachActivity(activity:AppCompatActivity){
        fragmentManager = activity.supportFragmentManager
    }

    fun detachActivity(){
        fragmentManager = null
    }

    override fun stepBack() {
        fragmentManager?.popBackStack()
    }

    override fun addScreen(data: Serializable, fragmentKey: String?) {
        when(fragmentKey){
            RoomsFragment.ROOM_SCREEN_VALUE ->{
                launchAddFragment(RoomsFragment.newInstance(data))
            }
            ReservationFragment.RESERVATION_SCREEN_VALUE->{
                launchAddFragment(ReservationFragment.newInstance(data))
            }
            OrderFragment.ORDER_FRAGMENT_VALUE->{
                launchAddFragment(OrderFragment.newInstance(data))
            }
        }
    }

    override fun replaceScreen(data: Serializable, fragmentKey:String?) {
        when(fragmentKey){
            RoomsFragment.ROOM_SCREEN_VALUE ->{
                launchReplaceFragment(RoomsFragment.newInstance(data))
            }
            ReservationFragment.RESERVATION_SCREEN_VALUE->{
                launchReplaceFragment(ReservationFragment.newInstance(data))
            }
            OrderFragment.ORDER_FRAGMENT_VALUE->{
                launchReplaceFragment(OrderFragment.newInstance(data))
            }
        }
    }

    override fun popUntil(tag:String) {
        fragmentManager?.popBackStack(tag,FragmentManager.POP_BACK_STACK_INCLUSIVE)

    }
    private fun launchAddFragment(fragment: Fragment) {
        fragmentManager?.beginTransaction()?.add(R.id.mainContainer, fragment)?.addToBackStack(fragment.tag)?.commit()
    }
    private fun launchReplaceFragment(fragment:Fragment){
        fragmentManager?.beginTransaction()?.replace(R.id.mainContainer,fragment)?.addToBackStack(fragment.tag)?.commit()
    }
}
