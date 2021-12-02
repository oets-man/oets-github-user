package com.example.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.AdapterUserListBinding

class UserListAdapter(private val listUser: ArrayList<UserDetailResponse>) :
    RecyclerView.Adapter<UserListAdapter.ListViewHolder>() {
    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: AdapterUserListBinding) :
        RecyclerView.ViewHolder(binding.root) {}

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            AdapterUserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = listUser[position]
        holder.binding.tvUsername.text = data.login
        holder.binding.tvName.text = data.name
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .apply(RequestOptions().override(250, 250))
            .into(holder.binding.imgAvatar)
        holder.binding.tvCompany.text = data.company
        holder.binding.tvLocation.text = data.location

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: UserDetailResponse)
    }
}


