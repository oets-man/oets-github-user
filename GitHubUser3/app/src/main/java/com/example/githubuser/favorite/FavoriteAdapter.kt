package com.example.githubuser.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.databinding.AdapterUserBinding


class FavoriteAdapter : RecyclerView.Adapter<FavoriteAdapter.ListViewHolder>() {
    private val list = ArrayList<FavoriteEntity>()

    var listenerTrash: RecyclerViewClickListener? = null
    var listenerItem: RecyclerViewClickListener? = null

    class ListViewHolder(var binding: AdapterUserBinding) :
        RecyclerView.ViewHolder(binding.root)


    fun setList(list: List<FavoriteEntity>) {
        val diffCallback = FavoriteDiffCallback(this.list, list)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.list.clear()
        this.list.addAll(list)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ListViewHolder {
        val binding =
            AdapterUserBinding.inflate(
                LayoutInflater.from(viewGroup.context),
                viewGroup,
                false
            )
        return ListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val data = list[position]
        holder.binding.tvName.text = data.login
        Glide.with(holder.itemView.context)
            .load(data.avatarUrl)
            .error(R.drawable.error_image)
            .placeholder(R.drawable.waiting_image)
            .circleCrop()
            .apply(RequestOptions().override(250, 250))
            .into(holder.binding.imgAvatar)

        holder.binding.tvId.text =
            holder.itemView.context.getString(R.string.id, data.id.toString())
        holder.binding.tvType.text =
            holder.itemView.context.getString(R.string.type, data.type)
        holder.binding.trash.visibility = View.VISIBLE


        holder.itemView.setOnClickListener { listenerItem?.onItemClicked(it, list[position]) }

        holder.binding.trash.setOnClickListener { listenerTrash?.onTrashClicked(it, list[position]) }
    }


    override fun getItemCount(): Int = list.size
}

