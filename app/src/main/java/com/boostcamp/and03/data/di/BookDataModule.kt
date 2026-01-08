package com.boostcamp.and03.data.di

import com.boostcamp.and03.data.datasource.remote.BookRemoteDataSource
import com.boostcamp.and03.data.datasource.remote.FirestoreBookRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class BookDataModule {

    @Binds
    abstract fun bindBookRemoteDataSource(
        impl: FirestoreBookRemoteDataSourceImpl
    ): BookRemoteDataSource
}