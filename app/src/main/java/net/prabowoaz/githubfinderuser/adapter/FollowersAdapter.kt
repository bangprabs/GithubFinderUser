package net.prabowoaz.githubfinderuser.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.user_row_items.view.*
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.model.ItemUsers

class FollowersAdapter : RecyclerView.Adapter<FollowersAdapter.FollowersViewHolder>() {
    private val followersData = ArrayList<ItemUsers>()

    companion object {
        const val SIZE = 250
    }

    fun setData(items: ArrayList<ItemUsers>) {
        followersData.clear()
        followersData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowersViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_items, viewGroup, false)
        return FollowersViewHolder(mView)
    }

    override fun onBindViewHolder(weatherViewHolder: FollowersViewHolder, position: Int) {
        weatherViewHolder.bind(followersData[position])
    }

    override fun getItemCount(): Int = followersData.size

    inner class FollowersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemUsers: ItemUsers) {
            with(itemView){
                textCity.text = itemUsers.login
                textUrl.text = itemUsers.url
                Glide.with(itemView.context)
                    .load(itemUsers.avatar)
                    .apply(RequestOptions().override(SIZE, SIZE))
                    .placeholder(R.drawable.user_pic)
                    .into(iv_profile)
                cardView.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
                iv_frame.animation = AnimationUtils.loadAnimation(context, R.anim.fade_transition_animation)
                textCity.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
                textUrl.animation = AnimationUtils.loadAnimation(context, R.anim.fade_scale_animation)
            }
        }
    }
}