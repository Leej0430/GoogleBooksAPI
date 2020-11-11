package com.example.googlebooksapi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkNetworkStatus()
    }

    fun checkNetworkStatus(){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        networkInfo?.let {
        if(networkInfo.isConnected){
            val network = Network()
            Thread(Runnable{
                network.configureNetworkConnection()
            }).start()
        }
            else
                Toast.makeText(this,
                        "No network connection",
                        Toast.LENGTH_SHORT
                ).show()
        }
    }
}