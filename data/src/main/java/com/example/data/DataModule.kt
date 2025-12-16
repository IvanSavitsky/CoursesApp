package com.example.data

import androidx.room.Room
import com.example.data.source.CoursesRepositoryImpl
import com.example.data.source.local.AppDatabase
import com.example.data.source.local.FavouritesRepositoryImpl
import com.example.data.source.remote.CoursesApi
import com.example.domain.repository.AuthRepository
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.FavouritesRepository
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import java.util.concurrent.TimeUnit

val dataModule = module {
    single {
        OkHttpClient.Builder()
            .addInterceptor(
                interceptor = HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            )
            .readTimeout(20, TimeUnit.SECONDS)
            .connectTimeout(20, TimeUnit.SECONDS)
            .build()
    }
    single {
        val client: OkHttpClient = get()

        val retrofit = Retrofit.Builder()
            .baseUrl("http://drive.usercontent.google.com")
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .client(client)
            .build()

        retrofit.create(CoursesApi::class.java)
    }

    single {
        Room.databaseBuilder(
            context = get(),
            klass = AppDatabase::class.java,
            name = "favourite_courses.db"
        ).build()
    }
    single {
        val db: AppDatabase = get()
        db.favouriteCourseDao()
    }

    single<CoursesRepository> {
        CoursesRepositoryImpl(coursesApi = get())
    }
    single<AuthRepository> {
        AuthRepositoryImpl(sharedPreferences = get())
    }
    single<FavouritesRepository> {
        FavouritesRepositoryImpl(favouriteCourseDao = get())
    }
}