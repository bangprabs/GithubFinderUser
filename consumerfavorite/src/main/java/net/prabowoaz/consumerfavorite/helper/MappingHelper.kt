package net.prabowoaz.consumerfavorite.helper

import android.database.Cursor
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion.URL
import net.prabowoaz.consumerfavorite.database.DatabaseContract.FavoriteColumns.Companion._ID
import net.prabowoaz.consumerfavorite.model.ItemFavorite

object MappingHelper {

    fun mapCursorToArrayList(favoriteCusrsor: Cursor?): ArrayList<ItemFavorite> {
        val favoriteList = ArrayList<ItemFavorite>()

        favoriteCusrsor?.apply {
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