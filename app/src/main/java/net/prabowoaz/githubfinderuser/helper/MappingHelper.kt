package net.prabowoaz.githubfinderuser.helper

import android.database.Cursor
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion._ID
import net.prabowoaz.githubfinderuser.model.ItemFavorite

object MappingHelper {

    fun mapCursorToArrayList(favoriteCursor: Cursor?): ArrayList<ItemFavorite> {
        val favoriteList = ArrayList<ItemFavorite>()

        favoriteCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val login = getString(getColumnIndexOrThrow(LOGIN))
                val avatar = getString(getColumnIndexOrThrow(AVATAR_URL))
                val url = getString(getColumnIndexOrThrow(URL))
                val favorite = getInt(getColumnIndexOrThrow(FAVORITE))

                favoriteList.add(
                    ItemFavorite(
                        id,
                        login,
                        avatar,
                        url,
                        favorite
                    )
                )
            }
        }
        return favoriteList
    }
}