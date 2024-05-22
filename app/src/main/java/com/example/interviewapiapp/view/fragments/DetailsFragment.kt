package com.example.interviewapiapp.view.fragments

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.Spanned
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.interviewapiapp.R
import com.example.interviewapiapp.api.RetrofitProvider
import com.example.interviewapiapp.databinding.FragmentDetailsBinding
import com.example.interviewapiapp.repository.MainRepository
import com.example.interviewapiapp.view.MainActivity
import com.example.interviewapiapp.viewmodel.MainViewModel
import com.example.interviewapiapp.viewmodel.MainViewModelFactory

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: MainViewModel

    private lateinit var bundle: Bundle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val jsonAPI = RetrofitProvider.jsonAPI

        viewModel = ViewModelProvider(
            this, MainViewModelFactory(MainRepository(jsonAPI))
        )[MainViewModel::class.java]
        bundle = requireArguments()

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        (activity as MainActivity).customizeActionBar(binding.toolbar, "User Details")
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (activity as MainActivity).customizeActionBar(binding.toolbar, "User Details")

        val name= bundle.getString("name")
        val email = bundle.getString("email")
        val phone = bundle.getString("phone")
        val street = bundle.getString("street")
        val city = bundle.getString("city")
        val suite = bundle.getString("suite")
        val zip = bundle.getString("zip")
        val lat = bundle.getString("lat")
        val long = bundle.getString("long")

        val name_text = "Name: ${name}"
        val email_text = "Email Address: ${email}"
        val phone_text = "Phone.: ${phone}"
        val street_text = "Street: ${street}"
        val city_text = "City: ${city}"
        val suite_text = "Suite: ${suite}"
        val zip_text = "Zip: ${zip}"
        val lat_text = "Latitude: ${lat}"
        val long_text = "Longitude: ${long}"


        val boldSpan = StyleSpan(Typeface.BOLD)
        val normalSpan = StyleSpan(Typeface.NORMAL)

        val blueColor = ForegroundColorSpan(Color.parseColor("#0091EA"))

        val spannableName = SpannableString(name_text)
        val spannableEmail = SpannableString(email_text)
        val spannablePhone = SpannableString(phone_text)
        val spannableStreet = SpannableString(street_text)
        val spannableCity = SpannableString(city_text)
        val spannableSuite = SpannableString(suite_text)
        val spannableZip = SpannableString(zip_text)
        val spannableLat = SpannableString(lat_text)
        val spannableLong = SpannableString(long_text)

        val spannableStringList = mutableListOf<SpannableString>()
        spannableStringList.add(spannableName)
        spannableStringList.add(spannableEmail)
        spannableStringList.add(spannablePhone)
        spannableStringList.add(spannableStreet)
        spannableStringList.add(spannableCity)
        spannableStringList.add(spannableSuite)
        spannableStringList.add(spannableZip)
        spannableStringList.add(spannableLat)
        spannableStringList.add(spannableLong)

        for(item in spannableStringList){
            val substring = item.split(" ")
            var firstWord = substring[0]

            if(firstWord == "Email")
                firstWord = "Email Address:"

            applySpan(item, boldSpan, null, null,0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            applySpan(item, normalSpan, null, null, firstWord.length, item.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            applySpan(item, null, AbsoluteSizeSpan(17, true), null, 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            applySpan(item, null, AbsoluteSizeSpan(15, true), null, firstWord.length, item.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            applySpan(item, null, null, blueColor, 0, firstWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        binding.txtDetailName.text = spannableName
        binding.txtDetailEmail.text = spannableEmail
        binding.txtCity.text = spannableCity
        binding.txtDetailPhone.text = spannablePhone
        binding.txtStreet.text = spannableStreet
        binding.txtSuite.text = spannableSuite
        binding.txtZipcode.text = spannableZip
        binding.txtLat.text = spannableLat
        binding.txtLng.text = spannableLong




    }

    private fun applySpan(spannableString: SpannableString, typefaceSpan: StyleSpan?, sizeSpan: AbsoluteSizeSpan?, colorSpan: ForegroundColorSpan?, start: Int, end: Int, flag: Int){
        if (typefaceSpan!= null)
            spannableString.setSpan(typefaceSpan, start, end, flag)
        else
            spannableString.setSpan(sizeSpan, start, end, flag)

        if (colorSpan != null)
            spannableString.setSpan(colorSpan, start, end, flag)
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}