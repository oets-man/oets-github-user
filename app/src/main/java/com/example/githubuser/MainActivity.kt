package com.example.githubuser

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.*
import retrofit2.Response

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var listData: ArrayList<UserDetailResponse> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        showUserAll()
//        showRecyclerList()

        supportActionBar?.title = "Cari User GitHub"

        val layoutManager = LinearLayoutManager(this)
        binding.rvUsers.layoutManager = layoutManager
        val itemDecoration = DividerItemDecoration(this, layoutManager.orientation)
        binding.rvUsers.addItemDecoration(itemDecoration)

    }

    private fun showUsersBy(query: String) {
        val client = ApiConfig.getApiService().getUserBy(query)
        client.enqueue(object : Callback<UserSearchResponse> {
            override fun onResponse(
                call: Call<UserSearchResponse>,
                response: Response<UserSearchResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = responseBody.items.toString()
                        Log.d("resultsearch", result)
                        for (i in responseBody.items.indices) {
                            val jsonArray = responseBody.items[i]
                            val login: String = jsonArray.login
                            getUserDetail(login)
                        }
                    }

                }
            }

            override fun onFailure(call: Call<UserSearchResponse>, t: Throwable) {
                Log.e("x", "sialan")
            }
        })

    }

    private fun showUserAll() {
        showLoading(true)
        val client = ApiConfig.getApiService().getUsers()
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
                showLoading(false)
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        val result = responseBody.toString()
                        Log.d("result", result)
                        for (i in responseBody.indices) {
                            val jsonArray = responseBody[i]
                            val login: String = jsonArray.login
                            getUserDetail(login)
                        }
                    }
                } else {
                    Log.e("responnotsuccess", "Mungin kena limit")
                }
            }

            override fun onFailure(call: Call<List<UserResponseItem>>, t: Throwable) {
                showLoading(false)
                Log.e("gagalfind", "onFailure: ${t.message}")
            }
        })
    }

    private fun getUserDetail(login: String) {
        showLoading(true)
        val client = ApiConfig.getApiService().getUserDetail(login)


        client.enqueue(object : Callback<UserDetailResponse> {
            override fun onResponse(
                call: Call<UserDetailResponse>,
                response: Response<UserDetailResponse>
            ) {
                if (response.isSuccessful) {
                    showLoading(false)
                    val responseBody = response.body()
                    setUser(responseBody)
                }
            }

            override fun onFailure(call: Call<UserDetailResponse>, t: Throwable) {
                showLoading(false)
                Log.e("gagallagi", "onFailure: ${t.message}")
            }

            private fun setUser(user: UserDetailResponse?) {
                listData.add(user!!)
                showRecyclerList()
            }
        })
    }


    private fun showRecyclerList() {

        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = UserListAdapter(listData)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserDetailResponse) {
                showSelectedUser(data)
            }
        })
    }


    private fun showSelectedUser(data: UserDetailResponse) {
        val user = UserDetailResponse(
            data.followers,
            data.avatarUrl,
            data.following,
            data.name,
            data.company,
            data.location,
            data.publicRepos,
            data.login
        )
        val move = Intent(this@MainActivity, UserDetailActivity::class.java)
        move.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(move)

        Toast.makeText(
            this,
            "${user.name}",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBar.visibility = View.VISIBLE
        } else {
            binding.progressBar.visibility = View.GONE
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
        searchView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    listData.clear()
                    showUsersBy(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }
}