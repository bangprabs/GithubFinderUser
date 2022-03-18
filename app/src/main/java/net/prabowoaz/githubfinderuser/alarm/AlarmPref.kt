package net.prabowoaz.githubfinderuser.alarm

import android.content.Context

class AlarmPref(context: Context) {

    companion object {
        private const val PREFS_NAME = "user_prefs"
        private const val TITLE_REMINDER = "title_reminder"
    }

    private val preferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun setDailyReminder(state: Boolean){
        val editor = preferences.edit()
        editor.putBoolean(TITLE_REMINDER, state)
        editor.apply()
    }

}