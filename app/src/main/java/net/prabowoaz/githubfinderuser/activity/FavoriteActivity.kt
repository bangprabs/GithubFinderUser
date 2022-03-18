package net.prabowoaz.githubfinderuser.activity

import android.database.ContentObserver
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.view.View
import android.widget.Adapter
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.activity_favorite.*
import kotlinx.android.synthetic.main.activity_favorite.iv_dataMain
import kotlinx.android.synthetic.main.activity_favorite.progressBar
import kotlinx.android.synthetic.main.activity_favorite.tvDataMain
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.adapter.FavoriteAdapter
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import net.prabowoaz.githubfinderuser.database.QueryHelper
import net.prabowoaz.githubfinderuser.helper.MappingHelper
import net.prabowoaz.githubfinderuser.model.ItemFavorite
import net.prabowoaz.githubfinderuser.model.ItemUsers

class FavoriteActivity : AppCompatActivity() {

    private lateinit var adapter: FavoriteAdapter
    private lateinit var queryHelper: QueryHelper

    companion object {
        private const val EXTRA_STATE = "extra_state"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)

        queryHelper = QueryHelper.getInstance(applicationContext)
        queryHelper.open()

        recyclerViewFav.layoutManager = LinearLayoutManager(this)
        recyclerViewFav.setHasFixedSize(true)
        adapter = FavoriteAdapter(this)
        recyclerViewFav.adapter = adapter

        iv_back.setOnClickListener {
            finish()
        }

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
                recyclerViewFav.visibility = View.INVISIBLE
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

    override fun onResume() {
        super.onResume()
        loadFavoriteList()
    }

    override fun onDestroy() {
        super.onDestroy()
        queryHelper.close()
    }
}