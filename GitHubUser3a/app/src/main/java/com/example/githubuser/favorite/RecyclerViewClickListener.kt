package com.example.githubuser.favorite

import android.view.View

interface RecyclerViewClickListener {

    // method yang akan dipanggil di MainActivity
    fun onItemClicked(view: View, favorite: FavoriteEntity)
    fun onTrashClicked(view: View, favorite: FavoriteEntity)

}
