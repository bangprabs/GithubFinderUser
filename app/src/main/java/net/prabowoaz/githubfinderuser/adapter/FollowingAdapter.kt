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


class FollowingAdapter : RecyclerView.Adapter<FollowingAdapter.FollowingViewHolder>() {
    private val followingData = ArrayList<ItemUsers>()
    companion object {
        const val SIZE = 250
    }

    fun setData(items: ArrayList<ItemUsers>) {
        followingData.clear()
        followingData.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): FollowingViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_items, viewGroup, false)
        return FollowingViewHolder(mView)
    }

    override fun onBindViewHolder(weatherViewHolder: FollowingViewHolder, position: Int) {
        weatherViewHolder.bind(followingData[position])
    }

    override fun getItemCount(): Int = followingData.size

    inner class FollowingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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