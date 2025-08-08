package com.fenixgs.filmedodia.presentation.genreselection

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.fenixgs.filmedodia.data.repository.UserPreferencesRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class GenreSelectionViewModel(
    private val userPreferencesRepository: UserPreferencesRepository
) : ViewModel() {

    private val _selectedGenres = MutableStateFlow<List<Int>>(emptyList())
    val selectedGenres: StateFlow<List<Int>> = _selectedGenres

    private val _isSaving = MutableStateFlow(false)
    val isSaving: StateFlow<Boolean> = _isSaving

    private val _saveSuccess = MutableStateFlow<Boolean?>(null)
    val saveSuccess: StateFlow<Boolean?> = _saveSuccess

    fun toggleGenreSelection(genreId: Int) {
        val current = _selectedGenres.value.toMutableList()
        if (current.contains(genreId)) {
            current.remove(genreId)
        } else {
            current.add(genreId)
        }
        _selectedGenres.value = current
    }

    fun savePreferredGenres() {
        if (_selectedGenres.value.isEmpty()) return

        _isSaving.value = true
        viewModelScope.launch {
            try {
                userPreferencesRepository.savePreferredGenres(_selectedGenres.value)
                _saveSuccess.value = true
            } catch (e: Exception) {
                _saveSuccess.value = false
            } finally {
                _isSaving.value = false
            }
        }
    }

    fun resetSaveStatus() {
        _saveSuccess.value = null
    }
}
