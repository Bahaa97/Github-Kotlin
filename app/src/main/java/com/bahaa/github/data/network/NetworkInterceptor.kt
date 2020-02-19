package com.bahaa.github.data.network

import android.content.Context
import android.util.Log

import com.bahaa.github.ui.activities.base.BaseActivity

import org.json.JSONException
import org.json.JSONObject

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Response

class NetworkInterceptor(private val context: Context) : Interceptor {
    private val networkEvent: NetworkEvent?

    init {
        this.networkEvent = NetworkEvent.instance
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        if (!ConnectivityStatus.isConnected(context)) {
            BaseActivity.instance?.hideProgressDialog()
            networkEvent!!.publish(NetworkState.NO_INTERNET)
            Log.d("NETWORK", "code : " + request.body!!.toString() + " " + request.url)
        } else {
            try {
                val response = chain.proceed(request)
                //                Log.d("NETWORK", "////////////////////////////////////////////////");
                //                Log.d("NETWORK", "code : " + response.code() + "  " + request.url());
                //                Log.d("NETWORK", "Response : " + response.body().string());
                BaseActivity.instance?.hideProgressDialog()
                when (response.code) {
                    401 -> networkEvent!!.publish(NetworkState.UNAUTHORISED)
                    500 -> networkEvent!!.publish(NetworkState.SERVER_ERROR)
                    404 -> networkEvent!!.publish(NetworkState.API_NOT_FOUND)
                    405 -> networkEvent!!.publish(NetworkState.NOT_ALLOWED_METHOD)
                    503 -> networkEvent!!.publish(NetworkState.MAINTENANCE)
                    400, 403 -> {
                        var errorMessage403 = ""
                        var jsonObject: JSONObject? = null
                        try {
                            jsonObject = JSONObject(response.body!!.string())
                            errorMessage403 = jsonObject.optJSONArray("error")!!.getString(0)
                        } catch (e: JSONException) {
                            Log.e("NETWORK", e.message)
                        }

                        if (jsonObject != null) {
                            handle400(errorMessage403, jsonObject)
                        }
                    }
                }
                return response

            } catch (e: IOException) {
                Log.e("NETWORK", e.message)
                networkEvent!!.publish(NetworkState.NO_RESPONSE)
            }

        }
        return null!!
    }

    private fun handle400(message: String, jsonObject: JSONObject?) {
        when (message) {
            "User is logged." -> networkEvent!!.publish(NetworkState.ALREADY_LOGIN)
            "User is not logged.", "User is not logged in" -> networkEvent!!.publish(NetworkState.UNAUTHORISED)
            else -> networkEvent!!.publish(NetworkState.BAD_REQUEST)
        }
    }
}
