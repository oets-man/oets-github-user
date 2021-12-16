package com.example.githubuser.follower

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowerBinding
import com.example.githubuser.model.UserResponseItem
import com.example.githubuser.detail.UserDetailActivity
import com.example.githubuser.UserAdapter
import com.example.githubuser.favorite.FavoriteEntity

class FollowerFragment : Fragment() {
    private var _binding: FragmentFollowerBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowersViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FollowersViewModel::class.java]

        binding.rvFollower.layoutManager = LinearLayoutManager(activity)

        val dataUser = activity?.intent?.getParcelableExtra<UserResponseItem>(UserDetailActivity.EXTRA_USER) as UserResponseItem

        viewModel.loadFollowers(dataUser.login)

        viewModel.isLoading().observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getFollowers().observe(viewLifecycleOwner, {
            val adapter = UserAdapter(it)
            binding.rvFollower.adapter = adapter
            adapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponseItem) { showSelectedUser(data) }
            })
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        startActivity(
            Intent(context, UserDetailActivity::class.java).putExtra(
                UserDetailActivity.EXTRA_USER,
                user
            )
        )
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollower.visibility = View.VISIBLE
        } else {
            binding.progressBarFollower.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

