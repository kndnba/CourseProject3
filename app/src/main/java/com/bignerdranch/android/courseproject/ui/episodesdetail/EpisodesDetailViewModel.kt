package com.bignerdranch.android.courseproject.ui.episodesdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.bignerdranch.android.courseproject.data.entities.Episodes
import com.bignerdranch.android.courseproject.data.repository.EpisodesRepository
import com.bignerdranch.android.courseproject.utils.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesDetailViewModel @Inject constructor(
    private val repository: EpisodesRepository
) : ViewModel() {

    private val _id = MutableLiveData<Int>()

    private val _episode = _id.switchMap { id ->
        repository.getEpisode(id)
    }
    val episode: LiveData<Resource<Episodes>> = _episode

    fun start(id: Int) {
        _id.value = id
    }
}