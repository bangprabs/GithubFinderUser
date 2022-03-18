package net.prabowoaz.consumerfavorite.adapter

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.weather_row_items.view.*
import net.prabowoaz.consumerfavorite.R
import net.prabowoaz.consumerfavorite.model.ItemFavorite
import net.prabowoaz.consumerfavorite.model.ItemUsers

class FavoriteAdapter(private val activity:Activity) : RecyclerView.Adapter<FavoriteAdapter.FavoriteViewHolder>() {

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
        val view = LayoutInflater.from(parent.context).inflate(R.layout.weather_row_items, parent, false)
        return FavoriteViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listFavorite.size
    }

    override fun onBindViewHolder(holder: FavoriteViewHolder, position: Int) {
        holder.bind(listFavorite[position])
    }

    inner class FavoriteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(itemFavorite: ItemFavorite) {
            with(itemView) {
                textCity.text = itemFavorite.avatar
                textUrl.text = itemFavorite.url
                Glide.with(itemView.context)
                    .load(itemFavorite.login)
                    .apply(RequestOptions().override(250, 250))
                    .placeholder(R.drawable.user_pic)
                    .into(iv_profile)
            }
        }
    }
}