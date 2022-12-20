package com.plum.KGB.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.plum.KGB.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var essayViewModel: EssayViewModel
    private lateinit var essayAdapter : EssayAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        essayViewModel = ViewModelProvider(this)[EssayViewModel::class.java]
        essayAdapter = EssayAdapter(essayViewModel)

        essayViewModel.essays.observe(this){
            essayAdapter.notifyDataSetChanged()
        }

        binding.listItem.apply{
            layoutManager = LinearLayoutManager(application)
            itemAnimator = DefaultItemAnimator()
            setHasFixedSize(true)
            adapter = essayAdapter
        }
        essayViewModel.requestEssay()
    }
}