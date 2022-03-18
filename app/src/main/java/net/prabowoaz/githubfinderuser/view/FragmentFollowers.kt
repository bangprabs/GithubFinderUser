package net.prabowoaz.githubfinderuser.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_followers.*
import kotlinx.android.synthetic.main.fragment_following.progressBar
import net.prabowoaz.githubfinderuser.viewmodel.FollowersViewModel
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.adapter.FollowersAdapter

class FragmentFollowers : Fragment() {

    private lateinit var adapter: FollowersAdapter
    private lateinit var followersViewModel: FollowersViewModel

    companion object {
        const val PARCEL_FOLLOWERS = "key_followers"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_followers, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowersAdapter()
        showRecyclerView()

        followersViewModel = ViewModelProvider(
            this,
            ViewModelProvider.NewInstanceFactory()
        ).get(FollowersViewModel::class.java)

        if (arguments != null) {
            val username = arguments?.getString(PARCEL_FOLLOWERS)
            showLoading(true)
            followersViewModel.setFollowingGithubUser(username.toString(), context)
        }


        followersViewModel.getWeathers().observe(viewLifecycleOwner, Observer { githubUser ->
            if (githubUser != null) {
                adapter.setData(githubUser)
                showLoading(false)
            }
        })
    }

    private fun showRecyclerView() {
        rv_followers_github_user.layoutManager = LinearLayoutManager(context)
        rv_followers_github_user.adapter = adapter
        adapter.notifyDataSetChanged()
    }


    private fun showLoading(state: Boolean) {
        if (state) {
            progressBar.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
        }
    }
}

