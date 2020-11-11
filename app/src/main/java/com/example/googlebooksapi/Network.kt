package com.example.googlebooksapi

import android.net.Uri
import android.text.InputType
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStream
import java.io.InputStreamReader
import java.io.Reader
import java.net.HttpURLConnection
import java.net.URL

//https://www.googleapis.com/books/v1/volumes?q=pride+prejudice&maxResults=5&printType=books

const val BASE_URL="https://www.googleapis.com/"
const val ENDPOINT ="books/v1/volumes?"
const val Q_PARAM ="q"
const val MAX_RESULTS_PARM="maxResults"
const val PRINT_TYPE_PARAM = "printType"

// Implement the UI to Search Book Title, MaxResult, and PrintType
class Network {
    private val URI = Uri.parse("$BASE_URL$ENDPOINT").buildUpon()
            .appendQueryParameter(Q_PARAM,"The Witcher")
            .appendQueryParameter(MAX_RESULTS_PARM, "5")
            .appendQueryParameter(PRINT_TYPE_PARAM,"books")
            .build()

    private val url: URL = URL(URI.toString())

    fun configureNetworkConnection(){

        val httpUrlConnection = url.openConnection()as HttpURLConnection
        httpUrlConnection.readTimeout = 10000
        httpUrlConnection.connectTimeout = 10000
        httpUrlConnection.requestMethod= "GET"
        httpUrlConnection.doInput = true
        httpUrlConnection.connect()

        val inputStream = httpUrlConnection.inputStream
        val responseCode = httpUrlConnection.responseCode

       // val jsonResponse = convertIsToString(inputStream,1024)
       val jsonResponse = convertIsToString2(inputStream)
        println(jsonResponse)

    }
    private fun deserializeJsonResponse(input:String){
        val booksResponse:BooksResponse
        var booksItemsDescription:ItemsDescription
        val jsonResponse: JSONObject=JSONObject(input)
        val booksListItemsDescription = mutableListOf<ItemsDescription>()
        val jsonArrayItems = jsonResponse.getJSONArray("items")
        for(index in 0 until jsonArrayItems.length()){
            val jsonElement = jsonArrayItems[index] as JSONObject

            val jsonVolumeInfo= jsonElement.getJSONObject("volumeInfo")

            val booksItemInfo = VolumeInfo(jsonVolumeInfo.getString("title"),
                    jsonVolumeInfo.getString("subtitle"))

            booksItemsDescription = ItemsDescription(booksItemInfo)
            booksListItemsDescription.add(booksItemsDescription)
        }
        booksResponse = BooksResponse(booksListItemsDescription)
        println(booksResponse.toString())
    }


    fun convertIsToString(stream: InputStream?, len: Int): String? {
        var reader: Reader? = null
        reader = InputStreamReader(stream, "UTF-8")
        val buffer = CharArray(len)
        reader.read(buffer)
        return String(buffer)
    }
    fun convertIsToString2(inputStream: InputStream): String?{
        val builder = StringBuilder()
        val reader = BufferedReader(InputStreamReader(inputStream))
        var line: String? = reader.readLine()
        //while(line = reader.readLine()) ! = null)
        while (line != null) {
            builder.append( line)
            line = reader.readLine()
        }
        if (builder.isEmpty()) {
            return null
        }
         return  builder.toString()

    }

}