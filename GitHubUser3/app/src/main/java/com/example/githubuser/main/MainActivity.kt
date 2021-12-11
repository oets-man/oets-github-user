package com.example.githubuser.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityMainBinding
import com.example.githubuser.setting.*
import com.example.githubuser.detail.UserDetailActivity
import com.example.githubuser.UserListAdapter
import com.example.githubuser.model.UserResponseItem
import com.google.android.material.snackbar.Snackbar
import retrofit2.*

//private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private var _binding: ActivityMainBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        supportActionBar?.title = "Cari User GitHub"

        val layoutManager = LinearLayoutManager(this)
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding?.rvUsers?.setHasFixedSize(true)
        binding?.rvUsers?.layoutManager = layoutManager
        binding?.rvUsers?.addItemDecoration(itemDecoration)

        showRecyclerList()

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.showUsersBy("sidi")
        viewModel.getUsers().observe(this, {
            val listUserAdapter = UserListAdapter(it.items)
            binding?.rvUsers?.adapter = listUserAdapter
            listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponseItem) {
                    showSelectedUser(data)
                }
            })
        })

        viewModel.isLoading().observe(this, {
            showLoading(it)
        })

//        showTheme() // MENYEBABKAN LOADING DUA KALI ?????

        snackBar()

    }

    private fun snackBar() {
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        viewModel.snackbarText.observe(this, {
            it.getContentIfNotHandled()?.let { snackBarText ->
                Snackbar.make(
                    window.decorView.rootView,
                    snackBarText,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        })
    }

    private fun showTheme() {
        //ambil data dari view model
//        val pref = ThemePreferences.getInstance(dataStore)
//        val themeViewModel =
//            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]
//        themeViewModel.getThemeSettings().observe(this,
//            { isDarkModeActive: Boolean ->
//                if (isDarkModeActive) {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
//                } else {
//                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//                }
//            })
    }


    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding?.rvUsers?.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding?.rvUsers?.layoutManager = LinearLayoutManager(this)
        }
    }

    private fun showSelectedUser(user: UserResponseItem) {
        val move = Intent(this@MainActivity, UserDetailActivity::class.java)
        move.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(move)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding?.progressBar?.visibility = View.VISIBLE
        } else {
            binding?.progressBar?.visibility = View.GONE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView =
            menu.findItem(R.id.search).actionView as androidx.appcompat.widget.SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_hint)
        searchView.setOnQueryTextListener(object : OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    viewModel.showUsersBy(query)
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.switch_theme) {
            val intent = Intent(
                this, ThemeActivity::class.java
            )
            startActivity(intent)
        }
        return true
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }
}