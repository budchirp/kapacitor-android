package com.cankolay.kapacitor.android.presentation.view.notes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.presentation.composable.GridCard
import com.cankolay.kapacitor.android.presentation.navigation.Route
import com.cankolay.kapacitor.android.presentation.viewmodel.notes.NotesViewModel

@Composable
fun NotesView(
    notesViewModel: NotesViewModel = hiltViewModel<NotesViewModel>()
) {
    val notes by notesViewModel.notes.collectAsState()
    val isLoading by notesViewModel.isLoading.collectAsState()

    Column(modifier = Modifier.fillMaxSize()) {
        if (isLoading) {
            LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
        }

        LazyVerticalGrid(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
            contentPadding = PaddingValues(vertical = 16.dp),
            columns = GridCells.Fixed(count = 2),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items = notes) { note ->
                GridCard(
                    route = Route.Note(id = note.id.toString()),
                    title = note.title,
                    content = note.content
                )
            }
        }
    }
}