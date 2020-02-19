package com.bahaa.github.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout

import com.bahaa.github.MyApplication
import com.bahaa.github.R
import com.bahaa.github.databinding.FragmentHomeBinding
import com.bahaa.github.models.RepoModel
import com.bahaa.github.ui.fragment.adapter.repoAdapter.ReposAdapter
import com.bahaa.github.ui.fragment.base.BaseFragment
import com.bahaa.github.util.AppUtils
import com.bahaa.github.util.PaginationListener


class HomeFragment : BaseFragment(), ReposAdapter.OnMovieClick, SwipeRefreshLayout.OnRefreshListener {

    private var homeViewModel: HomeViewModel? = null
    private var fragmentHomeBinding: FragmentHomeBinding? = null
    private var movieAdapter: ReposAdapter? = null


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        fragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        homeViewModel = HomeViewModel(MyApplication.application!!)
        fragmentHomeBinding!!.swipe.setOnRefreshListener(this)
        fragmentHomeBinding!!.homeViewModle = homeViewModel
        initRecycler()
        homeViewModel!!.moviesResponseMutableLiveData.observe(viewLifecycleOwner, Observer { repos -> movieAdapter!!.setCartList(repos) })

        return fragmentHomeBinding!!.root

    }

    private fun initRecycler() {
        val gridLayoutManager = AppUtils.initVerticalRV(context!!, fragmentHomeBinding!!.recyclerMovies, 1, 10)
        movieAdapter = ReposAdapter(this, R.layout.item_repo)
        fragmentHomeBinding!!.recyclerMovies.adapter = movieAdapter
        fragmentHomeBinding!!.recyclerMovies.addOnScrollListener(object : PaginationListener(gridLayoutManager) {
            override val isLastPage: Boolean
                get() = homeViewModel!!.isLastPage
            override val isLoading: Boolean
                get() = homeViewModel!!.isLoading

            override fun loadMoreItems() {
                homeViewModel!!.isLoading = true
                homeViewModel!!.page.value = homeViewModel!!.page.value!! + 1
                homeViewModel!!.getData()
            }

        })
    }

    override fun onMovieClickListener(repo: RepoModel) {

    }

    override fun onRefresh() {
        fragmentHomeBinding!!.swipe.isRefreshing = false
        homeViewModel!!.getData()
    }
}