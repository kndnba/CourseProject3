package com.bignerdranch.android.courseproject.data.remote

import com.bignerdranch.android.courseproject.data.entities.*
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {
    @GET("character ")
    suspend fun getAllCharacters(@Query("page") pages: Int) : Response<CharacterList>

    @GET("character/{id}")
    suspend fun getCharacter(@Path("id") id: Int): Response<Character>

    @GET("location")
    suspend fun getAllLocations(@Query("page")pages: Int) : Response<LocationResponse>

    @GET("location/{id}")
    suspend fun getLocation(@Path("id") id: Int): Response<Locations>

    @GET("episode")
    suspend fun getAllEpisodes(@Query("page") pages: Int) : Response<EpisodeResponse>

    @GET("episode/{id}")
    suspend fun getEpisode(@Path("id") id: Int): Response<Episodes>

}