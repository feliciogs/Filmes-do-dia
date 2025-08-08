package com.fenixgs.filmedodia.data.repository

import com.fenixgs.filmedodia.data.api.RetrofitInstance
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import kotlinx.coroutines.tasks.await

class MovieRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String? get() = auth.currentUser?.uid

    suspend fun getMoviesByGenre(apiKey: String,genre:Int): List<MovieDTO> {
        val response = RetrofitInstance.api.getMoviesByGenre(
            apiKey = apiKey,
            genreId = genre
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

    fun saveWatchedMovie(title: String, posterUrl: String) {
        val uid = auth.currentUser?.uid ?: return
        val data = mapOf(
            "watched" to true,
            "posterUrl" to posterUrl,
            "watchedAt" to System.currentTimeMillis() // timestamp
        )
        firestore.collection("users")
            .document(uid)
            .collection("watched")
            .document(title)
            .set(data)
    }


    suspend fun getWatchedMoviesDetailed(): List<MovieDTO> {
        val uid = userId ?: return emptyList()
        val snapshot = firestore.collection("users")
            .document(uid)
            .collection("watched")
            .orderBy("watchedAt", Query.Direction.DESCENDING)
            .get()
            .await()

        return snapshot.documents.mapNotNull { doc ->
            MovieDTO(
                id = 0,
                title = doc.id,
                poster_path = doc.getString("posterUrl"),
                overview = "",
                vote_average = 10.0,
                release_date = ""
            )
        }
    }


}
