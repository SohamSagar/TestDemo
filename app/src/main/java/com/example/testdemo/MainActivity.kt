package com.example.testdemo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.btnSendData).setOnClickListener {
            startActivity(Intent(this,SendDataActivity::class.java))
        }

        findViewById<Button>(R.id.btnReceiveData).setOnClickListener {
            startActivity(Intent(this,ReceiveDataActivity::class.java))
        }
    }
}