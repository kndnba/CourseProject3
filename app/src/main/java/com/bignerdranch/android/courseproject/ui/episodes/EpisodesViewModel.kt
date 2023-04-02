package com.bignerdranch.android.courseproject.ui.episodes

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.repository.EpisodesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val repository: EpisodesRepository
) : ViewModel() {

    val episodes = repository.getEpisodes()
}