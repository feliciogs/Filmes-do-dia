package com.fenixgs.filmedodia.presentation.genreselection

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.fenixgs.filmedodia.domain.model.provider.GenresProvider
import kotlinx.coroutines.launch
import org.koin.androidx.compose.getViewModel

@Composable
fun GenreSelectionScreen(
    navController: NavHostController,
) {
    val genreSelectionViewModel = getViewModel<GenreSelectionViewModel>()

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
