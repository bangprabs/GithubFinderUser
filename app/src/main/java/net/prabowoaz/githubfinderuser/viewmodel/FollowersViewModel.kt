package net.prabowoaz.githubfinderuser.viewmodel

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.model.ItemUsers
import org.json.JSONArray


class FollowersViewModel : ViewModel() {
    val listUsersFollowers = MutableLiveData<ArrayList<ItemUsers>>()
    private lateinit var ivStatus: ImageView
    private lateinit var tvStatus: TextView


    fun setFollowingGithubUser(username: String, context: Context?) {
        val listItems = ArrayList<ItemUsers>()

        ivStatus = (context as Activity).findViewById(R.id.iv_dataFollowers)
        tvStatus = (context).findViewById(R.id.tvDataFollowers)


        val keyToken = "7b770908f8426b7a4abb0c61b96d2dbec47dc588"
        val url = "https://api.github.com/users/$username/followers"

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
                    val result = String(responseBody)
                    val responseArray = JSONArray(result)

                    for (i in 0 until responseArray.length()) {
                        val jsonObject = responseArray.getJSONObject(i)
                        val weatherItems = ItemUsers()
                        weatherItems.login = jsonObject.getString("login")
                        weatherItems.avatar = jsonObject.getString("avatar_url")
                        weatherItems.url = jsonObject.getString("html_url")
                        listItems.add(weatherItems)
                    }
                    listUsersFollowers.postValue(listItems)
                    if (responseArray.length() == 0) {
                        ivStatus.visibility = View.VISIBLE
                        tvStatus.visibility = View.VISIBLE
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
        return listUsersFollowers
    }

}