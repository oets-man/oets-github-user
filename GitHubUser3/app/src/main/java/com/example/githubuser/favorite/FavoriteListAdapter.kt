package com.example.githubuser.favorite

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.UserListAdapter
import com.example.githubuser.databinding.AdapterFavoriteListBinding
import com.example.githubuser.databinding.AdapterUserListBinding
import com.example.githubuser.model.UserResponseItem

class FavoriteListAdapter(private val list: ArrayList<FavoriteEntity>) :
    RecyclerView.Adapter<FavoriteListAdapter.ListViewHolder>() {

    class ListViewHolder(var binding: AdapterFavoriteListBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            AdapterFavoriteListBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteListAdapter.ListViewHolder, position: Int) {
        val data = list[position]
        holder.binding.include.tvName.text = data.login
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.waiting_image)
            .circleCrop()
            .apply(RequestOptions().override(250, 250))
            .into(holder.binding.include.imgAvatar)

        holder.binding.include.tvId.text =
            holder.itemView.context.getString(R.string.id, data.id.toString())
        holder.binding.include.tvType.text =
            holder.itemView.context.getString(R.string.type, data.type)
    }

    override fun getItemCount(): Int = list.size

}

