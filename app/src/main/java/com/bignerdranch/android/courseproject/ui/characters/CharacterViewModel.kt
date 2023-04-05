package com.bignerdranch.android.courseproject.ui.characters

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.interactor.CharacterInteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val interactor: CharacterInteractor,
) : ViewModel() {

    fun getCharacters(page: Int) = interactor.getCharacters(page)
}