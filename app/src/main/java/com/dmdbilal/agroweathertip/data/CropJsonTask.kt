package com.dmdbilal.agroweathertip.data

import android.os.AsyncTask
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response

class CropJsonTask(
    private val N: Float,
    private val P: Float,
    private val K: Float,
    private val temperature: Float,
    private val humidity: Float,
    private val ph: Float,
    private val rainfall: Float,
    private val callback: (String?) -> Unit
) : AsyncTask<Void, Void, String>() {

    override fun doInBackground(vararg params: Void?): String? {
        val baseUrl = "https://agro-weather-tip-aakm.onrender.com/predict"
        val client = OkHttpClient()
        val requestBody: String = "{\n" +
                "    \"N\": $N,\n" +
                "    \"P\": $P,\n" +
                "    \"K\": $K,\n" +
                "    \"temperature\": $temperature,\n" +
                "    \"humidity\": $humidity,\n" +
                "    \"ph\": $ph,\n" +
                "    \"rainfall\": $rainfall\n" +
                "}\n"

        try {
            val urlStr = "$baseUrl?N=$N&P=$P&K=$K&temperature=$temperature&humidity=$humidity&ph=$ph&rainfall=$rainfall"

            val request = Request.Builder()
                .url(urlStr)
                .method("POST", RequestBody.create(null, requestBody))
                .header("Content-Type", "application/json")
                .build()

            val response: Response = client.newCall(request).execute()
            val jsonData = response.body?.string()

            return if (response.isSuccessful) {
                println("Fetched: $jsonData")
                jsonData
            } else {
                println("jsonData not")
                null
            }
        } catch (e: Exception) {
            println(e.message)
            return null
        }
    }

    override fun onPostExecute(jsonData: String?) {
        super.onPostExecute(jsonData)
        callback(jsonData)
    }
}
