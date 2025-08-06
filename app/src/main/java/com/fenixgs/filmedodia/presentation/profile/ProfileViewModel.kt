package com.fenixgs.filmedodia.presentation.profile

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel : ViewModel() {

    private val _displayName = MutableStateFlow<String>("Usuário")
    val displayName: StateFlow<String> = _displayName

    init {
        val user = FirebaseAuth.getInstance().currentUser
        _displayName.value = user?.displayName ?: "Usuário"
    }
}
