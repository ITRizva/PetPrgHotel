package com.example.hoteltest.reservation.presentation.ui

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hoteltest.R
import com.example.hoteltest.binding.BaseFragment
import com.example.hoteltest.databinding.FragmentReservationFragmentBinding

import com.example.hoteltest.reservation.presentation.vm.ReservationViewModel
import dagger.hilt.android.AndroidEntryPoint
import ru.tinkoff.decoro.Mask
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.slots.PredefinedSlots
import ru.tinkoff.decoro.watchers.MaskFormatWatcher
import java.io.Serializable


@AndroidEntryPoint
class ReservationFragment : BaseFragment<FragmentReservationFragmentBinding>() {

    private val viewModel by viewModels<ReservationViewModel>()

    private val recycler: ReservationRecyclerAdapter by lazy{ ReservationRecyclerAdapter { position:Int, personData:PersonRecyclerItem -> viewModel.writePersonData(position,personData)} }

    companion object{
        const val RESERVATION_SCREEN_VALUE = "RESERVATION_VALUE"
        @JvmStatic
        fun newInstance(value: Serializable): ReservationFragment {
            val transitValue: Bundle = Bundle().apply { putSerializable(RESERVATION_SCREEN_VALUE,value) }
            val fragment = ReservationFragment()
            fragment.arguments = transitValue
            return fragment
        }
    }

    override fun getBinding(inflater: LayoutInflater, container: ViewGroup?): FragmentReservationFragmentBinding = FragmentReservationFragmentBinding.inflate(inflater,container,false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.recyclerPersons.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        binding.recyclerPersons.adapter = recycler
        viewModel.personInformationItems.observe(viewLifecycleOwner){
            recycler.submitList(it)
        }

        viewModel.hotelRoomData.observe(viewLifecycleOwner){
            binding.hotelRating.text = it.hotel.ratingNumName
            binding.hotelName.text = it.hotel.name
            binding.hotel.text = it.hotel.name
            binding.hotelAdress.text = it.hotel.adress
            binding.pricePer.text = it.room.pricePer
            binding.roomName.text = it.room.name
            binding.nutrition.text = it.room.peculiarities?.get(0)
            setMaskOnEditText(binding.phoneEditText)
        }

        binding.addPerson.setOnClickListener {
            viewModel.addPerson()
        }
        binding.emailEditText.addTextChangedListener {
            cleanEditTextBackground()
        }
        binding.phoneEditText.addTextChangedListener {
            cleanEditTextBackground()
        }
        binding.payButton.setOnClickListener {
            if(binding.emailEditText.text.toString().isEmailValid() && binding.phoneEditText.text.toString().trim().isPhoneValid()){
                Log.d("Email Check",binding.phoneEditText.text.toString().trim())
            } else {
                Toast.makeText(activity, binding.phoneEditText.text.toString(), Toast.LENGTH_SHORT).show()
                binding.emailEditLayout.boxBackgroundColor = ResourcesCompat.getColor(resources,R.color.invalid_edit_text,null)
                binding.phoneEditLayout.boxBackgroundColor = ResourcesCompat.getColor(resources,R.color.invalid_edit_text,null)
                Log.d("Email Check",binding.phoneEditText.text.toString().trim()+"Hui")
            }
        }
    }

    private fun cleanEditTextBackground(){
        binding.emailEditLayout.boxBackgroundColor = ResourcesCompat.getColor(resources,R.color.grey_edittext,null)
        binding.phoneEditLayout.boxBackgroundColor = ResourcesCompat.getColor(resources,R.color.grey_edittext,null)
    }

    private fun setMaskOnEditText(edittext:EditText){
        val mask: MaskImpl = MaskImpl.createTerminated(PredefinedSlots.RUS_PHONE_NUMBER)
        mask.placeholder = '*'
        mask.isShowingEmptySlots = true
        val formatWatcher = MaskFormatWatcher(mask)
        formatWatcher.installOn(edittext)
        edittext.setText("+")
    }

    private fun String.isEmailValid(): Boolean = !TextUtils.isEmpty(this) && android.util.Patterns.EMAIL_ADDRESS.matcher(this).matches()

    private fun String.isPhoneValid():Boolean = Regex(""".\d..\d{3}..\d{3}-\d{2}-\d{2}""").matches(this)
}