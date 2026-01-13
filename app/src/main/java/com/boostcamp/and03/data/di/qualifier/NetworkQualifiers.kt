package com.boostcamp.and03.data.di.qualifier

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverBookSearchRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class NaverBookSearchOkHttpClient

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AladinBookLookUpRetrofit

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class AladinBookLookUpOkHttpClient