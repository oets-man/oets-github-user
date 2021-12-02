package com.example.githubuser

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_follower.*
import kotlinx.android.synthetic.main.fragment_following.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowingFragment : Fragment() {
    private var listData: ArrayList<UserDetailResponse> = ArrayList()
    private lateinit var adapter: UserListAdapter

    //    private lateinit var binding: FragmentFollowerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_following, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = UserListAdapter(listData)
        val dataUser =
            activity?.intent?.getParcelableExtra<UserDetailResponse>(UserDetailActivity.EXTRA_USER) as UserDetailResponse
        Log.d("data user", dataUser.toString())
        Log.d("login", dataUser.login.toString())
        progressBarFollowing.visibility = View.GONE

        listData.clear()
        getFollowing(dataUser.login.toString())
    }

    private fun getFollowing(login: String) {
        val client = ApiConfig.getApiService().getFollowing(login)
        client.enqueue(object : Callback<List<UserResponseItem>> {
            override fun onResponse(
                call: Call<List<UserResponseItem>>,
                response: Response<List<UserResponseItem>>
            ) {
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
                Log.e("x", "sialan")
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
        rv_following.layoutManager = LinearLayoutManager(activity)
        rv_following.adapter = adapter
        val listUserAdapter = UserListAdapter(listData)
        rv_following.adapter = listUserAdapter

        listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
            override fun onItemClicked(data: UserDetailResponse) {
                showSelectedUser(data)
            }

            private fun showSelectedUser(data: UserDetailResponse) {
//                Toast.makeText(activity, data.login, Toast.LENGTH_SHORT).show()

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
                requireActivity().run {
                    startActivity(Intent(this, UserDetailActivity::class.java).putExtra(UserDetailActivity.EXTRA_USER,user))
                    finish() // If activity no more needed in back stack
                }
            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarFollowing.visibility = View.VISIBLE
        } else {
            progressBarFollowing.visibility = View.GONE
        }
    }

}

