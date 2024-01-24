package com.example.testdemo

import android.content.Context
import android.net.wifi.WifiManager
import android.os.Bundle
import android.text.format.Formatter
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.testdemo.NetworkUtils.getLocalIpAddress
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.net.ServerSocket
import java.net.Socket


class ReceiveDataActivity : AppCompatActivity() {

    // Code to receive data over the network
    lateinit var serverSocket: ServerSocket
    lateinit var clientSocket: Socket
    lateinit var inputStream: InputStream
    lateinit var reader: BufferedReader

    var freePort:Int = 9100
    var ipAddress = ""

    lateinit var tvIpAddress:TextView
    lateinit var tvResult:TextView
    var receivedData = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_receive_data)

        ipAddress = getLocalIpAddress(this)!!

        tvIpAddress = findViewById(R.id.tvMyIp)
        tvResult = findViewById(R.id.tvResult)

        val manager = getSystemService(Context.WIFI_SERVICE) as WifiManager
        val info = manager.connectionInfo

        if (ipAddress != null) {
            Log.d("MainActivity", "Local IP Address: $ipAddress")
            tvIpAddress.text = "This Device Ip Addredss : ${Formatter.formatIpAddress(info.ipAddress)}, Port : $freePort"
        } else {
            Log.e("MainActivity", "Failed to retrieve local IP address")
            tvIpAddress.text = "Failed to retrieve local IP address!"
        }

        // Read data
        CoroutineScope(Dispatchers.IO).launch {
            Log.e("Soham", "onCreate: 1" )
            connectToServer(freePort)
        }
    }

    private fun connectToServer(PORT_NUMBER: Int) {
        try {

            // Code to receive data over the network
            val serverSocket = ServerSocket(PORT_NUMBER)
            Log.e("Soham", "onCreate: 2")
            freePort = serverSocket.localPort
            val clientSocket = serverSocket.accept()
            Log.e("Soham", "onCreate: 4")
            val inputStream = clientSocket.getInputStream()
            Log.e("Soham", "onCreate: 5")

            reader = BufferedReader(InputStreamReader(inputStream))
            receivedData = reader.readLine()

            this.runOnUiThread {
                Log.e("Soham", "onCreate: 6")
                tvResult.text = receivedData
                Log.e("Soham", "onCreate: 7")
                CoroutineScope(Dispatchers.IO).launch {
                    reader.close();
                    clientSocket.close();
                    serverSocket.close();
                    connectToServer(freePort)
                }
            }
            // Read data

            Log.e("Soham", "onCreate: 8")
        }catch (e:Exception){
            e.printStackTrace()
            connectToServer(freePort)
        }
    }

    override fun onPause() {
        super.onPause()
        // Close resources
        reader.close();
        clientSocket.close();
        serverSocket.close();
    }
}