package com.curiousapps.codechalenge.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.curiousapps.codechalenge.R
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val TAG: String = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews();
    }

    private fun initViews(){
        flag_logo.setOnClickListener {
            Log.d(TAG, "###Button Clicked###")
            val intent = Intent(this, StoreListActivity::class.java)
            startActivity(intent)
        }
    }
}
