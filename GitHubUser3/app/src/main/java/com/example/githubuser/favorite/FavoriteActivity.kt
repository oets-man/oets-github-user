package com.example.githubuser.favorite

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityFavoriteBinding
import com.example.githubuser.detail.UserDetailActivity
import com.example.githubuser.model.UserResponseItem
import kotlinx.coroutines.DelicateCoroutinesApi
import java.nio.file.Files.delete

class FavoriteActivity : AppCompatActivity(), RecyclerViewClickListener {

    private var _binding: ActivityFavoriteBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FavoriteViewModel
    private lateinit var adapter: FavoriteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        title = "List Favorite"

        adapter = FavoriteAdapter()
        binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.adapter = adapter

        val viewModel = obtainViewModel(this)
        viewModel.getAllFavorites().observe(this, { list ->
            if (list != null) {
                adapter.setList(list)
            }
        })

        showRecyclerList()

        // set click listener
        adapter.listenerItem = this
        adapter.listenerTrash = this
    }



    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = FavoriteViewFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(FavoriteViewModel::class.java)
    }

    private fun showRecyclerList() {

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvFavorite?.setHasFixedSize(true)
        binding?.rvFavorite?.layoutManager = layoutManager
        binding?.rvFavorite?.addItemDecoration(itemDecoration)

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.rvFavorite?.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding?.rvFavorite?.layoutManager = LinearLayoutManager(this)
        }
    }


    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    private fun showSelectedUser(user: UserResponseItem) {
        val move = Intent(this, UserDetailActivity::class.java)
        move.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(move)
    }

    override fun onItemClicked(view: View, favorite: FavoriteEntity) {
        val id = favorite.id.toString().toLong()
        Toast.makeText(
            this,
            "${favorite.login} ($id) berhasil di klik",
            Toast.LENGTH_SHORT
        ).show()

        val user = UserResponseItem()
        user.id = favorite.id.toString().toLong()
        user.login = favorite.login.toString()
        user.avatarUrl = favorite.avatarUrl.toString()
        user.type = favorite.type.toString()

        showSelectedUser(user)

    }

    override fun onTrashClicked(view: View, favorite: FavoriteEntity) {
        // toas sukses
        Toast.makeText(
            this,
            "tes siapa yang tampil ${favorite.login}",
            Toast.LENGTH_SHORT
        ).show()
//        viewModel.delete(favorite)

        val dialogTitle = "Konfirmasi"
        val dialogMessage = "Yakin menghapus ${favorite.login} dari daftar favorit?"

        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton("Ya") { _, _ ->
//                viewModel.deleteById(favorite.id)     //ini gagal
                viewModel.delete(favorite)            //ini juga gagal
            }
            setNegativeButton("Tidak") { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}