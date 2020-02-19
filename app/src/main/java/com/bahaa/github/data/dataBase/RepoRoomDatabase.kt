package com.bahaa.github.data.dataBase

import android.content.Context
import android.os.AsyncTask

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

import com.bahaa.github.models.RepoModel

import java.util.ArrayList
import java.util.concurrent.ExecutionException
import java.util.concurrent.Executors

@Database(entities = [RepoModel::class], version = 1, exportSchema = false)
abstract class RepoRoomDatabase : RoomDatabase() {

    val allUsers: List<RepoModel>
        get() {
            try {
                return GetUsersAsyncTask().execute().get()
            } catch (e: ExecutionException) {
                e.printStackTrace()
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }

            return ArrayList()
        }

    abstract fun RepoDeo(): RepoDao


    private inner class GetUsersAsyncTask : AsyncTask<Void, Void, List<RepoModel>>() {
        override fun doInBackground(vararg voids: Void): List<RepoModel> {
            return INSTANCE!!.RepoDeo().repos
        }
    }

    companion object {

        @Volatile
        private var INSTANCE: RepoRoomDatabase? = null

        fun getDatabase(context: Context): RepoRoomDatabase? {
            if (INSTANCE == null) {
                synchronized(RepoRoomDatabase::class.java) {
                    if (INSTANCE == null) {
                        INSTANCE = Room.databaseBuilder(context.applicationContext,
                                RepoRoomDatabase::class.java, "repos")
                                .build()
                    }
                }
            }
            return INSTANCE
        }
    }
}
