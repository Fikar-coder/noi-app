package com.farhanisty.noi.ui.matkul.edit

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.farhanisty.noi.ui.matkul.MatkulForm
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditMatkulScreen(
    onNavigateBack: () -> Unit,
    viewModel: EditMatkulViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is EditMatkulEvent.Success -> onNavigateBack()
                is EditMatkulEvent.Error -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Edit Mata Kuliah", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Kembali")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { padding ->
        if (state.isLoading && state.nama.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) { CircularProgressIndicator() }
        } else {
            MatkulForm(
                state = state,
                onNamaChange = viewModel::onNamaChange,
                onKodeChange = viewModel::onKodeChange,
                onSksChange = viewModel::onSksChange,
                onSemesterChange = viewModel::onSemesterChange,
                onDosenChange = viewModel::onDosenChange,
                onDeskripsiChange = viewModel::onDeskripsiChange,
                onSave = viewModel::updateMatkul,
                saveLabel = "Simpan Perubahan",
                modifier = Modifier.padding(padding)
            )
        }
    }
}
