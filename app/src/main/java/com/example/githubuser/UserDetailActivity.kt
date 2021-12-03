package com.example.githubuser

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.githubuser.databinding.ActivityUserDetailBinding
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<UserDetailResponse>(EXTRA_USER) as UserDetailResponse

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val orange = Color.rgb(255,165,0)
        binding.apply {
            imgDetailAvatar.loadCircularImage(user.avatarUrl,10F, orange)
            tvDetailNama.text           =user.name
            tvDetailUser.text           =user.login
            tvDetailLokasi.text         =StringBuilder("Location: ${user.location}")
            tvDetailCompany.text        =StringBuilder("Company: ${user.company}")
            tvDetailRepository.text     =StringBuilder("Repository: ${user.publicRepos}")
            tvDetailFollower.text       =StringBuilder("Follower: ${user.followers}")
            tvDetailFollowing.text      =StringBuilder("Following: ${user.following}")
            imgDetailAvatar.setOnClickListener(this@UserDetailActivity)
        }

//        pengaturan tab
        val sectionsPagerAdapter = SectionsPagerAdapter(this)
        val viewPager: ViewPager2 = binding.viewPager
        viewPager.adapter = sectionsPagerAdapter
        val tabs: TabLayout = binding.tabs
        TabLayoutMediator(tabs, viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
//        supportActionBar?.elevation = 0f
        supportActionBar?.hide()  //sepertinya lebih menarik
    }

    override fun onClick(v: View?) {
        val user = intent.getParcelableExtra<UserDetailResponse>(EXTRA_USER) as UserDetailResponse
        when(v?.id){
            R.id.img_detail_avatar -> {
                val showAvatar = Intent(this@UserDetailActivity, UserAvatarActivity::class.java)
                showAvatar.putExtra(UserAvatarActivity.EXTRA_AVATAR, user.avatarUrl)
                startActivity(showAvatar)
            }
        }
    }

    companion object {
        const val EXTRA_USER = "extra_user"
        @StringRes
        private val TAB_TITLES = intArrayOf(
            R.string.tab_text_1,
            R.string.tab_text_2        )
    }
}
