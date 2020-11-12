package com.example.googlebooksapi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.widget.*
import androidx.core.os.HandlerCompat

class MainActivity : AppCompatActivity(),UpdateData{

    private val handler = Handler()
//    private val h = HandlerCompat.createAsync(mainLooper)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val numPicker = findViewById<NumberPicker>(R.id.numb_picker)
        numPicker.minValue=1
        numPicker.maxValue=40

        val etInput = findViewById<TextView>(R.id.et_title)
        val button = findViewById<ImageButton>(R.id.bt_search)
        val spinner = findViewById<Spinner>(R.id.sp_book_type)
        val arrayAdapter = ArrayAdapter<String>(this,
            android.R.layout.simple_list_item_1,
            resources.getStringArray(R.array.print_type_options))
        spinner.adapter = arrayAdapter


        button.setOnClickListener {
       checkNetworkStatus(etInput.text.toString(),
           numPicker.value.toString(),
           spinner.selectedItem.toString()
           )

        }

    }

    fun checkNetworkStatus(bookTitle:String,maxResult:String,bookType:String){
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE)
                as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo

        networkInfo?.let {
        if(it.isConnected){

            val network = Network(this, handler)

            network.setBookTitle(bookTitle )
            network.setBookMaxResult(maxResult)
            network.setBookType(bookType)

            Thread(Runnable{
                val dataSet=
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

    override fun sendData(booksResponse: BooksResponse) {
        val fragment = FragmentDisplayBooks.newInstance(booksResponse)
        supportFragmentManager.beginTransaction()
            .add(R.id.display_fragment_container,fragment)
            .commit()
    }
}