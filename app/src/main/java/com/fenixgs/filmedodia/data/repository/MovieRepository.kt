package com.fenixgs.filmedodia.data.repository

import com.fenixgs.filmedodia.data.api.RetrofitInstance
import com.fenixgs.filmedodia.data.api.dto.MovieDTO
import com.fenixgs.filmedodia.domain.model.Genre
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.tasks.await

class MovieRepository(
    private val firestore: FirebaseFirestore,
    private val auth: FirebaseAuth
) {

    private val userId: String? get() = auth.currentUser?.uid

    suspend fun getDramaMovies(apiKey: String): List<MovieDTO> {
        val response = RetrofitInstance.api.getMoviesByGenre(
            apiKey = apiKey,
            genreId = 18
        )
        return response.results
    }

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

    suspend fun saveUserPreferredGenres(genres: List<Genre>) {
        val uid = Firebase.auth.currentUser?.uid ?: return
        val db = Firebase.firestore

        val genreIds = genres.map { it.id }
        db.collection("users").document(uid).set(mapOf("preferredGenres" to genreIds), SetOptions.merge()).await()
    }


}
