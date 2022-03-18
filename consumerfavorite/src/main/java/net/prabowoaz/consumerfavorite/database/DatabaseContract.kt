package net.prabowoaz.consumerfavorite.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "net.prabowoaz.githubuserfinder"
    const val SCHEME = "content"

    class FavoriteColumns : BaseColumns {

        companion object {
            const val TABLE_NAME = "tableFavorite"
            const val _ID = "_id"
            const val LOGIN = "login"
            const val AVATAR_URL = "avatar_url"
            const val URL = "url"
            const val FAVORITE = "favorite"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}