package com.bignerdranch.android.courseproject.data.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "episodes")
data class Episodes(
    val name: String?,
    @PrimaryKey
    val id: Int,
    val air_date: String?,
    val episode: String?,
    val created: String?
)
