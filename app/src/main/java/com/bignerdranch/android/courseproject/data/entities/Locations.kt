package com.bignerdranch.android.courseproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "locations")
data class Locations(
    val dimension: String?,
    @PrimaryKey
    val id: Int,
    val name: String?,
    val type: String?,
    val created: String?
    )
