package com.bignerdranch.android.courseproject.ui.locationsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bignerdranch.android.courseproject.data.entities.Locations
import com.bignerdranch.android.courseproject.data.interactor.LocationsInteractor
import com.bignerdranch.android.courseproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LocationsDetailViewModel @Inject constructor(
    private val interactor: LocationsInteractor
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _location = _id.switchMap { id ->
        interactor.getLocation(id)
    }
    val location: LiveData<Resource<Locations>> = _location

    fun start(id: Int) {
        _id.value = id
    }
}