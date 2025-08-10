package com.fenixgs.filmedodia.presentation.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SmallTopAppBar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditGenresScreen(
    navController: NavController,
    viewModel: ProfileViewModel = koinViewModel()
) {
    val allGenres by viewModel.allGenres.collectAsState()
    val selectedGenreIds by viewModel.selectedGenreIds.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.loadUserPreferredGenres()
    }

    var selectedSet by remember { mutableStateOf(selectedGenreIds.toMutableSet()) }
    var isSaving by remember { mutableStateOf(false) }

    LaunchedEffect(selectedGenreIds) {
        selectedSet = selectedGenreIds.toMutableSet()
    }

    Scaffold(
        topBar = {
            SmallTopAppBar(
                title = { Text("Editar gêneros preferidos") },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Voltar")
                    }
                }
            )
        },
        content = { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp, vertical = 8.dp)
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(allGenres) { genre ->
                        FilterChip(
                            selected = selectedSet.contains(genre.id),
                            onClick = {
                                selectedSet = selectedSet.toMutableSet().apply {
                                    if (contains(genre.id)) remove(genre.id) else add(genre.id)
                                }
                            },
                            label = { Text(genre.name) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                Button(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    enabled = !isSaving && selectedSet.isNotEmpty(),
                    onClick = {
                        isSaving = true
                        viewModel.updatePreferredGenres(selectedSet.toList()) {
                            isSaving = false
                            navController.popBackStack()
                        }
                    }
                ) {
                    Text("Salvar")
                }
            }
        }
    )
}





