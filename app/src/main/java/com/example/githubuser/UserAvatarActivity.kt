package com.example.githubuser

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.databinding.ActivityUserAvatarBinding

class UserAvatarActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserAvatarBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserAvatarBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        binding.imageView.setImageResource(intent.getIntExtra(EXTRA_AVATAR,0))
        Glide.with(this)
            .load(intent.getStringExtra(EXTRA_AVATAR))
            .into(binding.imageView)
    }

    companion object {
        const val EXTRA_AVATAR = "extra_avatar"
    }
}
