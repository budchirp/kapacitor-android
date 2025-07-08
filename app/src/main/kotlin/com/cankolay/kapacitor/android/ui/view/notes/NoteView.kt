package com.cankolay.kapacitor.android.ui.view.notes

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cankolay.kapacitor.android.R
import com.cankolay.kapacitor.android.ui.composable.Layout
import com.cankolay.kapacitor.android.viewmodel.notes.NoteViewModel

@Composable
fun NoteView(id: String, noteViewModel: NoteViewModel = hiltViewModel<NoteViewModel>()) {
    LaunchedEffect(
        key1 = id
    ) {
        noteViewModel.fetch(id)
    }

    val note by noteViewModel.notes.collectAsState()
    val isLoading by noteViewModel.isLoading.collectAsState()

    Layout(title = note?.title ?: stringResource(id = R.string.loading)) {
        Column(modifier = Modifier.fillMaxSize()) {
            if (isLoading) {
                LinearProgressIndicator(modifier = Modifier.fillMaxWidth())
            }

            note?.let {
                LazyColumn(modifier = Modifier.fillMaxSize()) {
                    item {
                        Row(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(horizontal = 16.dp)
                        ) {
                            Text(text = note!!.title)
                        }
                    }
                }
            }
        }
    }
}