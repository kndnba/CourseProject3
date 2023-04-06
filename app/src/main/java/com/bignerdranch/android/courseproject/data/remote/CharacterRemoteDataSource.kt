package com.bignerdranch.android.courseproject.data.remote

import javax.inject.Inject

class CharacterRemoteDataSource @Inject constructor(
    private val apiService: ApiService
): BaseDataSource() {

    suspend fun getCharacters(page: Int) = getResult { apiService.getAllCharacters(page) }
    suspend fun getCharacter(id: Int) = getResult { apiService.getCharacter(id) }
}