package com.bignerdranch.android.courseproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.courseproject.data.entities.Episodes

@Dao
interface EpisodesDao {

    @Query("SELECT * FROM episodes")
    fun getAllEpisodes() : LiveData<List<Episodes>>

    @Query("SELECT * FROM episodes WHERE id = :id")
    fun getEpisode(id: Int): LiveData<Episodes>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(episodes: List<Episodes>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(episodes: Episodes)
}