package com.example.githubuser.setting

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemePreferences private constructor(private val dataStore: DataStore<Preferences>) {

    /*
    membaca dan menyimpan data pengaturan tema yang berupa Boolean ke dalam DataStore
    Untuk menyimpan data, yang Anda perlukan hanyalah instance DataStore dan Key pada SettingPreferences.
     */
    private val THEME_KEY = booleanPreferencesKey("theme_setting")
    fun getThemeSetting(): Flow<Boolean> {
        return dataStore.data.map { preferences ->
            preferences[THEME_KEY] ?: false
        }
    }

    /*
    Untuk menyimpan data, kita menggunakan fungsi lambda edit dengan parameter preferences yang merupakan MutablePrefereces.
    Untuk mengubah data, Anda perlu menentukan key data yang ingin diubah dan isi datanya.
    Selain itu, karena edit adalah suspend function, maka ia harus dijalankan di coroutine atau suspend function juga.
     */
    suspend fun saveThemeSetting(isDarkModeActive: Boolean) {
        dataStore.edit { preferences ->
            preferences[THEME_KEY] = isDarkModeActive
        }
    }

    /*
    Perlu diketahui bahwa instance DataStore harus berupa Singleton. Singleton hanya memperbolehkan ada satu instance yang digunakan di banyak tempat.
    Karena itu untuk membuat SettingPreference, kita tidak menggunakan constructor secara langsung,
    melainkan melalui fungsi getInstance yang berfungsi sebagai Singleton seperti berikut:
     */
    companion object {
        @Volatile
        private var INSTANCE: ThemePreferences? = null

        fun getInstance(dataStore: DataStore<Preferences>): ThemePreferences {
            return INSTANCE ?: synchronized(this) {
                val instance = ThemePreferences(dataStore)
                INSTANCE = instance
                instance
            }
        }
    }

/*
Fungsi dari Singleton yaitu dapat menciptakan satu instance saja di dalam JVM, sehingga tidak memakan memori yang terbatas.
Jadi, ketika Activity A memanggil SettingPreferences, kelas itu akan membuat instance dalam bentuk volatile.

Volatile adalah keyword yang digunakan supaya nilai pada suatu variabel tidak dimasukkan ke dalam cache.
Kemudian jika Activity B memanggil fungsi ini, kelas tersebut akan memeriksa apakah instance-nya sudah ada.
Jika tidak null, sistem akan mengembalikan instance tersebut pada Activity B, tidak membuat instance baru.

Khusus di sistem Android, terdapat multi-threading yang bisa menjalankan kode di thread yang berbeda-beda,
sehingga bisa saja instance dibuat di thread yang berbeda. Untuk itulah dibutuhkan kode synchronized untuk membuat semua thread tersinkronisasi.
Dengan cara ini, hanya satu thread yang boleh menjalankan fungsi yang sama di waktu bersamaan.

Hal ini  sangat bermanfaat terutama untuk instance yang berkaitan dengan data.
Apabila ada 2 instance yang hidup dengan data yang berbeda pada setiap instance-nya, akan menyebabkan anomali data yang tidak konsisten.
Untuk itulah Singleton berperan penting di sini.
 */
}