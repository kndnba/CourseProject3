package com.bignerdranch.android.courseproject.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bignerdranch.android.courseproject.data.entities.Locations

@Dao
interface LocationsDao {

    @Query("SELECT * FROM locations")
    fun getAllLocations() : LiveData<List<Locations>>

    @Query("SELECT * FROM locations WHERE id = :id")
    fun getLocation(id: Int): LiveData<Locations>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(locations: List<Locations>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(location: Locations)
}