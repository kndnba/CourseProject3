package com.bignerdranch.android.courseproject.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.bignerdranch.android.courseproject.data.entities.Locations

@Database(entities = [Locations::class], version = 5, exportSchema = false)
abstract class LocationsDatabase : RoomDatabase() {

    abstract fun getLocationsDao(): LocationsDao

    companion object {
        @Volatile private var instance: LocationsDatabase? = null

        fun getDatabase(context: Context): LocationsDatabase =
            instance ?: synchronized(this) { instance ?: buildDatabase(context).also { instance = it } }

        private fun buildDatabase(appContext: Context) =
            Room.databaseBuilder(appContext, LocationsDatabase::class.java, "locations")
                .fallbackToDestructiveMigration()
                .build()
    }
}