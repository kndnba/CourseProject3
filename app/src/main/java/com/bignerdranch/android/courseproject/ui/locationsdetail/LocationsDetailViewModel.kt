package com.bignerdranch.android.courseproject.ui.locationsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bignerdranch.android.courseproject.data.entities.Locations
import com.bignerdranch.android.courseproject.data.repository.LocationsRepository
import com.bignerdranch.android.courseproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsDetailViewModel @Inject constructor(
    private val repository: LocationsRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _location = _id.switchMap { id ->
        repository.getLocation(id)
    }
    val location: LiveData<Resource<Locations>> = _location

    fun start(id: Int) {
        _id.value = id
    }
}