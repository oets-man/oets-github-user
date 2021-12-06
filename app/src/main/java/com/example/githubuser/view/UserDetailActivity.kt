package com.example.githubuser.view

import android.app.Dialog
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.githubuser.R
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.example.githubuser.model.UserDetailResponse
import com.example.githubuser.model.UserResponseItem
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {
    private var _binding: ActivityUserDetailBinding? = null
    private val binding get() = _binding!!
    private lateinit var data: UserResponseItem

    private lateinit var viewModel: UserDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        viewModel = ViewModelProvider(this)[UserDetailViewModel::class.java]

        data = intent.getParcelableExtra<UserResponseItem>(EXTRA_USER) as UserResponseItem

        setTabViewPager()
        viewModel.loadDetailUser(data.login ?: "")
        viewModel.getDetailUser().observe(this, {
            showUser(it)
        })
    }

    private fun showUser(user: UserDetailResponse) {
        val orange = Color.rgb(255, 165, 0)
        binding.apply {
            Glide.with(this@UserDetailActivity)
                .load(user.avatarUrl)
                .error(R.drawable.error_image)
                .placeholder(R.drawable.waiting_image)
                .into(imgDetailAvatar)

            imgDetailAvatar.borderWidth = 10
            imgDetailAvatar.borderColor = orange
            tvDetailNama.text = user.name
            tvDetailUser.text = user.login
            tvDetailLokasi.text = StringBuilder("Location: ${user.location}")
            tvDetailCompany.text = StringBuilder("Company: ${user.company}")
            tvDetailRepository.text = StringBuilder("Repository: ${user.publicRepos}")
            tvDetailFollower.text = StringBuilder("Follower: ${user.followers}")
            tvDetailFollowing.text = StringBuilder("Following: ${user.following}")
            imgDetailAvatar.setOnClickListener(this@UserDetailActivity)
        }
        binding.progressBarDetail.visibility = View.GONE
    }

    private fun setTabViewPager() {
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.img_detail_avatar -> {
                val dialog = Dialog(this)
                dialog.setContentView(R.layout.dialog_user_avatar)
                val imageView = dialog.findViewById<ImageView>(R.id.imageView)
                Glide.with(this)
                    .load(data.avatarUrl)
                    .error(R.drawable.error_image)
                    .placeholder(R.drawable.waiting_image)
                    .into(imageView)
                dialog.show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        _binding = null
    }

    companion object {
        const val EXTRA_USER = "extra_user"

        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2
        )
    }
}
