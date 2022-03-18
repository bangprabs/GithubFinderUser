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

class MainAdapter : RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    private val dataUsers = ArrayList<ItemUsers>()
    private lateinit var onItemClickCallback: OnItemClickCallback
    companion object {
        const val SIZE = 250
    }

    fun setData(items: ArrayList<ItemUsers>) {
        dataUsers.clear()
        dataUsers.addAll(items)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, position: Int): MainViewHolder {
        val mView = LayoutInflater.from(viewGroup.context).inflate(R.layout.user_row_items, viewGroup, false)
        return MainViewHolder(mView)
    }

    override fun onBindViewHolder(weatherViewHolder: MainViewHolder, position: Int) {
        weatherViewHolder.bind(dataUsers[position])
    }

    override fun getItemCount(): Int = dataUsers.size

    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(itemUsers)
                }
            }
        }
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: ItemUsers)
    }
}