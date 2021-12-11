package com.example.githubuser.setting

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/*
Perlu diketahui, kita tidak bisa membuat ViewModel dengan constructor secara langsung.
Untuk itu, kita perlu membuat class yang extend ke ViewModelProvider
untuk bisa memasukkan constructor ke dalam ViewModel.
 */
//class ThemeViewModelFactory(private val pref: ThemePreferences) : ViewModelProvider.NewInstanceFactory() {
//
//    @Suppress("UNCHECKED_CAST")
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
//            return ThemeViewModel(pref) as T
//        }
//        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
//    }
//}

/*
Dengan ViewModelFactory, Anda dapat memasukkan constructor dengan cara mengirim data ke VIewModelFactory terlebih dahulu,
baru setelah itu dikirimkan ke ViewModel pada fungsi create.
 */


class ThemeViewModelFactory(private val pref: ThemePreferences) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ThemeViewModel::class.java)) {
            return ThemeViewModel(pref) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }


}