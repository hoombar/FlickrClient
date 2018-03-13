package com.rdydev.flickr.gallery.data

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class DataModule {

    companion object {

        fun provideRetrofit() : Retrofit {
            return Retrofit.Builder()
                    .baseUrl("https://api.flickr.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build()
        }

        fun provideFlickrApi() : FlickrApi {
            val retrofit = provideRetrofit()
            return FlickrApi(retrofit.create(FlickrContract::class.java))
        }

        fun provideDataSorter(): DataSorter {
            return DataSorter()
        }

    }

}