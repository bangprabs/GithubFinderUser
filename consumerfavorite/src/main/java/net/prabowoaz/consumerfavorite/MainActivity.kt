package net.prabowoaz.consumerfavorite

import android.app.Activity
import android.content.Intent
import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.prabowoaz.consumerfavorite.adapter.FavoriteAdapter
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import net.prabowoaz.consumerfavorite.helper.MappingHelper
import net.prabowoaz.consumerfavorite.model.ItemFavorite

class MainActivity : AppCompatActivity() {
    private lateinit var adapter: FavoriteAdapter

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerViewFav.layoutManager = LinearLayoutManager(this)
        recyclerViewFav.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        recyclerViewFav.adapter = adapter

        showLoading(true)

        val handlerThread = HandlerThread("DataObserver")
        handlerThread.start()
        val handler = Handler(handlerThread.looper)

        val myObserver = object : ContentObserver(handler) {
            override fun onChange(selfChange: Boolean) {
                loadFavoriteList()
                super.onChange(selfChange)
            }
        }

        contentResolver.registerContentObserver(CONTENT_URI, true, myObserver)

        if (savedInstanceState == null) {
            loadFavoriteList()
        } else {
            val list = savedInstanceState.getParcelableArrayList<ItemFavorite>(EXTRA_STATE)
            if (list != null) {
                adapter.listFavorite = list
            }
        }
    }

    private fun loadFavoriteList() {
        GlobalScope.launch(Dispatchers.Main) {
            val deferredNotes = async(Dispatchers.IO) {
                val cursor = contentResolver?.query(CONTENT_URI, null,null,null,null)
                MappingHelper.mapCursorToArrayList(cursor)
            }
            val favorite = deferredNotes.await()
            if (favorite.size > 0) {
                adapter.listFavorite = favorite
                showLoading(false)
                iv_dataMain.visibility = View.GONE
                tvDataMain.visibility = View.GONE
            } else {
                adapter.listFavorite = ArrayList()
                showLoading(false)
                iv_dataMain.visibility = View.VISIBLE
                tvDataMain.visibility = View.VISIBLE
            }
        }
    }

    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
            iv_dataMain.visibility = View.GONE
            tvDataMain.visibility = View.GONE
        } else {
            progressBar.visibility = View.GONE
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putParcelableArrayList(EXTRA_STATE, adapter.listFavorite)
    }

    override fun onResume() {
        super.onResume()
        loadFavoriteList()
    }
}