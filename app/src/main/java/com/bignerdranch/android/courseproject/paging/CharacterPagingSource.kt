package com.bignerdranch.android.courseproject.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.bignerdranch.android.courseproject.data.entities.CharacterList
import com.bignerdranch.android.courseproject.data.remote.ApiService
import com.bignerdranch.android.courseproject.data.repository.CharacterRepository
import retrofit2.HttpException

class CharacterPagingSource(private val repository: ApiService) : PagingSource<Int, com.bignerdranch.android.courseproject.data.entities.Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, com.bignerdranch.android.courseproject.data.entities.Character> {
        return try {
            val currentPage = params.key ?: 1
            val response = repository.getAllCharacters()
            val data = response.body()!!.results
            val responseData = mutableListOf<com.bignerdranch.android.courseproject.data.entities.Character>()
            responseData.addAll(data)

            LoadResult.Page(
                data = responseData,
                prevKey = if (currentPage == 1) null else -1,
                nextKey = currentPage.plus(1)
            )
        } catch (e: Exception){
            LoadResult.Error(e)
        } catch (httpE : HttpException){
            LoadResult.Error(httpE)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, com.bignerdranch.android.courseproject.data.entities.Character>): Int? {
        return null
    }
}