package net.prabowoaz.githubfinderuser.adapter

import android.content.Context
import android.os.Bundle
import androidx.annotation.Nullable
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.view.FragmentFollowers
import net.prabowoaz.githubfinderuser.view.FragmentFollowing

class SectionsPagerAdapter(private val context: Context, fm : FragmentManager) : FragmentPagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    var login : String? = "login"

    @StringRes
    private val titleTabLayout = intArrayOf(R.string.tab_followers, R.string.tab_following)

    fun setData(usernameGithub : String){
        login = usernameGithub
    }

    private fun getData(): String? {
        return login
    }

    override fun getItem(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> {
                fragment = FragmentFollowers()
                val bundle = Bundle()
                bundle.putString(FragmentFollowers.PARCEL_FOLLOWERS, getData())
                fragment.arguments = bundle
            }
            1 -> {

                fragment = FragmentFollowing()
                val bundle = Bundle()
                bundle.putString(FragmentFollowing.PARCEL_FOLLOWING, getData())
                fragment.arguments = bundle
            }
        }
        return fragment as Fragment
    }

    @Nullable
    override fun getPageTitle(position: Int): CharSequence? {
        return context.resources.getString(titleTabLayout[position])
    }

    override fun getCount(): Int {
        return 2
    }
}