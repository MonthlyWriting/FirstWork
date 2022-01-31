package com.example.monthlywriting.di

import android.content.Context
import androidx.room.Room
import com.example.monthlywriting.Repository
import com.example.monthlywriting.room.AppDataBase
import com.example.monthlywriting.room.DailyWritingItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context) : AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "daily").build()
    }

    @Provides
    @Singleton
    fun provideDailyDao(appDataBase: AppDataBase) : DailyWritingItemDao{
        return appDataBase.dailyWritingItemDao()
    }

    @Provides
    @Singleton
    fun provideRepository(dailyWritingItemDao: DailyWritingItemDao) : Repository {
        return Repository(dailyWritingItemDao)
    }
}