package com.example.anirecord.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.anirecord.databinding.ActivityMainBinding
import com.example.anirecord.domain.usecase.GetTagsUseCase
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val getTagsUseCase: GetTagsUseCase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }
}