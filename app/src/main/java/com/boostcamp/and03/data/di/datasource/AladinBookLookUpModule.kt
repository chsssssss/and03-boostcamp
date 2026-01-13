package com.boostcamp.and03.data.di.datasource

import com.boostcamp.and03.data.datasource.remote.BookDetailRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.AladinBookLookUpRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AladinBookLookUpModule {

    @Binds
    @Singleton
    abstract fun bindNaverBookSearchRemoteDataSource(
        impl: AladinBookLookUpRemoteDataSourceImpl
    ): BookDetailRemoteDataSource
}