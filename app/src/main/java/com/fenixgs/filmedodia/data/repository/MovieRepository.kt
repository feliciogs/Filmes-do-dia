package com.fenixgs.filmedodia.data.repository

import com.fenixgs.filmedodia.data.api.RetrofitInstance
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class MovieRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String? get() = auth.currentUser?.uid

    suspend fun getDramaMovies(apiKey: String): List<MovieDTO> {
        val response = RetrofitInstance.api.getMoviesByGenre(
            apiKey = apiKey,
            genreId = 28
        )
        return response.results
    }

    suspend fun getWatchedMovies(): List<String> {
        val uid = userId ?: return emptyList()
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("watched")
            .get()
            .await()

        return snapshot.documents.mapNotNull { it.id }
    }

     fun saveWatchedMovie(title: String) {
        val uid = auth.currentUser?.uid ?: return
        firestore.collection("users")
            .document(uid)
            .collection("watched")
            .document(title)
            .set(mapOf("watched" to true))
    }

}
