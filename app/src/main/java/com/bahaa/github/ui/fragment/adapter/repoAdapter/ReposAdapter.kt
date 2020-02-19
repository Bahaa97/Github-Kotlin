package com.bahaa.github.ui.fragment.adapter.repoAdapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView

import com.bahaa.github.R
import com.bahaa.github.databinding.ItemRepoBinding
import com.bahaa.github.databinding.ItemRepoGrideBinding
import com.bahaa.github.models.RepoModel
import com.bahaa.github.util.AppTools

import java.util.ArrayList


class ReposAdapter(private val onMovieClick: OnMovieClick, private val view: Int) : RecyclerView.Adapter<ReposAdapter.ViewHolder>() {

    private val cartList: MutableList<RepoModel>

    init {
        this.cartList = ArrayList()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReposAdapter.ViewHolder {
        when (view) {
            R.layout.item_repo -> {
                val itemRepoBinding = DataBindingUtil.inflate<ItemRepoBinding>(LayoutInflater.from(parent.context), view,
                        parent, false)
                return ViewHolder(itemRepoBinding)
            }
            R.layout.item_repo_gride -> {
                val itemRepoGrideBinding = DataBindingUtil.inflate<ItemRepoGrideBinding>(LayoutInflater.from(parent.context), view,
                        parent, false)
                return ViewHolder(itemRepoGrideBinding)
            }
        }
        return null!!
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(cartList[position])
    }

    override fun getItemCount(): Int {
        return cartList.size
    }


    fun setCartList(cartList: List<RepoModel>) {
        this.cartList.addAll(cartList)
        notifyDataSetChanged()
    }

    inner class ViewHolder : RecyclerView.ViewHolder {

        private var itemRepoBinding: ItemRepoBinding? = null
        private var itemRepoGrideBinding: ItemRepoGrideBinding? = null

        constructor(itemRepoBinding: ItemRepoBinding) : super(itemRepoBinding.root) {
            this.itemRepoBinding = itemRepoBinding
        }

        constructor(itemRepoGrideBinding: ItemRepoGrideBinding) : super(itemRepoGrideBinding.root) {
            this.itemRepoGrideBinding = itemRepoGrideBinding
        }

        internal fun bindData(data: RepoModel) {
            val movieViewModel = ReposViewModel(data)
            when (view) {
                R.layout.item_repo -> itemRepoBinding!!.repoItem = movieViewModel
                R.layout.item_repo_gride -> itemRepoGrideBinding!!.repoItem = movieViewModel
            }
            movieViewModel.action.observe(itemView.context as LifecycleOwner, Observer { action ->
                onMovieClick.onMovieClickListener(data)
            })

        }
    }

    interface OnMovieClick {
        fun onMovieClickListener(githubResponse: RepoModel)
    }
}
