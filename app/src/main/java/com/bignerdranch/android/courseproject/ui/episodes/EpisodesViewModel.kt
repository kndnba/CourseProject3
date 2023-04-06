package com.bignerdranch.android.courseproject.ui.episodes

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.interactor.EpisodesInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpisodesViewModel @Inject constructor(
    private val interactor: EpisodesInteractor
) : ViewModel() {

    fun getEpisodes(page: Int) = interactor.getEpisodes(page)
}