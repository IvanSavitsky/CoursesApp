package com.example.network.data.source.remote

import retrofit2.http.GET

interface CoursesApi {
    @GET("https://drive.usercontent.google.com/download?id=15arTK7XT2b7Yv4BJsmDctA4Hg-BbS8-q&export=download&authuser=0")
    suspend fun getCourseList(): CourseList
}