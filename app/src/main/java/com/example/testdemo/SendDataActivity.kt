package com.example.testdemo

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.OutputStream
import java.io.PrintWriter
import java.net.Socket

class SendDataActivity : AppCompatActivity() {
    lateinit var socket: Socket
    lateinit var outputStream: OutputStream
    lateinit var writer: PrintWriter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_send_data)

        val edtIp = findViewById<EditText>(R.id.edtIP)
        val edtPort = findViewById<EditText>(R.id.edtPort)
        val edtData = findViewById<EditText>(R.id.edtData)

        findViewById<Button>(R.id.btnSendData).setOnClickListener {

            if (edtIp.text.toString().trim().isNotEmpty()) {
                if (edtPort.text.toString().trim().isNotEmpty()) {
                    CoroutineScope(Dispatchers.IO).launch {
                        // Code to send data over the network
                        socket = Socket(edtIp.text.toString(), edtPort.text.toString().toInt())
                        outputStream = socket.getOutputStream()
                        writer = PrintWriter(outputStream, true)

                        // Send data
                        writer.println(edtData.text.toString())

                    }
                }else
                    Toast.makeText(this, "Please Enter Port Number!", Toast.LENGTH_SHORT).show()
            }else
                Toast.makeText(this, "Please Enter Ip Address!", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onPause() {
        super.onPause()
        // Close resources
        writer.close()
        socket.close()
    }
}