package com.example.contact_tracking.views

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.PersistableBundle
import android.util.AttributeSet
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.contact_tracking.databinding.MainBinding
import com.example.contact_tracking.logger.Logger

class MainActivity: AppCompatActivity() {

    //Variables for binding and Logger
    private lateinit var binding: MainBinding
    private var logger: Logger = Logger(this.javaClass.simpleName)

    //Variables Person

    override fun onCreate(savedInstanceState: Bundle?) {
        this.logger.info("MainActivity was started")

        // Locking app into landscape mode
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

        super.onCreate(savedInstanceState)

        //Binding inflate layout
        binding = MainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}