package com.example.githubuser.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.databinding.AdapterUserListBinding
import com.example.githubuser.model.UserResponseItem

class UserListAdapter(private val listUser: ArrayList<UserResponseItem>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: AdapterUserListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            AdapterUserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.binding.tvName.text = data.login
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.waiting_image)
            .circleCrop()
            .apply(RequestOptions().override(250, 250))
            .into(holder.binding.imgAvatar)

        holder.binding.tvId.text = holder.itemView.context.getString(R.string.id, data.id.toString())
        holder.binding.tvType.text = holder.itemView.context.getString(R.string.type, data.type)

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(data) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserResponseItem)
    }
}


