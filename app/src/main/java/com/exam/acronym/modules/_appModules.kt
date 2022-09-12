package com.exam.acronym.modules

import com.exam.acronym.network.AcronymAPI
import com.exam.acronym.repositories.AcronymRepository
import com.exam.acronym.utils.Constants
import com.exam.acronym.viewModels.MainViewModel
import com.squareup.moshi.Moshi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

val networkModule = module {
    single { provideOkHttpClient() }
    single { provideMoshi() }
    factory { provideRetrofit(get(), get()) }
    factory { provideAPI(get()) }
}

val repositoryModule = module {
    factory { AcronymRepository(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
}

/**
 * @return [Retrofit] instance
 */
private fun provideRetrofit(
    okHttpClient: OkHttpClient,
    moshi: Moshi
): Retrofit = Retrofit.Builder()
    .baseUrl(Constants.BASE_URL)
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
    .client(okHttpClient)
    .build()

/**
 * @return [OkHttpClient] instance
 */
private fun provideOkHttpClient() = OkHttpClient.Builder()
    .readTimeout(10L, TimeUnit.SECONDS)
    .addInterceptor(
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    )
    .build()

/**
 * @return [Moshi] instance
 */
private fun provideMoshi(): Moshi = Moshi.Builder().build()

/**
 * @return [AcronymAPI] instance
 */
private fun provideAPI(retrofit: Retrofit): AcronymAPI = retrofit.create(AcronymAPI::class.java)
