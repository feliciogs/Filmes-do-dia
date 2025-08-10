package com.fenixgs.filmedodia.presentation.genreselection

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fenixgs.filmedodia.domain.model.provider.GenresProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreSelectionScreen(
    navController: NavHostController,
) {
    val genreSelectionViewModel :GenreSelectionViewModel  = koinViewModel()

    val selectedGenres by genreSelectionViewModel.selectedGenres.collectAsState()
    val isSaving by genreSelectionViewModel.isSaving.collectAsState()
    val saveSuccess by genreSelectionViewModel.saveSuccess.collectAsState()

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val genres = GenresProvider.genres
    val scrollState = rememberScrollState()

    LaunchedEffect(saveSuccess) {
        when (saveSuccess) {
            true -> {
                navController.navigate("main") {
                    popUpTo("genreSelection") { inclusive = true }
                }
            }
            false -> {
                scope.launch {
                    snackbarHostState.showSnackbar("Erro ao salvar preferências")
                    genreSelectionViewModel.resetSaveStatus()
                }
            }
            else -> {}
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .verticalScroll(scrollState)
                .fillMaxSize()
        ) {
            Text("Selecione seus gêneros preferidos", style = MaterialTheme.typography.headlineSmall)

            Spacer(modifier = Modifier.height(16.dp))

            genres.forEach { genre ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        checked = selectedGenres.contains(genre.id),
                        onCheckedChange = {
                            genreSelectionViewModel.toggleGenreSelection(genre.id)
                        }
                    )
                    Text(text = genre.name)
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = { genreSelectionViewModel.savePreferredGenres() },
                enabled = selectedGenres.isNotEmpty() && !isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .size(20.dp)
                            .padding(end = 8.dp),
                        strokeWidth = 2.dp
                    )
                }
                Text("Salvar e continuar")
            }
        }
    }
}
