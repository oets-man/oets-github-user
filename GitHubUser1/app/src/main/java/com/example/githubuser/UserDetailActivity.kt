package com.example.githubuser

import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.githubuser.databinding.ActivityUserDetailBinding

class UserDetailActivity : AppCompatActivity(), View.OnClickListener {

    companion object {
        const val EXTRA_USER = "extra_user"
    }

    private lateinit var binding: ActivityUserDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val user = intent.getParcelableExtra<Users>(EXTRA_USER) as Users

        binding = ActivityUserDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val orange = Color.rgb(255,165,0)
        binding.imgDetailAvatar.loadCircularImage(user.avatar,10F, orange)
        binding.tvDetailNama.text       =user.name
        binding.tvDetailUser.text       =user.username
        binding.tvDetailLokasi.text     ="Location: " + user.location
        binding.tvDetailCompany.text    ="Company: " + user.company
        binding.tvDetailRepository.text ="Repository: " + user.repository
        binding.tvDetailFollower.text   ="Follower: " + user.followers
        binding.tvDetailFollowing.text  ="Following: " + user.following

        binding.imgDetailAvatar.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val user = intent.getParcelableExtra<Users>(EXTRA_USER) as Users
        when(v?.id){
            R.id.img_detail_avatar -> {
                val showAvatar = Intent(this@UserDetailActivity, UserAvatarActivity::class.java)
                showAvatar.putExtra(UserAvatarActivity.EXTRA_AVATAR, user.avatar)
                startActivity(showAvatar)
            }
        }
    }
}