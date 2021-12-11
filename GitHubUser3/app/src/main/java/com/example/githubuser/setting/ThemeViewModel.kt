package com.example.githubuser.setting

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/*
Di sini kita akan mengubah data stream berupa Flow/Flowable menjadi LiveData
 */
class ThemeViewModel(private val pref: ThemePreferences) : ViewModel() {

    /*
    Untuk mengambil data yang sudah disimpan, kita menggunakan fungsi map pada variabel data.
    Pastikan Anda menggunakan key yang sama dengan saat Anda menyimpannya untuk mendapatkan data yang tepat.
    Selain itu, Anda juga dapat menambahkan elvis operator untuk memberikan nilai default jika datanya masih kosong/null.

    Perlu diperhatikan juga bahwa nilai kembalian dari fungsi ini berupa Flow.
    Flow merupakan salah satu bagian dari Coroutine yang digunakan untuk mengambil data secara berkelanjutan (data stream) dengan jumlah yang banyak.
    Flow sering digunakan untuk membuat reactive programming yang akan dipelajari lebih lanjut pada kelas Expert.
    Untuk saat ini, karena keluaran dari DataStore masih berupa Flow, maka kita perlu mengubahnya menjadi LiveData pada VIewModel dengan cara seperti berikut:

    asLiveData merupakan fungsi yang digunakan untuk mengubah Flow menjadi LiveData.
    Anda dapat melakukan ini karena telah menambahkan library lifecycle-livedata-ktx sebelumnya di awal latihan.
     */
    fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    /*
    Pada MainViewModel kita menggunakan viewModelScopeuntuk menjalankan suspend function seperti berikut:
     */
    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
    /*
    viewModelScope merupakan scope yang sudah disediakan library lifecycle-viewmodel-ktx untuk menjalankan coroutine pada ViewModel yang sudah aware dengan lifecycle.
    Dengan begitu instance coroutine akan otomatis dihapus ketika ViewModel dibersihkan sehingga aplikasi tidak mengalami memory leak (kebocoran memori).
    Selanjutnya di sini kita menggunakan method launch karena kita akan memulai background process tanpa nilai kembalian alias fire and forget.
     */
}