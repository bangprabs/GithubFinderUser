package net.prabowoaz.githubfinderuser.adapter

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.user_row_items.view.*
import net.prabowoaz.githubfinderuser.R
import net.prabowoaz.githubfinderuser.activity.DetailActivity
import net.prabowoaz.githubfinderuser.model.ItemFavorite
import net.prabowoaz.githubfinderuser.model.ItemUsers

class FavoriteAdapter(private val activity:Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

    companion object {
        const val SIZE = 250
    }

     var listFavorite = ArrayList<ItemFavorite>()
        set(listFavorite) {
            if (listFavorite.size > 0) {
                this.listFavorite.clear()
            }
            this.listFavorite.addAll(listFavorite)
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): FavoriteAdapter.FavoriteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_row_items, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
        val data = listFavorite[position]
        holder.itemView.setOnClickListener {
            val dataUsers = ItemUsers(
                data.id,
                data.login,
                data.avatar,
                data.url
            )
            val mIntent = Intent(it.context, DetailActivity::class.java)
            mIntent.putExtra(DetailActivity.EXTRA_STATE, dataUsers)
            it.context.startActivity(mIntent)
        }
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemFavorite: ItemFavorite) {
            with(itemView) {
                textCity.text = itemFavorite.avatar
                textUrl.text = itemFavorite.url
                Glide.with(itemView.context)
                    .load(itemFavorite.login)
                    .apply(RequestOptions().override(SIZE, SIZE))
                    .placeholder(R.drawable.user_pic)
                    .into(iv_profile)
            }
        }

    }
}