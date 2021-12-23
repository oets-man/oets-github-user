package com.example.githubuser

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.githubuser.main.MainActivity
import com.example.githubuser.setting.ThemePreferences
import com.example.githubuser.setting.ThemeViewModel
import com.example.githubuser.setting.ThemeViewModelFactory
import kotlinx.coroutines.DelicateCoroutinesApi

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

@DelicateCoroutinesApi
@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            val splashScreen = Intent(this, MainActivity::class.java)
            startActivity(splashScreen)
            finish()
        }, TIME)

        showTheme() // MENYEBABKAN LOADING DUA KALI ?????
    }

    private fun showTheme() {
        //ambil data dari view model
        val pref = ThemePreferences.getInstance(dataStore)
        val themeViewModel =
            ViewModelProvider(this, ThemeViewModelFactory(pref))[ThemeViewModel::class.java]
        themeViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
    }

    companion object {
        const val TIME: Long = 2000
    }
}