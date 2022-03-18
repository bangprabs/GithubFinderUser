package net.prabowoaz.githubfinderuser.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.loopj.android.http.AsyncHttpClient
import com.loopj.android.http.AsyncHttpResponseHandler
import cz.msebera.android.httpclient.Header
import net.prabowoaz.githubfinderuser.model.ItemUsers
import org.json.JSONObject

class DetailViewModel : ViewModel() {
    val listUsers = MutableLiveData<ItemUsers>()

    fun setDetailUsers(username: String) {

        val keyToken = "7b770908f8426b7a4abb0c61b96d2dbec47dc588"
        val url = "https://api.github.com/users/$username"

        val client = AsyncHttpClient()
        client.addHeader("Authorization", "token $keyToken")
        client.addHeader("User-Agent", "request")
        client.get(url, object : AsyncHttpResponseHandler() {
            override fun onSuccess(statusCode: Int, headers: Array<Header>?, responseBody: ByteArray) {
                val result = String(responseBody)
                try {
                    val responseObject = JSONObject(result)
                    val githubUser = ItemUsers()
                    githubUser.avatar = responseObject.getString("avatar_url")
                    githubUser.login = responseObject.getString("login")
                    githubUser.name = responseObject.getString("name")
                    githubUser.location = responseObject.getString("location")
                    githubUser.company = responseObject.getString("company")
                    githubUser.following = responseObject.getInt("following")
                    githubUser.followers = responseObject.getInt("followers")
                    githubUser.followers = responseObject.getInt("followers")
                    githubUser.repos = responseObject.getInt("public_repos")
                    listUsers.postValue(githubUser)
                } catch (e: Exception) {
                    e.printStackTrace()
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

    fun getDetailUser(): LiveData<ItemUsers> {
        return listUsers
    }
}