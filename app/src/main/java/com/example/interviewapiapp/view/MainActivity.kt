package com.example.interviewapiapp.view

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.example.interviewapiapp.R
import com.example.interviewapiapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun customizeActionBar(toolbar: Toolbar, title: String){
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = title
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                findNavController(R.id.fragmentContainerView).navigate(R.id.action_detailsFragment_to_homeFragment)
                supportActionBar?.setDisplayHomeAsUpEnabled(false)
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }
}