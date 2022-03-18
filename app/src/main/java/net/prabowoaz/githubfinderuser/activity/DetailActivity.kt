package net.prabowoaz.githubfinderuser.activity

import android.annotation.SuppressLint
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.ContentValues
import android.database.Cursor
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import cn.pedant.SweetAlert.SweetAlertDialog
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.activity_detail.*
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.adapter.SectionsPagerAdapter
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion._ID
import net.prabowoaz.githubfinderuser.database.QueryHelper
import net.prabowoaz.githubfinderuser.model.ItemUsers
import net.prabowoaz.githubfinderuser.view.FragmentFollowers
import net.prabowoaz.githubfinderuser.view.FragmentFollowing
import net.prabowoaz.githubfinderuser.viewmodel.DetailViewModel
import net.prabowoaz.githubfinderuser.widget.ImageFavoriteWidget


class DetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var detailViewModel: DetailViewModel

    companion object {
        const val EXTRA_STATE = "extra_state"
        const val SIZE = 250
    }

    private lateinit var dbHelper: QueryHelper
    private var statusFavorite = false

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        dbHelper = QueryHelper.getInstance(applicationContext)
        dbHelper.open()

        iv_back.setOnClickListener {
            finish()
        }

        fabFavorite.setOnClickListener(this)

        val detailUsers = intent.getParcelableExtra(EXTRA_STATE) as ItemUsers
        val cursor: Cursor = dbHelper.queryById(detailUsers.id.toString())
        if (cursor.moveToFirst()) {
            statusFavorite = true
            setStatusFavorite(true)
        }

        detailViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(DetailViewModel::class.java)

        detailViewModel.setDetailUsers(detailUsers.login)
        detailViewModel.getDetailUser().observe(this, Observer { githubUser ->
            if (githubUser != null) {
                tv_detailName.text = githubUser.name
                tv_detailUname.text = getString(R.string.at) + githubUser.login
                tv_detailCompany.text = githubUser.company
                tv_detailLocation.text = githubUser.location
                tv_following.text = githubUser.following.toString() + getString(R.string.tFollowing)
                tv_followers.text = githubUser.followers.toString() + getString(R.string.tFollowers)
                tv_repos.text = githubUser.repos.toString() + getString(R.string.tRespository)
                Glide.with(this)
                    .load(githubUser.avatar)
                    .apply(RequestOptions().override(SIZE, SIZE))
                    .placeholder(R.drawable.user_pic)
                    .into(iv_avatar)
            }
        })

        val sectionsPagerAdapter = SectionsPagerAdapter(this, supportFragmentManager)
        sectionsPagerAdapter.setData(detailUsers.login)
        view_pager.adapter = sectionsPagerAdapter
        tabs_follow.setupWithViewPager(view_pager)

        val bundle = Bundle()
        val followingFragment = FragmentFollowing()
        bundle.putString(FragmentFollowing.PARCEL_FOLLOWING, detailUsers.login)
        followingFragment.arguments = bundle

        val followersFragment = FragmentFollowing()
        bundle.putString(FragmentFollowers.PARCEL_FOLLOWERS, detailUsers.login)
        followersFragment.arguments = bundle
    }

    override fun onClick(v: View?) {
        val data = intent.getParcelableExtra(EXTRA_STATE) as ItemUsers
        when (v?.id) {
            R.id.fabFavorite -> {
                setStatusFavorite(false)
                if (!statusFavorite) {
                    val values = ContentValues()
                    values.put(_ID, data.id)
                    values.put(LOGIN, data.login)
                    values.put(AVATAR_URL, data.avatar)
                    values.put(URL, data.url)
                    values.put(FAVORITE, 1)
                    statusFavorite = false
                    dbHelper.insert(values)
                    statusFavorite = !statusFavorite
                    setStatusFavorite(statusFavorite)
                    val appWidgetManager = AppWidgetManager.getInstance(this)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, ImageFavoriteWidget::class.java))
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(getString(R.string.berhasil))
                        .setContentText("${data.login} " + getString(R.string.add_to_favorite))
                        .show()
                } else {
                    val idUser = data.id.toString()
                    dbHelper.deleteById(idUser)
                    statusFavorite = !statusFavorite
                    setStatusFavorite(statusFavorite)
                    Toast.makeText(this, "${data.login} " + getString(R.string.delete_from_fav), Toast.LENGTH_SHORT).show()
                    val appWidgetManager = AppWidgetManager.getInstance(this)
                    val appWidgetIds = appWidgetManager.getAppWidgetIds(ComponentName(this, ImageFavoriteWidget::class.java))
                    appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.stack_view)
                    SweetAlertDialog(this, SweetAlertDialog.SUCCESS_TYPE)
                        .setTitleText(getString(R.string.berhasil))
                        .setContentText("${data.login} " + getString(R.string.delete_from_fav))
                        .show()
                }
            }
        }
    }

    private fun setStatusFavorite(status: Boolean) {
        if (status) {
            fabFavorite.setImageResource(R.drawable.ic_full_fav)
        } else {
            fabFavorite.setImageResource(R.drawable.ic_border_fav)
        }
    }
}