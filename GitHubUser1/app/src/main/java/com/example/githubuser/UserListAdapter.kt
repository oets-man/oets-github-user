package com.example.githubuser

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.githubuser.databinding.AdapterUserListBinding

class UserListAdapter(private val listUser: ArrayList<Users>) : RecyclerView.Adapter<UserListAdapter.ListViewHolder>(){
    private lateinit var onItemClickCallback: OnItemClickCallback

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    class ListViewHolder(var binding: AdapterUserListBinding) : RecyclerView.ViewHolder(binding.root) {    }


    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
    val binding = AdapterUserListBinding.inflate(LayoutInflater.from(viewGroup.context), viewGroup, false)
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (avatar, name, username, location,company) = listUser[position]
        holder.binding.imgAvatar.setImageResource(avatar)
        holder.binding.tvName.text = name
        holder.binding.tvUsername.text = username
        holder.binding.tvLocation.text = location
        holder.binding.tvCompany.text = company

        holder.itemView.setOnClickListener { onItemClickCallback.onItemClicked(listUser[holder.bindingAdapterPosition]) }
    }

    override fun getItemCount(): Int = listUser.size

    interface OnItemClickCallback {
        fun onItemClicked(data: Users)
    }
}
