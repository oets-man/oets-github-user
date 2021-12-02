package com.example.githubuser

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_follower.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class FollowerFragment : Fragment() {
    private var listData: ArrayList<UserDetailResponse> = ArrayList()
    private lateinit var adapter: FollowerAdapter
//    private lateinit var binding: FragmentFollowerBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follower, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        adapter = FollowerAdapter(listData)
        val dataUser = activity?.intent?.getParcelableExtra<UserDetailResponse>(UserDetailActivity.EXTRA_USER) as UserDetailResponse
        Log.d("data user", dataUser.toString())
        Log.d("login", dataUser.login.toString())
        progressBarFollower.visibility = View.GONE

        listData.clear()
        getFollowers(dataUser.login.toString())
    }

    private fun getFollowers(login: String) {
        val client = ApiConfig.getApiService().getFollowers(login)
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

        rv_follower.layoutManager = LinearLayoutManager(activity)
        val listDataAdapter =
            FollowingAdapter(listData)
        rv_follower.adapter = adapter

        listDataAdapter.setOnItemClickCallback(object :
            FollowingAdapter.OnItemClickCallback {

            override fun onItemClicked(data: UserDetailResponse) {
                // nanti dulu
            }
        })
    }


    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            progressBarFollower.visibility = View.VISIBLE
        } else {
            progressBarFollower.visibility = View.GONE
        }
    }

}

