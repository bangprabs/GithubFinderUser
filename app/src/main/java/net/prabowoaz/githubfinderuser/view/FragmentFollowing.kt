package net.prabowoaz.githubfinderuser.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_following.*
import kotlinx.android.synthetic.main.fragment_following.progressBar
import net.prabowoaz.githubfinderuser.viewmodel.FollowingViewModel
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.adapter.FollowingAdapter

class FragmentFollowing : Fragment() {

    private lateinit var adapter: FollowingAdapter
    private lateinit var followingViewModel: FollowingViewModel

    companion object {
        const val PARCEL_FOLLOWING = "key_following"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        adapter = FollowingAdapter()
        showRecyclerView()

        followingViewModel = ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(
            FollowingViewModel::class.java)
        if(arguments != null){
            val username = arguments?.getString(PARCEL_FOLLOWING)
            showLoading(true)
            followingViewModel.setFollowingGithubUser(username.toString(), context)
        }

        followingViewModel.getWeathers().observe(viewLifecycleOwner, Observer { githubUser ->
            if(githubUser != null){
                adapter.setData(githubUser)
                showLoading(false)

            }
        })
    }

    private fun showRecyclerView() {
        rv_following_github_user.layoutManager = LinearLayoutManager(context)
        rv_following_github_user.adapter = adapter
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