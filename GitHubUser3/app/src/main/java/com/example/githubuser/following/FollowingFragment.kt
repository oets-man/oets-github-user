package com.example.githubuser.following

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.githubuser.UserAdapter
import com.example.githubuser.databinding.FragmentFollowingBinding
import com.example.githubuser.detail.UserDetailActivity
import com.example.githubuser.model.UserResponseItem
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
class FollowingFragment : Fragment() {
    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding

    private lateinit var viewModel: FollowingViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[FollowingViewModel::class.java]

        val dataUser = activity?.intent?.getParcelableExtra<UserResponseItem>(UserDetailActivity.EXTRA_USER) as UserResponseItem
        binding?.rvFollowing?.layoutManager = LinearLayoutManager(activity)

        viewModel.loadData(dataUser.login ?: "")

        viewModel.isLoading().observe(viewLifecycleOwner, {
            showLoading(it)
        })

        viewModel.getFollowing().observe(viewLifecycleOwner, {
            binding?.tvDataIsEmpty?.visibility = if(it.isEmpty()) View.VISIBLE else View.INVISIBLE
            val listUserAdapter = UserAdapter(it)
            binding?.rvFollowing?.adapter = listUserAdapter
            listUserAdapter.setOnItemClickCallback(object : UserAdapter.OnItemClickCallback {
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
        binding?.progressBarFollowing?.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}

