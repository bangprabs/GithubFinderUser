package net.prabowoaz.githubfinderuser

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import net.prabowoaz.githubfinderuser.activity.DetailActivity
import net.prabowoaz.githubfinderuser.activity.FavoriteActivity
import net.prabowoaz.githubfinderuser.activity.SettingActivity
import net.prabowoaz.githubfinderuser.adapter.MainAdapter
import net.prabowoaz.githubfinderuser.model.ItemUsers
import net.prabowoaz.githubfinderuser.viewmodel.MainViewModel


class MainActivity : AppCompatActivity() {
    private lateinit var adapter: MainAdapter
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = MainAdapter()
        adapter.notifyDataSetChanged()

        showRecyclerList()

        mainViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel::class.java)

        btnCity.setOnClickListener {
            val city = edtCity.text.toString()

            if (city.isEmpty()) return@setOnClickListener

            showLoading(true)
            mainViewModel.setWeather(city, this)

            hideSoftKeyboard(this)
        }

        fabToFav.setOnClickListener {
            val intent = Intent(this, FavoriteActivity::class.java)
            startActivity(intent)
        }

        btnSetting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        mainViewModel.getWeathers().observe(this, Observer { weatherItems ->
            if (weatherItems != null) {
                adapter.setData(weatherItems)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerList() {
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        adapter.setOnItemClickCallback(object : MainAdapter.OnItemClickCallback{
            override fun onItemClicked(data : ItemUsers) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_STATE, data)
                startActivity(intent)
            }
        })
    }

    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager =
            activity.getSystemService(
                Activity.INPUT_METHOD_SERVICE
            ) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(
            activity.currentFocus!!.windowToken, 0
        )
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            iv_dataMain.visibility = View.GONE
            tvDataMain.visibility = View.GONE
            tvDataMain2.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}