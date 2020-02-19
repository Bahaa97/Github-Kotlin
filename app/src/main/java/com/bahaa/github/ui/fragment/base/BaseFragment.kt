package com.bahaa.github.ui.fragment.base

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment

import com.bahaa.github.ui.activities.base.BaseActivity
import com.bahaa.github.util.MyPreference
import com.google.gson.Gson
import com.google.gson.GsonBuilder


abstract class BaseFragment : Fragment() {

    var activity: BaseActivity? = null
    lateinit var gson: Gson
    lateinit var preference: MyPreference
    var rootView: View? = null

    val baseActivity: BaseActivity
        get() = BaseActivity.instance!!

    protected val isHasOptionsMenu: Boolean
        get() = false

    override fun onStart() {
        super.onStart()
        activity = baseActivity
    }


    override fun onAttach(context: Context) {
        super.onAttach(context)
        activity = baseActivity
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setHasOptionsMenu(isHasOptionsMenu());
        activity = baseActivity
        gson = GsonBuilder().create()
        preference = MyPreference(gson)
    }


    fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show()
    }


    fun showDialog(title: String, message: String, buttonOK: String, buttonNO: String, image: Int, onOkClickListener: View.OnClickListener) {
        activity?.showDialog(title, message, buttonOK, buttonNO, image, onOkClickListener)
    }

    fun showDialog(title: String, message: String) {
        activity?.showDialog(title, message)
    }

    fun hideToolbar() {
        activity?.hideToolbar()
    }


    fun showProgressDialog(): ProgressDialog {
        return activity!!.showProgressDialog()
    }

    fun hideProgressDialog() {
        activity?.hideProgressDialog()
    }

    protected fun navigate(layoutId: Int) {
        BaseActivity.instance?.navigate(layoutId, this)
    }

    protected fun navigateWithBundle(view: View, bundle: Bundle, actionId: Int) {
        BaseActivity.instance?.navigateWithBundle(view, bundle, actionId)
    }

    override fun onResume() {
        super.onResume()
        activity = baseActivity
        BaseActivity.instance?.initBottomContainer()
    }


    fun customBackClick() {
        baseActivity.customBackClick()
    }
}
