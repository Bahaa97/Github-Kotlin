package com.bahaa.github.data.dataBase

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

import com.bahaa.github.models.RepoModel

import io.reactivex.Flowable
import io.reactivex.Maybe

@Dao
interface RepoDao {

    @get:Query("SELECT * from repos")
    val repos: List<RepoModel>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(repoModel: List<RepoModel>)

    @Query("DELETE FROM repos")
    fun deleteAll()
}
