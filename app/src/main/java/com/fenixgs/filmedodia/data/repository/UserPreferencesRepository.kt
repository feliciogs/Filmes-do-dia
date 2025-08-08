package com.fenixgs.filmedodia.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class UserPreferencesRepository(
    private val auth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) {

    suspend fun hasPreferredGenres(): Boolean {
        val user = auth.currentUser ?: return false
        val snapshot = firestore.collection("users")
            .document(user.uid)
            .get()
            .await()

        val genres = snapshot.get("preferredGenres") as? List<*>
        return !genres.isNullOrEmpty()
    }

    suspend fun savePreferredGenres(genreIds: List<Int>) {
        val user = auth.currentUser ?: return
        firestore.collection("users")
            .document(user.uid)
            .set(mapOf("preferredGenres" to genreIds))
            .await()
    }

    suspend fun getPreferredGenres(): List<Int> {
        val user = auth.currentUser ?: return emptyList()
        val snapshot = firestore.collection("users")
            .document(user.uid)
            .get()
            .await()

        return (snapshot.get("preferredGenres") as? List<Long>)?.map { it.toInt() } ?: emptyList()
    }
}
