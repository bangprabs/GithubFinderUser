package net.prabowoaz.githubfinderuser.activity

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.LinearInterpolator
import android.widget.ProgressBar
import androidx.appcompat.app.AppCompatActivity
import net.prabowoaz.githubfinderuser.MainActivity
import net.prabowoaz.githubfinderuser.R


class SplashActivity : AppCompatActivity() {

    private val LONGTIME: Long = 2200

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        setProgressAnimate()

        Handler().postDelayed({
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }, LONGTIME)
    }

    private fun setProgressAnimate() {
        val mProgressBar = findViewById<ProgressBar>(R.id.splash_screen_progress_bar)
        val progressAnimator =
            ObjectAnimator.ofInt(mProgressBar, "progress", 0, 100)
        progressAnimator.duration = 1000
        progressAnimator.interpolator = LinearInterpolator()
        progressAnimator.start()
    }
}