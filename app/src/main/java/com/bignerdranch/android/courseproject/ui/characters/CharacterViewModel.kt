package com.bignerdranch.android.courseproject.ui.characters

import androidx.lifecycle.ViewModel
import com.bignerdranch.android.courseproject.data.repository.CharacterRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CharacterViewModel @Inject constructor(
    private val repository: CharacterRepository
) : ViewModel() {

    val characters = repository.getCharacters()
}