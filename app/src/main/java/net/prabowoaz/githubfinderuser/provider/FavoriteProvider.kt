package net.prabowoaz.githubfinderuser.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import net.prabowoaz.githubfinderuser.database.DatabaseContract.AUTHORITY
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.CONTENT_URI
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import net.prabowoaz.githubfinderuser.database.QueryHelper

class FavoriteProvider : ContentProvider() {

    companion object {
        private const val USER_FAVORITE = 1
        private const val USER_FAVORITE_ID = 2
        private val sUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var queryHelper: QueryHelper

        init {
            sUriMatcher.addURI(AUTHORITY, TABLE_NAME, USER_FAVORITE)
            sUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_FAVORITE_ID)
        }
    }

    override fun onCreate(): Boolean {
        queryHelper = QueryHelper.getInstance(context as Context)
        queryHelper.open()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?, selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        val cursor : Cursor?
        when (sUriMatcher.match(uri)) {
            USER_FAVORITE -> cursor = queryHelper.queryAll()
            USER_FAVORITE_ID -> cursor = queryHelper.queryById(uri.lastPathSegment.toString())
            else -> cursor = null
        }
        return cursor
    }

    override fun getType(uri: Uri): String? {
        return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val added: Long = when(USER_FAVORITE) {
            sUriMatcher.match(uri) -> queryHelper.insert(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI, null)
        return Uri.parse("$CONTENT_URI/$added")
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?, selectionArgs: Array<String>?): Int {
        return 0
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted: Int = when(USER_FAVORITE_ID){
            sUriMatcher.match(uri) -> queryHelper.deleteById(uri.lastPathSegment.toString())
            else -> 0
        }

        context?.contentResolver?.notifyChange(CONTENT_URI, null)

        return deleted
    }
}
