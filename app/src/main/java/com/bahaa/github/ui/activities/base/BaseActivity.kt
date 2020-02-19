package com.bahaa.github.ui.activities.base

import android.app.Dialog
import android.app.ProgressDialog
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI

import com.bahaa.github.R
import com.bahaa.github.data.network.NetworkEvent
import com.bahaa.github.data.network.NetworkState
import com.bahaa.github.data.network.interfaces.ErrorResponse
import com.chootdev.csnackbar.Align
import com.chootdev.csnackbar.Duration
import com.chootdev.csnackbar.Snackbar
import com.chootdev.csnackbar.Type
import com.google.android.material.bottomnavigation.BottomNavigationView
import io.reactivex.functions.Consumer

import org.json.JSONObject


class BaseActivity : AppCompatActivity(), ErrorResponse {
    private var mProgressDialog: ProgressDialog? = null
    private var navController: NavController? = null
    private var navView: BottomNavigationView? = null

    fun preOnCreate() {
        instance = this
    }
    public fun getInstance():BaseActivity{
        return instance!!
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        preOnCreate()
        super.onCreate(savedInstanceState)
        hideToolbar()
        setContentView(R.layout.activity_base)
        initActivity(savedInstanceState)
    }

    fun initActivity(savedInstanceState: Bundle?) {
        navView = findViewById(R.id.nav_view)
        navController = Navigation.findNavController(this, R.id.nav_host_fragment)
        NavigationUI.setupWithNavController(navView!!, navController!!)
        initBottomContainer()
    }

    fun initBottomContainer() {
        val f = navController!!.currentDestination!!.id
        if (f == R.id.navHome) {
            navView!!.visibility = View.VISIBLE
        } else {
            navView!!.visibility = View.GONE
        }
    }

    fun hideToolbar() {
        if (supportActionBar != null) supportActionBar!!.hide()
    }

    fun showProgressDialog(): ProgressDialog {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog(this)
            mProgressDialog!!.setProgressStyle(ProgressDialog.STYLE_SPINNER)
            mProgressDialog!!.setCancelable(false)
        }

        if (!mProgressDialog!!.isShowing) {
            mProgressDialog!!.setMessage(getString(R.string.message_loading))
            mProgressDialog!!.show()
        }

        return mProgressDialog as ProgressDialog
    }

    fun hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog!!.isShowing) {
            mProgressDialog!!.dismiss()
        }
    }


    override fun onBackPressed() {
        super.onBackPressed()
        hideProgressDialog()

    }

    fun showDialog(title: String, message: String) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val txtTitle: TextView
        val txtMessage: TextView
        val btnOK: Button
        val btnNO: Button

        txtTitle = dialog.findViewById(R.id.txtTitle)
        txtTitle.text = title

        txtMessage = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        btnOK = dialog.findViewById(R.id.btnOK)
        btnOK.setOnClickListener { dialog.dismiss() }

        btnNO = dialog.findViewById(R.id.btnNO)
        btnNO.visibility = View.GONE

        dialog.show()

    }

    fun showDialog(title: String, message: String, buttonOK: String, buttonNO: String?, image: Int, onOkClickListener: View.OnClickListener?): Dialog {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_alert)
        dialog.window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))

        val imageIcon: ImageView
        val txtTitle: TextView
        val txtMessage: TextView
        val btnOK: Button
        val btnNO: Button


        txtTitle = dialog.findViewById(R.id.txtTitle)
        txtTitle.text = title

        txtMessage = dialog.findViewById(R.id.txtMessage)
        txtMessage.text = message

        imageIcon = dialog.findViewById(R.id.imageIcon)
        if (image != 0) {
            imageIcon.setImageDrawable(getDrawable(image))
        } else {
            imageIcon.visibility = View.GONE
        }


        btnOK = dialog.findViewById(R.id.btnOK)
        btnOK.text = buttonOK
        btnOK.setOnClickListener { v ->
            dialog.dismiss()
            onOkClickListener?.onClick(v)
        }

        btnNO = dialog.findViewById(R.id.btnNO)
        if (buttonNO != null) {
            btnNO.text = buttonNO
            btnNO.setOnClickListener { dialog.dismiss() }
        } else {
            btnNO.visibility = View.GONE
        }

        dialog.show()
        return dialog
    }


    override fun onAlreadyLoggedIn() {

    }

    override fun onNoInternet() {

    }

    override fun onNotAuthorized() {

    }

    override fun onNotAllowedMethod() {

    }

    override fun onApiNotFound() {

    }

    override fun onBadRequest(`object`: JSONObject) {

    }

    override fun onServerSideError() {

    }

    override fun onForbidden() {

    }

    override fun onMaintenance() {

    }

    fun navigate(layoutId: Int, fragment: Fragment) {
        NavHostFragment.findNavController(fragment).navigate(layoutId)
    }

    fun navigateWithBundle(view: View, bundle: Bundle, actionId: Int) {
        Navigation.findNavController(view).navigate(actionId, bundle)

    }

    override fun onResume() {
        super.onResume()
        instance = this
        instance = this

        NetworkEvent.instance!!.register(this, Consumer {networkState ->
            when (networkState) {
                NetworkState.NO_INTERNET -> {
                }
                NetworkState.NO_RESPONSE, NetworkState.SERVER_ERROR -> onServerSideError()
                NetworkState.API_NOT_FOUND -> onApiNotFound()
                NetworkState.UNAUTHORISED -> {
                }
                NetworkState.NOT_ALLOWED_METHOD -> onNotAllowedMethod()
                NetworkState.ALREADY_LOGIN -> onAlreadyLoggedIn()
                NetworkState.MAINTENANCE -> onMaintenance()
                NetworkState.BAD_REQUEST -> {
                }
            }
        })
        initBottomContainer()
    }

    fun customBackClick() {
        navController!!.popBackStack()
    }

    override fun onStop() {
        super.onStop()
        NetworkEvent.instance!!.unregister(this)
    }

    companion object {

        var instance: BaseActivity? = null
            private set
    }

}
