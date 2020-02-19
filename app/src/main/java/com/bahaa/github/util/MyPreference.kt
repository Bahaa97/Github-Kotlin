package com.bahaa.github.util

import android.content.Context
import android.content.SharedPreferences

import com.bahaa.github.MyApplication
import com.google.gson.Gson

import org.json.JSONArray
import org.json.JSONException

class MyPreference(private val gson: Gson) {
    private val KEY_TOKEN = "token"
    private val LOGIN = "login"
    private val SCREEN = "screen"
    private val NOTIFICATIONS = "notifications"
    private val BOOKS = "MY_BOOKS"
    private val SECRET_KEY = "SECRET_KEY"
    private val HAS_SHIPPING = "HAS_SHIPPING"
    private val CART_COUNT = "CART_COUNT"
    private val SEARCH_HISTORY = "searchHistory"
    private val editor: SharedPreferences.Editor
    private val preferences: SharedPreferences
    val intro: Boolean
        get() = preferences.getBoolean(INTRO, false)
    var userName: String?
        get() = preferences.getString(KEY_NAME, "")
        set(name) {
            editor.putString(KEY_NAME, name)
            editor.commit()
        }
    var firstName: String?
        get() = preferences.getString(KEY_FIRST_NAME, "")
        set(name) {
            editor.putString(KEY_FIRST_NAME, name)
            editor.commit()
        }
    var lastName: String?
        get() = preferences.getString(KEY_LAST_NAME, "")
        set(name) {
            editor.putString(KEY_LAST_NAME, name)
            editor.commit()
        }
    var userEmail: String?
        get() = preferences.getString(KEY_MAIL, "")
        set(name) {
            editor.putString(KEY_MAIL, name)
            editor.commit()
        }
    var userPhone: String?
        get() = preferences.getString(KEY_PHONE, "")
        set(name) {
            editor.putString(KEY_PHONE, name)
            editor.commit()
        }

    val isLogin: Boolean
        get() = preferences.getBoolean(LOGIN, false)
    var news: Boolean
        get() = preferences.getBoolean(NEWS, false)
        set(b) {
            editor.putBoolean(NEWS, b)
            editor.commit()
        }

    val isNotificationsEnabled: Boolean
        get() = preferences.getBoolean(NOTIFICATIONS, true)

    val savedBooks: String?
        get() = preferences.getString(BOOKS, "")

    val isStayAwake: Boolean
        get() = preferences.getBoolean(SCREEN, false)

    var secreT_KEY: String?
        get() = preferences.getString(SECRET_KEY, "")
        set(key) {
            editor.putString(SECRET_KEY, key)
            editor.commit()
        }

    var isHasShipping: Boolean
        get() = preferences.getBoolean(HAS_SHIPPING, true)
        set(hasShipping) {
            editor.putBoolean(HAS_SHIPPING, hasShipping)
            editor.commit()
        }

    var cartCount: Int
        get() = preferences.getInt(CART_COUNT, 0)
        set(count) {
            editor.putInt(CART_COUNT, count)
            editor.commit()
        }


    var language: String?
        get() = preferences.getString("language", "ar")
        set(lang) {
            editor.putString("language", lang)
            editor.commit()
        }

    init {
        preferences = MyApplication.context.getSharedPreferences("OpenCart", Context.MODE_PRIVATE)
        editor = preferences.edit()
        editor.apply()
    }


    fun login() {
        editor.putBoolean(LOGIN, true)
        editor.commit()
    }

    fun finishIntro() {
        editor.putBoolean(INTRO, true)
        editor.commit()
    }

    fun setNotifications(b: Boolean) {
        editor.putBoolean(NOTIFICATIONS, b)
        editor.commit()
    }

    fun setScreen(b: Boolean) {
        editor.putBoolean(SCREEN, b)
        editor.commit()
    }

    fun saveBooks(books: String) {
        editor.putString(BOOKS, books)
        editor.commit()
    }

    fun logOut() {
        editor.putBoolean(LOGIN, false)
        editor.remove(KEY_TOKEN)
        editor.remove(KEY_NAME)
        editor.remove(KEY_MAIL)
        editor.remove(KEY_PHONE)
        editor.commit()
    }

    fun deleteSearchHistory() {
        editor.remove(SEARCH_HISTORY)
        editor.commit()
    }



    fun isFirst() {
        editor.putBoolean("isFirst", true)
        editor.commit()
    }

    fun first(): Boolean {
        return preferences.getBoolean("isFirst", false)
    }

    companion object {
        private val KEY_NAME = "userName"
        private val KEY_MAIL = "userEmail"
        private val KEY_PHONE = "phone"
        private val NEWS = "news"
        private val INTRO = "intro"
        private val KEY_FIRST_NAME = "firstName"
        private val KEY_LAST_NAME = "lastName"
    }
}
