package com.bignerdranch.android.courseproject.ui.locations

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.repository.LocationsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val repository: LocationsRepository
) : ViewModel() {

    val locations = repository.getLocations()
}