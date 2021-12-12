package com.example.githubuser.favorite

import androidx.recyclerview.widget.DiffUtil

class FavoriteDiffCallback (private val mOldFavoriteList: List<FavoriteEntity>, private val mNewFavoriteList: List<FavoriteEntity>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavoriteList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavoriteList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavoriteList[oldItemPosition].id == mNewFavoriteList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val old = mOldFavoriteList[oldItemPosition]
        val new = mNewFavoriteList[newItemPosition]
        return old.id == new.id &&
                old.type == new.type &&
                old.avatarUrl == new.avatarUrl &&
                old.login == new.login
    }
}