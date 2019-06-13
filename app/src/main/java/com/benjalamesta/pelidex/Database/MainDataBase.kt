package com.benjalamesta.pelidex.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.benjalamesta.pelidex.Database.daos.MovieDao
import com.benjalamesta.pelidex.Models.Movie

@Database(entities = [Movie::class], version = 2, exportSchema = false)
abstract class MainDataBase : RoomDatabase(){
    abstract fun movieDao(): MovieDao

    companion object{
        @Volatile
        private var INSTANCE: MainDataBase? = null

        fun getDatabase(appContext: Context): MainDataBase {
            if (INSTANCE == null) {
                synchronized(MainDataBase::class) {
                    INSTANCE = Room
                        .databaseBuilder(appContext.applicationContext
                            , MainDataBase::class.java
                            ,"movies_db")
                        .fallbackToDestructiveMigration()
                        .build()
                }
            }
            return INSTANCE!!
        }
    }

}