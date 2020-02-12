package io.vasilenko.otus.moviesearcher.data.network

import io.vasilenko.otus.moviesearcher.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object NetworkProvider {

    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun api(): RestApi {
        return provideRetrofit(provideOkHttpClient()).create(RestApi::class.java)
    }

    private fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                chain.proceed(
                    chain.request()
                        .newBuilder()
                        .addHeader("Authorization", "Bearer ${BuildConfig.API_KEY}")
                        .build()
                )
            }
            .build()
    }
}