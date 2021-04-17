package com.app.mystockapp.di

import android.content.Context
import androidx.room.Room
import com.app.mystockapp.api.RetrofitAPI
import com.app.mystockapp.db.StockDao
import com.app.mystockapp.db.StockDatabase
import com.app.mystockapp.repo.StockRepository
import com.app.mystockapp.util.Util.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun injectRoomDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context,StockDatabase::class.java,"StocksDB").build()

    @Singleton
    @Provides
    fun injectDao(
        database: StockDatabase
    ) = database.stockdao()


    @Singleton
    @Provides
    fun injectRetrofitAPI() : RetrofitAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL).build().create(RetrofitAPI::class.java)
    }

    @Singleton
    @Provides
    fun injectRepository(dao:StockDao,api:RetrofitAPI)=StockRepository(dao,api)
}