package com.example.githubuser

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val list = ArrayList<Users>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)

        list.addAll(listUser)
        showRecyclerList()
    }

    private val listUser:ArrayList<Users>
    get() {
        val dataAvatar = resources.obtainTypedArray(R.array.data_avatar)
        val dataName = resources.getStringArray(R.array.data_name)
        val dataUsername = resources.getStringArray(R.array.data_username)
        val dataLocation = resources.getStringArray(R.array.data_location)
        val dataCompany = resources.getStringArray(R.array.data_company)
        val dataRepository = resources.getStringArray(R.array.data_repository)
        val dataFollowers = resources.getStringArray(R.array.data_followers)
        val dataFollowing = resources.getStringArray(R.array.data_following)

        val listUsers = ArrayList<Users>()

        for (i in dataName.indices) {
            val user = Users(
                dataAvatar.getResourceId(i,-1),
                dataName[i],
                dataUsername[i],
                dataLocation[i],
                dataCompany[i],
                dataRepository[i],
                dataFollowers[i],
                dataFollowing[i]
            )
            listUsers.add(user)
        }
        return listUsers
    }

    private fun showRecyclerList() {
        if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            binding.rvUsers.layoutManager = GridLayoutManager(this, 2)
        } else {
            binding.rvUsers.layoutManager = LinearLayoutManager(this)
        }
        val listUserAdapter = UserListAdapter(list)
        binding.rvUsers.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Users) {
                showSelectedUser(data)
            }
        })
    }

    private fun showSelectedUser(data: Users) {
        val user = Users(
            data.avatar,
            data.name,
            data.username,
            data.location,
            data.company,
            data.repository,
            data.followers,
            data.following
        )
        val move = Intent(this@MainActivity, UserDetailActivity::class.java)
        move.putExtra(UserDetailActivity.EXTRA_USER, user)
        startActivity(move)
    }
}