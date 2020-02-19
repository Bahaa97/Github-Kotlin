package com.bahaa.github.util

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.content.res.Resources
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

object AppUtils {
    fun setLanguage(language: String, from: Activity) {
        val activityRes = from.resources
        val activityConf = activityRes.configuration
        val newLocale = Locale(language)
        activityConf.setLocale(newLocale)
        activityRes.updateConfiguration(activityConf, activityRes.displayMetrics)

        val applicationRes = from.applicationContext.resources
        val applicationConf = applicationRes.configuration
        applicationConf.setLocale(newLocale)
        applicationRes.updateConfiguration(applicationConf,
                applicationRes.displayMetrics)
    }

    fun setLanguageWithoutReload(language: String, from: Activity, to: Class<*>) {
        val languageLocale = Locale(language)
        Locale.setDefault(languageLocale)
        val languageConfig = Configuration()
        languageConfig.locale = languageLocale
        from.resources.updateConfiguration(languageConfig, from.resources.displayMetrics)
    }

    fun initVerticalRV(context: Context, recyclerView: RecyclerView, spanCount: Int, space: Int): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, spanCount, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(SpacesItemDecoration(space, spanCount, true))
        recyclerView.isNestedScrollingEnabled = false
        return gridLayoutManager
    }

    fun showToast(context: Context, text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }

    fun initHorizontalRV(context: Context, recyclerView: RecyclerView, spanCount: Int): GridLayoutManager {
        val gridLayoutManager = GridLayoutManager(context, spanCount, RecyclerView.HORIZONTAL, false)
        recyclerView.layoutManager = gridLayoutManager
        recyclerView.addItemDecoration(SpacesItemDecoration(10, spanCount, false))
        recyclerView.isNestedScrollingEnabled = false
        return gridLayoutManager
    }

    fun initVerticalRVLinear(context: Context, recyclerView: RecyclerView, spanCount: Int): LinearLayoutManager {
        val linearLayoutManager = LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        recyclerView.layoutManager = linearLayoutManager
        // recyclerView.addItemDecoration(new SpacesItemDecoration(10, spanCount, false));
        recyclerView.isNestedScrollingEnabled = false
        return linearLayoutManager
    }

    fun fillSpinner(context: Context, list: List<*>, spinner: Spinner) {
        val arrayAdapter = ArrayAdapter(context, android.R.layout.simple_spinner_item, list)
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = arrayAdapter
    }

    fun hideKeyboard(context: Activity) {
        try {
            val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            if (context.currentFocus != null)
                imm.hideSoftInputFromWindow(context.currentFocus!!.windowToken, 0)
        } catch (e: Exception) {
            Log.e("MultiBackStack", "Failed to add fragment to back stack", e)
        }

    }
}
