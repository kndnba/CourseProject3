package com.bignerdranch.android.courseproject.ui.locations

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.interactor.LocationsInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsViewModel @Inject constructor(
    private val interactor: LocationsInteractor
) : ViewModel() {

    fun getLocations(page: Int) = interactor.getLocations(page)
}