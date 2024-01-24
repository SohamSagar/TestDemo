package com.example.testdemo

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log


object NetworkUtils {
    fun getLocalIpAddress(context: Context): String? {
        val wifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        return if (wifiManager.isWifiEnabled) {
            val wifiInfo = wifiManager.connectionInfo
            val ipAddress = wifiInfo.ipAddress
            formatIpAddress(ipAddress)
        } else {
            Log.e("NetworkUtils", "WiFi is not enabled")
            null
        }
    }

    private fun formatIpAddress(ipAddress: Int): String {
        return (ipAddress and 0xFF).toString() + "." +
                (ipAddress shr 8 and 0xFF) + "." +
                (ipAddress shr 16 and 0xFF) + "." +
                (ipAddress shr 24 and 0xFF)
    }
}
