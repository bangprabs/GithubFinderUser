package net.prabowoaz.githubfinderuser.activity

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.widget.CompoundButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_setting.*
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.alarm.AlarmPref
import net.prabowoaz.githubfinderuser.alarm.BroadcastReceiver


class SettingActivity : AppCompatActivity() {

    private lateinit var userPreferences: AlarmPref
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        chg_language.setOnClickListener {
            val mIntent = Intent(Settings.ACTION_LOCALE_SETTINGS)
            startActivity(mIntent)
        }

        iv_backsetting.setOnClickListener {
            finish()
        }

        broadcastReceiver = BroadcastReceiver()
        userPreferences =
            AlarmPref(
                this
            )

        swDaily.isChecked = broadcastReceiver.isAlarmSet(this)
        swDaily.setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                broadcastReceiver.setScheduleAlarm(this)
                userPreferences.setDailyReminder(true)
                Toast.makeText(this, getString(R.string.toast_reminder), Toast.LENGTH_SHORT).show()
            } else {
                broadcastReceiver.cancelAlarm(this)
                userPreferences.setDailyReminder(false)
                Toast.makeText(this, getString(R.string.toast_off_reminder), Toast.LENGTH_SHORT).show()
            }
        })
    }
}