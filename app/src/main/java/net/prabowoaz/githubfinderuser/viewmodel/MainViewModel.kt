package net.prabowoaz.githubfinderuser.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import cn.pedant.SweetAlert.SweetAlertDialog
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.model.ItemUsers
import org.json.JSONObject


class MainViewModel : ViewModel() {
    val listMainUsers = MutableLiveData<ArrayList<ItemUsers>>()
    private lateinit var ivStatus: ImageView
    private lateinit var tvStatus_a: TextView
    private lateinit var tvStatus_b: TextView


    fun setWeather(username: String, context: Context?) {
        val listItems = ArrayList<ItemUsers>()

        val keyToken = "7b770908f8426b7a4abb0c61b96d2dbec47dc588"
        val url = "https://api.github.com/search/users?q=$username"

        ivStatus = (context as Activity).findViewById(R.id.iv_dataMain)
        tvStatus_a = (context).findViewById(R.id.tvDataMain)
        tvStatus_b = (context).findViewById(R.id.tvDataMain2)


        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $keyToken")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>,
                responseBody: ByteArray
            ) {
                try {
                    //parsing json
                    val result = String(responseBody)
                    val responseObject = JSONObject(result)
                    val list = responseObject.getJSONArray("items")

                    for (i in 0 until list.length()) {
                        val weather = list.getJSONObject(i)
                        val weatherItems = ItemUsers()
                        weatherItems.id = weather.getInt("id")
                        weatherItems.login = weather.getString("login")
                        weatherItems.avatar = weather.getString("avatar_url")
                        weatherItems.url = weather.getString("html_url")
                        listItems.add(weatherItems)
                    }
                    listMainUsers.postValue(listItems)
                    if (list.length() >= 0) {
                        ivStatus.visibility = View.GONE
                        tvStatus_a.visibility = View.GONE
                        tvStatus_b.visibility = View.GONE
                    }
                    if (list.length() == 0) {
                        SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                            .setTitleText("Oops...")
                            .setContentText(context.getString(R.string.warning_user))
                            .show()
                    }
                } catch (e: Exception) {
                    Log.d("Exception", e.message.toString())
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<Header>,
                responseBody: ByteArray,
                error: Throwable
            ) {
                Log.d("onFailure", error.message.toString())
            }
        })
    }

    fun getWeathers(): LiveData<ArrayList<ItemUsers>> {
        return listMainUsers
    }
}