package net.prabowoaz.githubfinderuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.AVATAR_URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.FAVORITE
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.LOGIN
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.TABLE_NAME
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion.URL
import net.prabowoaz.githubfinderuser.database.DatabaseContract.FavoriteColumns.Companion._ID

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoriteuser"
        private const val DATABASE_VERSION = 2
        private val SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE $TABLE_NAME" +
                "(${_ID} INTEGER PRIMARY KEY AUTOINCREMENT," +
                "${LOGIN} TEXT NOT NULL," +
                "${AVATAR_URL} TEXT NOT NULL," +
                "${URL} TEXT NOT NULL," +
                "${FAVORITE} INTEGER NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

}