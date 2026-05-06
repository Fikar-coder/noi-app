package com.farhanisty.noi.di

import android.content.Context
import androidx.room.Room
import com.farhanisty.noi.data.local.AppDatabase
import com.farhanisty.noi.data.local.dao.MatkulDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "noi_db").build()

    @Provides
    fun provideMatkulDao(db: AppDatabase): MatkulDao = db.matkulDao()
}
