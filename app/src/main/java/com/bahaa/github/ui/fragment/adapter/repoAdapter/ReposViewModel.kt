package com.bahaa.github.ui.fragment.adapter.repoAdapter

import android.widget.ImageView
import androidx.annotation.DrawableRes

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.bahaa.github.models.RepoModel
import com.bahaa.github.util.AppTools
import com.squareup.picasso.Picasso

class ReposViewModel(private var repo: RepoModel?) : ViewModel() {
    var imageObservable: ObservableField<String> = ObservableField()
    var repoNameObservable: ObservableField<String> = ObservableField()
    var repoTypeObservable: ObservableField<String> = ObservableField()
    var repoAdminObservable: ObservableField<String> = ObservableField()
    var repoLanguageObservable: ObservableField<String> = ObservableField()
    var repoWatcherObservable: ObservableField<String> = ObservableField()
    var action: MutableLiveData<String> = MutableLiveData()

    init {
        updateUI()
    }

    private fun updateUI() {
        imageObservable.set(repo!!.owner.avatarUrl)
        repoNameObservable.set(repo!!.owner.login)
        repoTypeObservable.set(repo!!.owner.type)
        repoAdminObservable.set(repo!!.owner.siteAdmin!!.toString())
        repoLanguageObservable.set(repo!!.language)
        repoWatcherObservable.set(repo!!.watchers!!.toString())
    }

    fun setrepo(repo: RepoModel) {
        this.repo = repo
    }

    fun onItemClick() {
        action.value = AppTools.OPEN_REPO
    }

    companion object {
        @BindingAdapter("imageUrl")
        @JvmStatic
        fun setImageUrl(imageView: ImageView, url: String) {
            Picasso.with(imageView.context).load(url).into(imageView)
        }
    }

}
