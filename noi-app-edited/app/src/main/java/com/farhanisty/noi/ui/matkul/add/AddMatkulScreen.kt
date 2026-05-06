package com.farhanisty.noi.ui.matkul.add

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.farhanisty.noi.ui.matkul.MatkulForm
import kotlinx.coroutines.flow.collectLatest

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddMatkulScreen(
    onNavigateBack: () -> Unit,
    viewModel: AddMatkulViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.event.collectLatest { event ->
            when (event) {
                is AddMatkulEvent.Success -> onNavigateBack()
                is AddMatkulEvent.Error -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Tambah Mata Kuliah", fontWeight = FontWeight.Bold) },
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
        MatkulForm(
            state = state,
            onNamaChange = viewModel::onNamaChange,
            onKodeChange = viewModel::onKodeChange,
            onSksChange = viewModel::onSksChange,
            onSemesterChange = viewModel::onSemesterChange,
            onDosenChange = viewModel::onDosenChange,
            onDeskripsiChange = viewModel::onDeskripsiChange,
            onSave = viewModel::saveMatkul,
            saveLabel = "Tambah Mata Kuliah",
            modifier = androidx.compose.ui.Modifier.padding(padding)
        )
    }
}
