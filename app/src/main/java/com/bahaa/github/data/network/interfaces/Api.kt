package com.bahaa.github.data.network.interfaces

import com.bahaa.github.models.RepoModel

import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface Api {
    @GET("JakeWharton/repos")
    fun getData(@Query("page") page: Int, @Query("per_page") items: Int): Observable<List<RepoModel>>

}
