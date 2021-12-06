package com.example.githubuser.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.model.UserResponseItem


class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]

        val dataUser = activity?.intent?.getParcelableExtra<UserResponseItem>(UserDetailActivity.EXTRA_USER) as UserResponseItem
        binding.rvFollowing.layoutManager = LinearLayoutManager(activity)

        viewModel.loadData(dataUser.login ?: "")

        viewModel.isLoading().observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getFollowing().observe(viewLifecycleOwner, {
            val listUserAdapter = UserListAdapter(it)
            binding.rvFollowing.adapter = listUserAdapter
            listUserAdapter.setOnItemClickCallback(object : UserListAdapter.OnItemClickCallback {
                override fun onItemClicked(data: UserResponseItem) {
                    showSelectedUser(data)
                }
            })
        })
    }

    private fun showSelectedUser(user: UserResponseItem) {
        requireActivity().run {
            startActivity(Intent(this, UserDetailActivity::class.java).putExtra(
                UserDetailActivity.EXTRA_USER,user))
            finish()
        }
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading) {
            binding.progressBarFollowing.visibility = View.VISIBLE
        } else {
            binding.progressBarFollowing.visibility = View.GONE
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

