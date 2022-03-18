package net.prabowoaz.githubfinderuser.widget

import android.content.ClipData
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Bitmap
import android.os.Binder
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import com.bumptech.glide.Glide
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.activity.DetailActivity
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion._ID
import net.prabowoaz.githubfinderuser.database.QueryHelper
import net.prabowoaz.githubfinderuser.helper.MappingHelper
import net.prabowoaz.githubfinderuser.model.ItemFavorite
import net.prabowoaz.githubfinderuser.model.ItemUsers
import java.util.concurrent.ExecutionException

class StackRemoteViewsFactory(private val mContext: Context) :
    RemoteViewsService.RemoteViewsFactory {

    private var listFavorite: Cursor? = null
    private val favoriteList = ArrayList<ItemFavorite>()

    override fun onDataSetChanged() {

        if (listFavorite != null) {
            listFavorite?.close()
        }

        val identityToken = Binder.clearCallingIdentity()
        listFavorite = mContext.contentResolver.query(CONTENT_URI, arrayOf(_ID, "login"), null, null, null)
        val listFav = MappingHelper.mapCursorToArrayList(listFavorite)
        favoriteList.addAll(listFav)
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun hasStableIds(): Boolean = false

    override fun getViewAt(position: Int): RemoteViews {
        val remoteViews = RemoteViews(mContext.packageName, R.layout.widget_item)


        var bitmap: Bitmap? = null
        try {
            bitmap = Glide.with(mContext)
                .asBitmap()
                .load(favoriteList[position].login)
                .submit()
                .get()
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }

        remoteViews.setImageViewBitmap(R.id.imageView, bitmap)
        val extras = Bundle()
        extras.putString(ImageFavoriteWidget.EXTRA_ITEM, favoriteList[position].avatar)
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        remoteViews.setOnClickFillInIntent(R.id.imageView, fillInIntent)
        return remoteViews
    }

    override fun getCount(): Int {
        return listFavorite?.count ?: 0
    }

    override fun getViewTypeCount(): Int = 1

    override fun getLoadingView(): RemoteViews? = null

    override fun onDestroy() {
    }

}
