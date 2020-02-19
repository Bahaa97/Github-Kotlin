package com.bahaa.github.ui.fragment.offline


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.bahaa.github.MyApplication
import com.bahaa.github.R
import com.bahaa.github.data.network.ConnectivityStatus
import com.bahaa.github.databinding.FragmentOfflineBinding
import com.bahaa.github.ui.fragment.base.BaseFragment
import com.bahaa.github.util.AppTools.Companion.TRY_AGAIN

import com.bahaa.github.util.AppTools

/**
 * A simple [Fragment] subclass.
 */
class OfflineFragment : BaseFragment() {
    private var offlineViewModel: OfflineViewModel? = null
    private var fragmentOfflineBinding: FragmentOfflineBinding? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        offlineViewModel = ViewModelProviders.of(this).get(OfflineViewModel::class.java)
        fragmentOfflineBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_offline, container, false)
        fragmentOfflineBinding!!.offlineViewModle = offlineViewModel
        offlineViewModel!!.mutableLiveData.observe(viewLifecycleOwner, Observer { integer ->
            when (integer) {
                TRY_AGAIN -> onBtnCloseClicked()
            }
        })
        return fragmentOfflineBinding!!.root
    }

    fun onBtnCloseClicked() {
        if (!ConnectivityStatus.isConnected(getActivity()!!)) {
            showToast(MyApplication.context.resources.getString(R.string.label_check_internet))
        } else {
            navigate(R.id.action_offlineFragment_to_navHome)
        }
    }


}
