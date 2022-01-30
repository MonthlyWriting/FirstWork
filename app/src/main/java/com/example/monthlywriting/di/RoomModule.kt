package com.example.monthlywriting.di

import com.example.monthlywriting.Repository
import com.example.monthlywriting.room.AppDataBase
import com.example.monthlywriting.room.DailyWritingItemDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

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