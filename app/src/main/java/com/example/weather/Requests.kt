package com.example.weather


import android.content.Context
import android.util.Log
import androidx.core.util.Pair
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONObject
import java.io.*
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL
import java.net.URLEncoder
import java.util.*
import javax.net.ssl.HttpsURLConnection

/**
 * Created by root on 24/05/15.
 */
class Requests{

    fun sendGet(x: String?): String {
        val stime: Long
        val etime: Long
        val dis: Long
        stime = System.nanoTime()
        return if (x != null && x.contains("https")) {
            val builder = StringBuilder()
            var urlConnection: HttpsURLConnection? = null
            var `in`: InputStream? = null
            try {
                //            SSLContext context=getsslcontexterror1(cnt);
                val url = URL(x)
                urlConnection = url.openConnection() as HttpsURLConnection
                //            urlConnection.setSSLSocketFactory(context.getSocketFactory());
                //                urlConnection.setHostnameVerifier(BaseActivity.hostnameVerifier);
                urlConnection.instanceFollowRedirects = false
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-Type", "text/plain")
                urlConnection.setRequestProperty("charset", "utf-8")

                urlConnection.setRequestProperty("Connection", "Keep-Alive")
                urlConnection.connect()
                `in` = urlConnection.inputStream
                val reader = BufferedReader(
                    InputStreamReader(`in`)
                )
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
            } catch (e1: MalformedURLException) {
                e1.printStackTrace()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
            if (urlConnection != null && `in` != null) {
                urlConnection.disconnect()
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }


            etime = System.nanoTime()
            dis = (etime - stime) / 1000000
            Log.e("time of api$x", dis.toString())
            builder.toString()
        } else {
            val builder = StringBuilder()
            var urlConnection: HttpURLConnection? = null
            var `in`: InputStream? = null
            try {
                val url = URL(x)
                urlConnection = url.openConnection() as HttpURLConnection
                urlConnection.instanceFollowRedirects = false
                urlConnection.requestMethod = "GET"
                urlConnection.setRequestProperty("Content-Type", "text/plain")
                urlConnection.setRequestProperty("charset", "utf-8")

                urlConnection.setRequestProperty("Connection", "Keep-Alive")
                urlConnection.connect()
                `in` = urlConnection.inputStream
                val reader = BufferedReader(
                    InputStreamReader(`in`)
                )
                var line: String?
                while (reader.readLine().also { line = it } != null) {
                    builder.append(line)
                }
            } catch (e1: MalformedURLException) {
                e1.printStackTrace()
            } catch (e1: IOException) {
                e1.printStackTrace()
            }
            if (urlConnection != null && `in` != null) {
                urlConnection.disconnect()
                try {
                    `in`.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }
            }


            builder.toString()
        }
    }

}